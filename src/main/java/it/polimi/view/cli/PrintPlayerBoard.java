package it.polimi.view.cli;

import it.polimi.model.*;

import java.io.Serializable;

public class PrintPlayerBoard implements Serializable {

    /**
     * Print attributes of PlayerBoard.
     */
    public static void print(Player player){

        System.out.println();
        System.out.println("********************************");
        System.out.println("PLAYER BOARD:");
        System.out.println();
        PrintPlayerInfo.printColorPlayer(player);
        System.out.println("NAME:   " +player.getName());
        System.out.println("ID:     " +player.getId());
        System.out.println("VALUE:  " +player.getPlayerBoard().getBoardValue());
        System.out.print("DAMAGES: ");
        PrintDamagesAndMarks.printDamages(player);
        System.out.println();
        System.out.print("MARKS: ");
        PrintDamagesAndMarks.printMarks(player);
        System.out.println();
        System.out.print("AMMO: ");
        PrintEnumCardsAmmo.print(player.getPlayerBoard().getAmmo());
        System.out.println();
        System.out.println("********************************");
    }
}
