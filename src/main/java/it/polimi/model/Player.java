package it.polimi.model;

import java.util.ArrayList;

/**
 * The type Player.
 */
public class Player {
    
    private int id;
    private String name;
    private EnumColorPlayer color;
    private PlayerBoard playerBoard;
    private int row;
    private int column;
    private int score;
    private boolean alive;
    private boolean damaged;
    
    /**
     * Instantiates a new Player setting the id, name and color with the given parameters.
     * It creates a new player board for the player and settings score and the field alive to the start value.
     *
     * @param id    the id of the player
     * @param name  the name of the player
     * @param color the color of the player
     */
    public Player (int id, String name, EnumColorPlayer color) {
    
        this.id = id;
        this.name = name;
        this.color = color;
        score = 0;
        alive = true;
        damaged = false;
        playerBoard = new PlayerBoard();

        // TODO vedere come gestire posizione in fase di inizializzazione.io inizialmente le metterei a  null quando vengono istanziati
    
    }

    public Player(){

        //todo metodo da cancellare
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId () {
    
        return id;
    }
    
    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName () {
    
        return name;
    }
    
    /**
     * Gets color.
     *
     * @return the color
     */
    public EnumColorPlayer getColor () {
    
        return color;
    }
    
    /**
     * Gets player board.
     *
     * @return the player board
     */
    public PlayerBoard getPlayerBoard () {
    
        return playerBoard;
    }
    
    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore () {
    
        return score;
    }
    
    /**
     * Gets column.
     *
     * @return the column
     */
    public int getColumn () {
    
        return column;
    }
    
    /**
     * Gets row.
     *
     * @return the row
     */
    public int getRow () {
    
        return row;
    }
    
    /**
     * Gets the value of the field alive.
     *
     * @return true if the player is alive, false otherwise.
     */
    public boolean isAlive () {
    
        return alive;
    }

    /**
     * Gets the value of the field damaged.
     *
     * @return true if the player it was damaged, false otherwise.
     */
    public boolean isDamaged(){

        return damaged;
    }
    
    /**
     * Sets the column
     *
     * @param column the column we want to set to the player.
     */
    private void setColumn (int column) {
        
        this.column = column;
    }

    /**
     * Sets the row
     *
     * @param row the row we want to set to the player.
     */
    private void setRow (int row) {
        
        this.row = row;
    }
    
    /**
     * Sets position of player assigning a row and a column.
     *
     * @param r the row.
     * @param c the column.
     */
    public void setPosition (int r, int c) {
    
        this.setRow(r);
        this.setColumn(c);
    }
    
    /**
     * Sets the field alive.
     *
     * @param alive player status
     */
    public void setAlive (boolean alive) {
    
        this.alive = alive;
    }

    /**
     * Increase score of the player.
     *
     * @param scoreToAdd points to add at player score.
     */
    public void increaseScore (int scoreToAdd) {

        this.score += scoreToAdd;
    }
    
    public void catchAmmoCard(AmmoCard ammoCard){

        this.playerBoard.manageAmmoCard(ammoCard);
    }

    public void singleMark(EnumColorPlayer mark){

        this.playerBoard.increaseMarks(mark);

    }

    public void multipleMarks(ArrayList<EnumColorPlayer> marks){

        this.playerBoard.increaseMarks(marks);
    }

    public void singleDamage(EnumColorPlayer damage){

        this.playerBoard.increaseDamages(damage);
        this.playerBoard.shiftMarks(damage);
        this.damaged=true;

    }

    public void multipleDamages(ArrayList<EnumColorPlayer> damages){

        this.playerBoard.increaseDamages(damages);
        this.playerBoard.shiftMarks(damages.get(0));
        this.damaged=true;
    }

    public void multipleDamagesSingleMark(ArrayList<EnumColorPlayer> damages, EnumColorPlayer mark){

        this.playerBoard.increaseDamages(damages);
        this.playerBoard.shiftMarks(mark);
        this.playerBoard.increaseMarks(mark);
        this.damaged=true;
    }

    public void singleDamageMultipleMarks(EnumColorPlayer damage, ArrayList<EnumColorPlayer> marks) {

        this.playerBoard.increaseDamages(damage);
        this.playerBoard.shiftMarks(damage);
        this.playerBoard.increaseMarks(marks);
        this.damaged=true;
    }

    @Override
    public String toString () {

        return "Player: " + this.id + " name: " + this.name + " r: " + this.row + " c: " + this.column;
    }

    public void stampa(){

        System.out.print("id: "+this.id+"   ");
        System.out.print("name: "+this.name+"   ");
        System.out.print("color: "+this.color+"   ");
        //System.out.println("playboard: "+this.playerBoard);
        //System.out.println("row :"+this.row);
        //System.out.println("column: "+this.column);
        //System.out.println("score: "+this.score);
        //System.out.println("alive: "+this.alive);
        System.out.print("damaged: "+this.damaged+"   ");
        //System.out.println("ammo: "+playerBoard.getAmmo());
        //System.out.println("ammoY: "+playerBoard.getAmmoY());
        //System.out.println("ammoR: "+playerBoard.getAmmoR());
        //System.out.println("ammoB: "+playerBoard.getAmmoB());
        //System.out.println("boardvalue: "+playerBoard.getBoardValue());
        //System.out.println("deaths: "+playerBoard.getNumberOfDeaths());
        System.out.print("damages: "+playerBoard.getDamages()+"   ");
        System.out.print("marks: "+playerBoard.getMarks()+"   ");
        //System.out.println("weapons: "+playerBoard.getPlayerWeapons());
        //System.out.println("powerup :"+playerBoard.getPlayerPowerUps());
        System.out.println();
    }


}
