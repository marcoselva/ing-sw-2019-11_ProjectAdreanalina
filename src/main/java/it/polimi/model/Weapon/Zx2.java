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
     * Creates the list of scanner mode cost (cost of alternative fire mode) settings it to null.
     */
    public Zx2() {

        super("ZX-2", EnumColorCardAndAmmo.YELLOW);
        rechargeCost = new ArrayList<EnumColorCardAndAmmo>();
        rechargeCost.add(EnumColorCardAndAmmo.YELLOW);
        rechargeCost.add(EnumColorCardAndAmmo.RED);
        scannerModeCost = new ArrayList<EnumColorCardAndAmmo>();
        scannerModeCost.add(null);
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
