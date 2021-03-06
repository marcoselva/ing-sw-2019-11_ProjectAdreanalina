package it.polimi.model.Weapon;

import it.polimi.model.*;
import it.polimi.model.Exception.MapException;
import it.polimi.model.Exception.NotValidDistance;
import it.polimi.model.Exception.NotValidInput;
import it.polimi.model.Exception.NotVisibleTarget;

import java.util.ArrayList;

public class Hellion extends WeaponCard {

    private ArrayList<EnumColorCardAndAmmo> nanoTracerModeCost;

    /**
     * Instantiates a new Hellion card.
     * Sets the field color to RED calling the constructor of weapon card (the super class).
     * Creates the list of recharge cost setting its value to RED,YELLOW.
     * Creates the list of effects setting its value to BaseMode,NanoTracerMode
     * Creates the list of nano tracer mode cost (cost of alternative fire mode) settings it to RED.
     */
    public Hellion() {

        super("HELLION", EnumColorCardAndAmmo.RED);
        ArrayList<EnumColorCardAndAmmo>rechargeCost = new ArrayList<EnumColorCardAndAmmo>();
        rechargeCost.add(EnumColorCardAndAmmo.RED);
        rechargeCost.add(EnumColorCardAndAmmo.YELLOW);
        setRechargeCost(rechargeCost);
        ArrayList<WeaponsEffect> weaponEffects= new ArrayList<>();
        weaponEffects.add(WeaponsEffect.BaseMode);
        weaponEffects.add(WeaponsEffect.NanoTracerMode);
        setWeaponEffects(weaponEffects);
        nanoTracerModeCost = new ArrayList<EnumColorCardAndAmmo>();
        nanoTracerModeCost.add(EnumColorCardAndAmmo.RED);
        setOptional(false);
        setDescription("Basic Mode: Deal 1 damage to 1 target you can see at least 1 move away. Then give 1 mark to that target and everyone else on that square.\n" +
                "in Nano-Tracer Mode: Deal 1 damage to 1 target you can see at least 1 move away. Then give 2 marks to that target and everyone else on that square.");
    }

    /**
     * get nanoTracerModeCost
     */
    public ArrayList<EnumColorCardAndAmmo> getNanoTracerModeCost() {

        return nanoTracerModeCost;
    }

    /**
     * Shoot a player who current player can see and distant exactly one movement from his square and mark all player
     * on the square just chosen.
     *
     * @param map the map of the game.
     * @param currentPlayer the current player.
     * @param targetPlayer the player you want to shoot.
     * @throws NotVisibleTarget
     * @throws NotValidDistance
     * @throws MapException
     */
    public void baseMode(Map map, Player currentPlayer,Player targetPlayer) throws NotVisibleTarget, NotValidDistance, MapException {

        if((map.isVisible(currentPlayer,targetPlayer)) && (map.distance(currentPlayer,targetPlayer)>0)){

            Square targetSquare = map.findPlayer(targetPlayer);
            ArrayList<Player> playersInTargetSquare;
            playersInTargetSquare= new ArrayList<>(map.playersOnSquare(targetSquare));
            targetPlayer.singleDamage(currentPlayer.getColor());
            for(Player p:playersInTargetSquare){

                p.singleMark(currentPlayer.getColor());
            }
        }else if(!(map.isVisible(currentPlayer,targetPlayer))){

            throw new NotVisibleTarget();
        }else if(!(map.distance(currentPlayer,targetPlayer)>0)){

            throw new NotValidDistance();
        }
    }

    /**
     * Shoot a player who current player can see and distant exactly one movement from his square and mark all player
     * on the square just chosen.
     *
     * @param map the map of the game.
     * @param currentPlayer the current player.
     * @param targetPlayer the player you want to shoot.
     * @throws NotVisibleTarget
     * @throws NotValidDistance
     * @throws MapException
     */
    public void nanoTracerMode(Map map, Player currentPlayer,Player targetPlayer) throws NotVisibleTarget, NotValidDistance, MapException {

        if((map.isVisible(currentPlayer,targetPlayer)) && (map.distance(currentPlayer,targetPlayer)>0)){

            Square targetSquare = map.findPlayer(targetPlayer);
            ArrayList<Player> playersInTargetSquare;
            playersInTargetSquare= new ArrayList<>(map.playersOnSquare(targetSquare));
            targetPlayer.singleDamage(currentPlayer.getColor());
            ArrayList<EnumColorPlayer> hellionMarks= new ArrayList<>();
            hellionMarks.add(currentPlayer.getColor());
            hellionMarks.add(currentPlayer.getColor());
            for(Player p:playersInTargetSquare){

                p.multipleMarks(hellionMarks);
            }
        }else if(!(map.isVisible(currentPlayer,targetPlayer))){

            throw new NotVisibleTarget();
        }else if(!(map.distance(currentPlayer,targetPlayer)>0)){

            throw new NotValidDistance();
        }
    }
}




