package it.polimi.view.cli;

import it.polimi.model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class PrintPlayer implements Serializable {

    /**
     * Print Player's attributes.
     * @param player   the player to print
     */
    public static void print(Player player){

        System.out.println();
        PrintPlayerInfo.print(player);
        PrintCoordinate.print(player);
        PrintScore.print(player);
        System.out.println("ALIVE: " +player.isAlive());
    }

    /**
     * Print Players' attributes.
     * @param players   the player to print
     */
    public static void print(ArrayList<Player> players){

        for(Player p : players){

            PrintPlayer.print(p);
        }
    }
}
