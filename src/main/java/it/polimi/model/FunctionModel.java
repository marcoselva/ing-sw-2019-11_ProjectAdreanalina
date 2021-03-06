package it.polimi.model;

import it.polimi.model.Exception.*;
import it.polimi.model.PowerUp.Newton;
import it.polimi.model.PowerUp.TagBackGrenade;
import it.polimi.model.PowerUp.TargetingScope;
import it.polimi.model.PowerUp.Teleporter;


import java.io.Serializable;
import java.util.*;

/**
 * The type Action model.
 */
public class FunctionModel implements Serializable {

    private GameModel gameModel;
    private int action = 0;
    private Map map;

    public FunctionModel (){
        
        this.gameModel = new GameModel();
      
        this.map=gameModel.getMap();

    }
    
    
    /**
     * Gets action number.
     *
     * @return the action number
     */
    public int getAction() {

        return action;
    }
    
    /**
     * Gets gameModel model.
     *
     * @return the gameModel model
     */
    public GameModel getGameModel() {

        return gameModel;
    }
    
    /**
     * Run Action.
     *
     * @param targetSquare the target square to move
     * @throws RunActionMaxDistLimitException the run action max dist limit exception
     */
    public void runFunctionModel (Square targetSquare) throws RunActionMaxDistLimitException, MapException {

        Player current = gameModel.getActualPlayer();
        Square playerSquare = map.findPlayer(current);
        //adrealinic distance
        int maxDist;
    
        if (current.getPlayerBoard().getDamages().size() < 2) {
        
            maxDist = 4;
        } else {
        
            maxDist = 5;
            gameModel.setMessageToCurrentView("YOUR ARE USING ADRENALIN MODE FOR RUN");
        }
        
        if (map.distance(playerSquare,targetSquare) < maxDist) {

            map.movePlayer(current, targetSquare);
            action++;
        } else {

            //run action not valid
            throw new RunActionMaxDistLimitException();
        }
    }
    
    /**
     * Grab Action.
     *
     * @param targetSquare the target square where grab
     * @param weaponIndex  the weapon index position in generation square
     * @throws GrabActionMaxDistLimitException the catch action max dist limit exception
     */
    public void grabActionModel(Square targetSquare, int weaponIndex) throws GrabActionMaxDistLimitException, MapException, GrabActionFullObjException {

        //adrenalinic distance
        int maxDist;
        Player actual = gameModel.getActualPlayer();

        if (actual.getPlayerBoard().getDamages().size() < 2) {

            maxDist = 1;
        } else {

            maxDist = 2;
            gameModel.setMessageToCurrentView("YOUR ARE USING ADRENALIN MODE FOR GRAB");
        }
        if (map.distance(map.findPlayer(actual), targetSquare) <= maxDist) {

            map.movePlayer(actual, targetSquare);
            
            if (!map.isGenerationSquare(targetSquare) && actual.getPlayerBoard().getPlayerPowerUps().size() <3) {
    
                actual.catchAmmoCard(((NormalSquare) map.findPlayer(actual)).catchAmmoCard());
    
            } else if ((map.isGenerationSquare(targetSquare)) && (actual.getPlayerBoard().getPlayerWeapons().size() <3 )&& (weaponIndex<(((GenerationSquare) map.findPlayer(actual)).getWeaponList().size()) )){
    
                actual.getPlayerBoard().addWeapon(((GenerationSquare) map.findPlayer(actual)).catchWeapon(weaponIndex));
            
            } else {

                throw new  GrabActionFullObjException();
            }
        } else {

            throw new GrabActionMaxDistLimitException();
        }
        getGameModel().setMessageToAllView("CURRENT PLAYER " + getGameModel().getActualPlayer().getName().toString() +" GRABED IN SQUARE: " + targetSquare.toString());
    }
    
    
    /**
     * Check the number in the turn.
     *
     * @return true if can do action, else otherwise
     */
    public boolean checkActionCount() {
        
        if (action == 1) {

            //gameModel.setState(State.ACTION1);//todo commento perchè non mi compila
            return true;
        } else if (action == 2) {

            //gameModel.setState(State.ACTION2);todo commento perchè non mi compila
            return true;
        } else {
            return false;
        }
    }
    
    
    /**
     * Use power up Newton.
     *
     * @param newton       the newton powerUp
     * @param targetPlayer the target player
     * @param targetSquare the target square
     * @throws NoPowerUpAvailable   No power up avaible
     * @throws NotInSameDirection Not in same direction
     * @throws NotValidDistance   Not valid distance
     */
    public void usePowerUpNewton(Newton newton, Player targetPlayer, Square targetSquare) throws  NotInSameDirection, NotValidDistance, MapException {
        
        newton.effect(gameModel.getMap(), targetSquare, targetPlayer);

    }
    
    /**
     * Use power up Teleporter.
     *
     * @param teleporter   the teleporter PowerUp
     * @param targetSquare the target square
     * @throws NoPowerUpAvailable the no power up avaible
     */
    public void usePowerUpTeleporter(Teleporter teleporter, Square targetSquare) throws  MapException {
        
        teleporter.effect(gameModel.getActualPlayer(), gameModel.getMap(), targetSquare);

    }
    
    /**
     * Use power up Targeting Scope.
     *
     * @param targetingScope the targeting scope PowerUp
     * @param targetPlayer   the target player
     * @throws NoPowerUpAvailable NO power up available
     */
    public void usePowerUpTargetingScope(TargetingScope targetingScope, Player targetPlayer) {
        
        targetingScope.effect(gameModel.getActualPlayer(),targetPlayer);

    }
    
    /**
     * Use power up Tag Back Grenade.
     *
     * @param tagBackGrenade the tag back grenade powerUp
     * @param targetPlayer   the target player
     * @throws NoPowerUpAvailable No power up avaible
     * @throws NotVisibleTarget Not visible target
     */
    public void usePowerUpTagBackGrenade(TagBackGrenade tagBackGrenade, Player targetPlayer) throws NotVisibleTarget  {
        
        tagBackGrenade.effect(gameModel.getMap(), gameModel.getActualPlayer(), targetPlayer);
    }
    
  
    
    /**
     * The type Player score, useful for scoring computation.
     */
    public class PlayerScore implements Comparable<PlayerScore>{
        
        private EnumColorPlayer color;
        private int value;
        
        @Override
        public int compareTo(PlayerScore o) {
            
            return (this.getValue()-o.getValue());
        }
    
        @Override
        public String toString () {
        
            return (this.getValue() + this.getColor().toString());
        }
    
        /**
         * Instantiates a new Player score.
         *
         * @param color the color of the player
         * @param value color value in point.
         */
        public PlayerScore(EnumColorPlayer color, int value){
            
            this.color=color;
            this.value=value;
        }
    
        /**
         * Gets color of the player.
         *
         * @return the color
         */
        public EnumColorPlayer getColor () {
        
            return color;
        }
    
        /**
         * Gets value of the color player.
         *
         * @return the value
         */
        public int getValue () {
        
            return value;
        }
    
        /**
         * Sets value.
         *
         * @param value the value
         */
        public void setValue (int value) {
        
            this.value = value;
        }
        
    }
    
    /**
     * Calculate damages point, valuating occurrence and tie
     *
     * @param player the player that have PlayerBoard to scoring
     * @return an Array list in order by the point to add to single player with same color.
     */
    public ArrayList<EnumColorPlayer> damagesOrderColor (Player player) {
        
        //create temp variables
        PlayerBoard playerBoard = player.getPlayerBoard();
        ArrayList<PlayerScore> playerScores = new ArrayList<PlayerScore>();
        
        ArrayList<EnumColorPlayer> playerColor = new ArrayList<EnumColorPlayer>(gameModel.getPlayerColor());
        
        //get color occurrence for all player in gameModel
        for (EnumColorPlayer a : playerColor) {
            
            if(playerBoard.colorOccurrenceInDamages(a)>0) {
                playerScores.add(new PlayerScore(a, playerBoard.colorOccurrenceInDamages(a)));
            }
        }
        
        //order in descending
        Collections.sort(playerScores);
        Collections.reverse(playerScores);
        
        //calculate the tie
        for (int i = 0; i < playerScores.size(); i++) {
            if(i<playerScores.size()-1) {
                PlayerScore cur = playerScores.get(i);
                PlayerScore next = playerScores.get(i + 1);
        
                if (cur.getValue() == next.getValue()) {
                    if (playerBoard.getFirstOccurrenceInDamage(cur.getColor()) > playerBoard.getFirstOccurrenceInDamage(next.getColor())) {
                        Collections.swap(playerScores, i, i + 1);
                    }
                }
            }
        }
        
        //create array of color in order of occurrence, calculated tie
        ArrayList<EnumColorPlayer> playerOrder = new ArrayList<>();
        
        for (PlayerScore a:playerScores){
            
            playerOrder.add(a.getColor());
        }
        return playerOrder;
    }
    
    /**
     * Scoring player board.
     *
     * @param player the player that have PlayerBoard to Score.
     */
    public void scoringPlayerBoard(Player player) {
    
        PlayerBoard playerBoard = player.getPlayerBoard();
        if (playerBoard.getDamages().size() == 11 || playerBoard.getDamages().size() == 12) {
            //player color in order by occurrence (tie calculated)
            ArrayList<EnumColorPlayer> playerOrderDamage = new ArrayList<>(damagesOrderColor(player));
            //fist blood
            EnumColorPlayer firstBlood = playerBoard.getDamages().get(0);
            //death
            EnumColorPlayer death = playerBoard.getDamages().get(10);
            //overkill
            EnumColorPlayer overkill = null;
            if (playerBoard.getDamages().size() == 12) {
                overkill = playerBoard.getDamages().get(11);
            }
    
            //player point
            ArrayList<PlayerScore> playerPoint = new ArrayList<PlayerScore>();
    
            int temp = 0;
            int pointTo = 0;
            //create danno
            switch (playerBoard.getBoardValue()) {
                case 8:
                    pointTo = 10;
                    temp = pointTo;
                    for (EnumColorPlayer a : playerOrderDamage) {
    
                        //decrement point to ad to the a player
                        temp -= 2;
                        if (temp < 2) {
    
                            temp = 1;
                        }
                        //add point if first blood
                        if (a == firstBlood) {
    
                            temp++;
                        }
                        playerPoint.add(new PlayerScore(a, temp));
                    }
    
                    break;
                case 6:
                    pointTo = 8;
                    for (EnumColorPlayer a : playerOrderDamage) {
    
                        temp -= 2;
                        if (temp < 2) {
    
                            pointTo = 1;
                            playerPoint.add(new PlayerScore(a, pointTo));
                        }
                    }
                    break;
                case 4:
                    pointTo = 6;
                    for (EnumColorPlayer a : playerOrderDamage) {
    
                        temp -= 2;
                        if (temp < 2) {
    
                            pointTo = 1;
                            playerPoint.add(new PlayerScore(a, pointTo));
                        }
                    }
                    break;
                case 2:
                    pointTo = 4;
                    for (EnumColorPlayer a : playerOrderDamage) {
    
                        temp -= 2;
                        if (temp < 2) {
    
                            pointTo = 1;
                            playerPoint.add(new PlayerScore(a, pointTo));
                        }
                    }
                    break;
                case 1:
                    pointTo = 2;
                    for (EnumColorPlayer a : playerOrderDamage) {
    
                        temp -= 2;
                        if (temp < 2) {
    
                            pointTo = 1;
                            playerPoint.add(new PlayerScore(a, pointTo));
                        }
                    }
                    break;
            }
    
            //share point to player in player point
            for (PlayerScore a : playerPoint) {
    
                gameModel.getPlayerByColor(a.getColor()).increaseScore(a.value);
            }
    
            //put color in killshot track
            if (gameModel.getKillShotTrack().skullNumber() >= 1) {
    
                ArrayList<EnumColorPlayer> toKillShot = new ArrayList<>();
    
                if (overkill != null) {
        
                    toKillShot.add(death);
                    toKillShot.add(overkill);
                    gameModel.getPlayerByColor(overkill).singleMark(player.getColor());
                } else {
        
                    toKillShot.add(death);
                }
    
                //update the killshot track point
                gameModel.getKillShotTrack().updateTrack(toKillShot);
                playerBoard.resetDamage();
                playerBoard.decreaseBoardValue();
    
            }
        }
    }
    
    
    //scoring
    public  ArrayList<EnumColorPlayer> killShotScoring (){
        
        KillShotTrack killShotTrack= gameModel.getKillShotTrack();
        ArrayList<EnumColorPlayer> playerColor = new ArrayList<>(gameModel.getPlayerColor());
        ArrayList<FunctionModel.PlayerScore> playerScores = new ArrayList<FunctionModel.PlayerScore>();
        
        //get color occurrence for all player in gameModel
        for (EnumColorPlayer a : playerColor) {
            
            if(killShotTrack.colorOccurence(a)>0) {
                playerScores.add(new FunctionModel.PlayerScore(a, killShotTrack.colorOccurence(a)));
            }
        }
        
        //order in descending
        Collections.sort(playerScores);
        Collections.reverse(playerScores);
        
    
        //create array of color in order of occurrence
        ArrayList<EnumColorPlayer> playerOrder = new ArrayList<>();
    
        for (PlayerScore a:playerScores){
        
            playerOrder.add(a.getColor());
        }
        return playerOrder;
        
    }
    
    
    public void finalScoring (){
        
        ArrayList<Player> players = new ArrayList<>(gameModel.getPlayers(true,true));
        for (int i = 0; i < players.size(); i++) {
            Player a = players.get(i);
        
            if (gameModel.getRemoteViews().get(i)==null){
                players.remove(a);
                i--;
            }
        
        }
        ArrayList<EnumColorPlayer> killShotPoint = new ArrayList<>(killShotScoring());
        
        for (int i = 0; i < players.size(); i++) {
            Player a = players.get(i);
        
            if(killShotPoint.contains(a.getColor()) && killShotPoint.indexOf(a.getColor())==0){
        
                a.increaseScore(8);
            }
            if(killShotPoint.contains(a.getColor()) && killShotPoint.indexOf(a.getColor())==1){
        
                a.increaseScore(6);
            }
            if(killShotPoint.contains(a.getColor()) && killShotPoint.indexOf(a.getColor())==2){
        
                a.increaseScore(4);
            }
            if(killShotPoint.contains(a.getColor()) && killShotPoint.indexOf(a.getColor())==3){
        
                a.increaseScore(2);
            }
            if(killShotPoint.contains(a.getColor()) && killShotPoint.indexOf(a.getColor())==4){
        
                a.increaseScore(1);
            }
        }
        
        ArrayList<PlayerScore> score = new ArrayList<>();
        
        for (Player a : players){
            
            score.add(new PlayerScore(a.getColor(),a.getScore()));
        }
        
        Collections.sort(score);
        Collections.reverse(score);
        
        ArrayList<Player> finalList = new ArrayList<>();
        
        for (PlayerScore a:score){
            
            finalList.add(gameModel.getPlayerByColor(a.color));
            
        }
        
        for (Player a : gameModel.getPlayers(true,true)){
            
            if (!finalList.contains(a)){
                finalList.add(a);
            }
        }
        
        gameModel.setFinalPlayersScoring(finalList);
        gameModel.setEndGame(true);
        gameModel.setState(State.FINALSCORING);
    }
    
    public void refreshMapAmmoCard(){

        for(Square s:map.getSquares()){

            if(!(map.isGenerationSquare(s))&&(((NormalSquare) s).getAmmoCard()==null)){

                AmmoCard ammoCardDrawn = gameModel.getAmmoDeck().drawAmmoCard(gameModel.getPowerUpDeck());
                ((NormalSquare) s).setAmmoCard(ammoCardDrawn);
            }
        }
    
    }
    
    public void refreshMapWeaponCard(){

        for (Square s : map.getSquares()) {

            if (map.isGenerationSquare(s)) {

                ArrayList<WeaponCard> weaponList = ((GenerationSquare) s).getWeaponList();
                for (int i = weaponList.size(); i <3; i++) {

                    WeaponCard weaponCardDraw = gameModel.getWeaponDeck().drawWeaponCard();
                    if(weaponCardDraw!=null){

                        ((GenerationSquare) s).addWeaponCard(weaponCardDraw);
                    }
                }
            }
        }
    }
}


        


