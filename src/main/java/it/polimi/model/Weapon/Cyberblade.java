package it.polimi.model.Weapon;

import it.polimi.model.*;
import it.polimi.model.Exception.MapException;
import it.polimi.model.Exception.NotValidDistance;


import java.util.ArrayList;

public class Cyberblade extends WeaponCard {

    private ArrayList<EnumColorCardAndAmmo> shadowstepCost;
    private ArrayList<EnumColorCardAndAmmo> sliceAndDiceCost;

    /**
     * Instantiates a new Cyberblade card.
     * Sets the field color to YELLOW calling the constructor of weapon card (the super class).
     * Creates the list of recharge cost settings its value to YELLOW,RED.
     * Creates the list of effects setting its value to BaseEffect,ShadowstepEffect,SliceAndDiceEffect.
     * Creates the list of shadow step cost(cost of optional effect 1) settings it to null.
     * Creates the list of slice and dice cost(cost of optional effect 2) settings it to YELLOW.
     */
    public Cyberblade(){

        super("CYBERBLADE", EnumColorCardAndAmmo.YELLOW);
        ArrayList<EnumColorCardAndAmmo> rechargeCost =new ArrayList<EnumColorCardAndAmmo>();
        rechargeCost.add(EnumColorCardAndAmmo.YELLOW);
        rechargeCost.add(EnumColorCardAndAmmo.RED);
        setRechargeCost(rechargeCost);
        ArrayList<WeaponsEffect> weaponEffects= new ArrayList<>();
        weaponEffects.add(WeaponsEffect.BaseEffect);
        weaponEffects.add(WeaponsEffect.ShadowstepEffect);
        weaponEffects.add(WeaponsEffect.SliceAndDiceEffect);
        setWeaponEffects(weaponEffects);
        shadowstepCost=new ArrayList<>();
        shadowstepCost.add(null);
        sliceAndDiceCost = new ArrayList<EnumColorCardAndAmmo>();
        sliceAndDiceCost.add(EnumColorCardAndAmmo.YELLOW);
        setOptional(true);
        setDescription("Basic Effect: Deal 2 damage to 1 target on your square.\n" +
                "with Shadowstep: Move 1 square before or after the basic effect.\n" +
                "with Slice and Dice: Deal 2 damage to a different target on your square. The shadowstep may be used before or after this effect.\n" +
                "Notes: Combining all effects allows you to move onto a square and whack 2 people; or whack somebody, move, and whack somebody else; or whack 2 people and then move.");
    }

    /**
     * get shadowstepCost
     */
    public ArrayList<EnumColorCardAndAmmo> getShadowstepCost() {

        return shadowstepCost;
    }

    /**
     * get sliceAndDiceCost
     */
    public ArrayList<EnumColorCardAndAmmo> getSliceAndDiceCost() {

        return sliceAndDiceCost;
    }

    /**
     * Shoot a player who is on current player's square.
     *
     * @param map the map of the game.
     * @param currentPlayer the current player.
     * @param target1 the player you want to shoot.
     * @throws NotValidDistance
     * @throws MapException
     */
    public void baseEffect(Map map,Player currentPlayer,Player target1) throws NotValidDistance, MapException {

        if(map.findPlayer(currentPlayer)==map.findPlayer(target1)){

            ArrayList<EnumColorPlayer> cyberbladeDamages=new ArrayList<>();
            cyberbladeDamages.add(currentPlayer.getColor());
            cyberbladeDamages.add(currentPlayer.getColor());
            target1.multipleDamages(cyberbladeDamages);
        }else {

            throw new NotValidDistance();
        }
    }

    /**
     * Moves the current player by one movement.
     *
     * @param map the map of the game.
     * @param currentPlayer the current player.
     * @param destSquare the square where current player want to move on.
     * @throws NotValidDistance
     * @throws MapException
     */
    public void shadowstepEffect(Map map, Player currentPlayer, Square destSquare) throws NotValidDistance, MapException {

        Square squareOfCurrentPlayer = map.findPlayer(currentPlayer);
        if(map.distance(squareOfCurrentPlayer,destSquare)==1){

            map.movePlayer(currentPlayer,destSquare);
        }else{

            throw new NotValidDistance();
        }
    }

    /**
     * Shoot a second player (different by the first) that is on current player's square.
     *
     * @param map the map of the game.
     * @param currentPlayer the current player.
     * @param target2 the second player you want to shoot.
     * @throws NotValidDistance
     * @throws MapException
     */
    public void sliceAndDiceEffect(Map map,Player currentPlayer,Player target2)throws NotValidDistance,MapException{

        baseEffect(map,currentPlayer,target2);
    }
}
