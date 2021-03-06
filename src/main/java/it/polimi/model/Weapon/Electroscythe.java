package it.polimi.model.Weapon;

import it.polimi.model.*;
import it.polimi.model.Exception.MapException;
import it.polimi.model.Exception.NoTargetInSquare;
import it.polimi.model.Exception.NotValidInput;

import java.util.ArrayList;

public class Electroscythe extends WeaponCard {

    private ArrayList<EnumColorCardAndAmmo> reaperModeCost;

    /**
     * Instantiates a new Lock Electoscythe card.
     * Sets the field color to BLU calling the constructor of weapon card (the super class).
     * Creates the list of recharge cost setting its value to BLU.
     * Creates the list of effects setting its value to BaseMode,ReaperMode.
     * Creates the list of reaper mode cost (cost of alternative fire mode) settings it to BLU,RED.
     */
    public Electroscythe() {

        super("ELECTOSCYTHE", EnumColorCardAndAmmo.BLU);
        ArrayList<EnumColorCardAndAmmo>rechargeCost = new ArrayList<EnumColorCardAndAmmo>();
        rechargeCost.add(EnumColorCardAndAmmo.BLU);
        setRechargeCost(rechargeCost);
        ArrayList<WeaponsEffect> weaponEffects=new ArrayList<>();
        weaponEffects.add(WeaponsEffect.BaseMode);
        weaponEffects.add(WeaponsEffect.ReaperMode);
        setWeaponEffects(weaponEffects);
        reaperModeCost= new ArrayList<EnumColorCardAndAmmo>();
        reaperModeCost.add(EnumColorCardAndAmmo.BLU);
        reaperModeCost.add(EnumColorCardAndAmmo.RED);
        setOptional(false);
        setDescription("Basic Mode: Deal 1 damage to every other player on your square.\n" +
                "in Reaper Mode: Deal 2 damage to every other player on your square");
    }

    /**
     * get reaperModeCost
     */
    public ArrayList<EnumColorCardAndAmmo> getReaperModeCost() {

        return reaperModeCost;
    }

    /**
     * Shoot all players that are on current player's square.
     *
     * @param map the map of the game.
     * @param currentPlayer the current player.
     * @throws NoTargetInSquare
     * @throws MapException
     */
    public void baseMode(Map map, Player currentPlayer) throws NoTargetInSquare, MapException {

        Square squareOfCurrentPlayer = map.findPlayer(currentPlayer);
        ArrayList<Player> playersOnSquare = new ArrayList<>(map.playersOnSquare(squareOfCurrentPlayer));
        playersOnSquare.remove(currentPlayer);
        if(playersOnSquare.size()>0){

            for(Player p:playersOnSquare) {

                p.singleDamage(currentPlayer.getColor());
            }
        }else {

            throw new NoTargetInSquare();
        }
    }

    /**
     * Shoot all players that are on current player's square.
     *
     * @param map the map of the game.
     * @param currentPlayer the current player.
     * @throws NoTargetInSquare
     * @throws MapException
     */
    public void reaperMode(Map map,Player currentPlayer) throws NoTargetInSquare, MapException {

        Square squareOfCurrentPlayer = map.findPlayer(currentPlayer);
        ArrayList<Player> playersOnSquare = new ArrayList<>(map.playersOnSquare(squareOfCurrentPlayer));
        playersOnSquare.remove(currentPlayer);
        
        if(playersOnSquare.size()>0){

            ArrayList<EnumColorPlayer> electroscytheDamages=new ArrayList<>();
            electroscytheDamages.add(currentPlayer.getColor());
            electroscytheDamages.add(currentPlayer.getColor());
            for(Player p:playersOnSquare) {

                p.multipleDamages(electroscytheDamages);
            }
        }else {

            throw new NoTargetInSquare();
        }
    }
}






