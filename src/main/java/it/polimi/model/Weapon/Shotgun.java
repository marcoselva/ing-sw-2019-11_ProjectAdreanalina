package it.polimi.model.Weapon;

import it.polimi.model.*;
import it.polimi.model.Exception.MapException;
import it.polimi.model.Exception.NotValidDistance;
import it.polimi.model.Exception.NotVisibleTarget;

import java.util.ArrayList;

public class Shotgun extends WeaponCard {

    private ArrayList<EnumColorCardAndAmmo> longBarrelModeCost;

    /**
     * Instantiates a new Shotgun card.
     * Sets the field color to YELLOW calling the constructor of weapon card (the super class).
     * Creates the list of recharge cost setting its value to YELLOW,YELLOW.
     * Creates the list of effects setting its value to BaseMode,LongBarrelMode.
     * Creates the list of long barrel mode cost (cost of alternative fire mode) settings it to null.
     */
    public Shotgun() {

        super("SHOTGUN", EnumColorCardAndAmmo.YELLOW);
        ArrayList<EnumColorCardAndAmmo>rechargeCost = new ArrayList<EnumColorCardAndAmmo>();
        rechargeCost.add(EnumColorCardAndAmmo.YELLOW);
        rechargeCost.add(EnumColorCardAndAmmo.YELLOW);
        setRechargeCost(rechargeCost);
        ArrayList<WeaponsEffect> weaponEffects= new ArrayList<>();
        weaponEffects.add(WeaponsEffect.BaseMode);
        weaponEffects.add(WeaponsEffect.LongBarrelMode);
        setWeaponEffects(weaponEffects);
        longBarrelModeCost = new ArrayList<EnumColorCardAndAmmo>();
        longBarrelModeCost.add(null);
        setOptional(false);
        setDescription("modalità base: Dai 3 danni a 1 bersaglio nel quadrato in cui ti trovi.\n" +
                "Se vuoi puoi muovere quel bersaglio di 1 quadrato.\n\n" +
                "modalità canna lunga: Dai 2 danni a 1 bersaglio in un quadrato distante esattamente 1 movimento.");
    }

    public ArrayList<EnumColorCardAndAmmo> getLongBarrelModeCost() {

        return longBarrelModeCost;
    }

    public void baseMode(Map map, Player currentPlayer, Player target1,Square destSquare)throws NotValidDistance, MapException {

        Square squareOfTargetPlayer=map.findPlayer(target1);
        if(map.distance(currentPlayer,target1)==0){

            if((destSquare!=null)&&(map.distance(squareOfTargetPlayer,destSquare)==1)){

                map.movePlayer(target1,destSquare);
            }else if((destSquare!=null)&&(!(map.distance(squareOfTargetPlayer,destSquare)==1))){

                throw new NotValidDistance();
            }
            ArrayList<EnumColorPlayer> shotGunDamages=new ArrayList<>();
            shotGunDamages.add(currentPlayer.getColor());
            shotGunDamages.add(currentPlayer.getColor());
            shotGunDamages.add(currentPlayer.getColor());
            target1.multipleDamages(shotGunDamages);
        }else{

            throw new NotValidDistance();
        }
    }

    public void longBarrelMode(Map map, Player currentPlayer, Player target1) throws NotValidDistance {

        if(map.distance(currentPlayer,target1)==1){

            ArrayList<EnumColorPlayer> shotGunDamages= new ArrayList<>();
            shotGunDamages.add(currentPlayer.getColor());
            shotGunDamages.add(currentPlayer.getColor());
            target1.multipleDamages(shotGunDamages);
        }else {

            throw new NotValidDistance();
        }

    }
}
