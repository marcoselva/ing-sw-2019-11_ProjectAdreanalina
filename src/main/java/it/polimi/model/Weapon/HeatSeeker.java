package it.polimi.model.Weapon;

import it.polimi.model.*;
import it.polimi.model.Exception.VisibleTarget;


import java.util.ArrayList;

public class HeatSeeker extends WeaponCard {

    /**
     * Instantiates a new Heat Seeker card.
     * Sets the field color to RED calling the constructor of weapon card (the super class).
     * Creates the list of recharge cost settings its value to RED,RED,YELLOW.
     * Creates the list of effects setting its value to BaseEffect.
     */
    public HeatSeeker(){

        super("HEAT SEEKER", EnumColorCardAndAmmo.RED);
        ArrayList<EnumColorCardAndAmmo>rechargeCost=new ArrayList<EnumColorCardAndAmmo>();
        rechargeCost.add(EnumColorCardAndAmmo.RED);
        rechargeCost.add(EnumColorCardAndAmmo.RED);
        rechargeCost.add(EnumColorCardAndAmmo.YELLOW);
        setRechargeCost(rechargeCost);
        ArrayList<WeaponsEffect> weaponEffects=new ArrayList<>();
        weaponEffects.add(WeaponsEffect.BaseEffect);
    }


    public void BaseEffect(Map map,Player currentPlayer,Player targetPlayer) throws VisibleTarget {

        if(!(map.isVisible(currentPlayer,targetPlayer))){

            ArrayList<EnumColorPlayer> heatSeekerDamages=new ArrayList<>();
            heatSeekerDamages.add(currentPlayer.getColor());
            heatSeekerDamages.add(currentPlayer.getColor());
            heatSeekerDamages.add(currentPlayer.getColor());
            targetPlayer.multipleDamages(heatSeekerDamages);
        }else {

            throw new VisibleTarget();
        }
    }


}
