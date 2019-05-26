package it.polimi.model.Weapon;

import it.polimi.model.*;
import it.polimi.model.Exception.NotVisibleTarget;

import java.util.ArrayList;

public class Zx2 extends WeaponCard {

    private ArrayList<EnumColorCardAndAmmo> scannerModeCost;

    /**
     * Instantiates a new Zx2 card.
     * Sets the field color to YELLOW calling the constructor of weapon card (the super class).
     * Creates the list of recharge cost setting its value to YELLOW,RED.
     * Creates the list of effects setting its value to BaseMode,ScannerMode.
     * Creates the list of scanner mode cost (cost of alternative fire mode) settings it to null.
     */
    public Zx2() {

        super("ZX-2", EnumColorCardAndAmmo.YELLOW);
        ArrayList<EnumColorCardAndAmmo>rechargeCost = new ArrayList<EnumColorCardAndAmmo>();
        rechargeCost.add(EnumColorCardAndAmmo.YELLOW);
        rechargeCost.add(EnumColorCardAndAmmo.RED);
        setRechargeCost(rechargeCost);
        ArrayList<WeaponsEffect>weaponEffects= new ArrayList<>();
        weaponEffects.add(WeaponsEffect.BaseMode);
        weaponEffects.add(WeaponsEffect.ScannerMode);
        setWeaponEffects(weaponEffects);
        scannerModeCost = new ArrayList<EnumColorCardAndAmmo>();
        scannerModeCost.add(null);
        setDescription("modalità base: Dai 1 danno e 2 marchi a 1 bersaglio che puoi vedere.\n\n" +
                "modalità scanner: Scegli fino a 3 bersagli che puoi vedere e dai 1 marchio a ciascuno.\n\n" +
                "Nota: Ricorda che i 3 bersagli possono anche essere in 3 stanze diverse.");
    }

    public ArrayList<EnumColorCardAndAmmo> getScannerModeCost() {

        return scannerModeCost;
    }

    public void baseMode(Map map, Player currentPlayer,Player target1) throws NotVisibleTarget{

        if(map.isVisible(currentPlayer,target1)){

            ArrayList<EnumColorPlayer> zx2marks=new ArrayList<>();
            zx2marks.add(currentPlayer.getColor());
            zx2marks.add(currentPlayer.getColor());
            target1.singleDamageMultipleMarks(currentPlayer.getColor(),zx2marks);
        }else{

            throw new NotVisibleTarget();
        }
    }

    public void scannerMode(Map map,Player currentPLayer,ArrayList<Player> targets)throws NotVisibleTarget{

        for(Player player:targets){

            if(!(map.isVisible(currentPLayer,player))){

                throw new NotVisibleTarget();
            }
        }
        for(Player player:targets){

            player.singleMark(currentPLayer.getColor());
        }
    }
}
