package it.polimi.view.cli;

import java.io.Serializable;

public class PrintMenu implements Serializable {

    /**
     * Print menu.
     */
    public static void print(){

        System.out.println();
        System.out.println("--------------------------------------------------------");
        System.out.println("WHAT MOVE DO YOU WANT TO MAKE?");
        System.out.println();
        System.out.println("1) CHOSE AN ACTION TO DO (RUN, GRAB OR SHOOT)!"); //printSelectMove
        System.out.println("2) VIEW YOUR ATTRIBUTES"); //id, name, position and score (printPlayer)
        System.out.println("3) VIEW YOUR PLAYERBOARD"); //damages, marks and ammo (printPlayerBoard)
        System.out.println("4) VIEW YOUR WEAPONS"); //take weapons from ActualPlayer (printWeapon)
        System.out.println("5) VIEW YOUR POWERUP"); //printPowerUp
        System.out.println("6) VIEW YOUR AMMO"); //only ammo (printAmmo)
        System.out.println("7) VIEW ANOTHER PLAYER'S ATTRIBUTES"); //id, name, position and score (printPlayer)
        System.out.println("8) VIEW ANOTHER PLAYER'S PLAYERBOARD"); //damages, marks and ammo (printPlayerBoard)
        System.out.println("9) VIEW OTHER WEAPONS ON THE MAP"); //take weapons from generation square (printWeapon)
        System.out.println("10) VIEW THE MAP");
        System.out.println("--------------------------------------------------------");
    }
}
