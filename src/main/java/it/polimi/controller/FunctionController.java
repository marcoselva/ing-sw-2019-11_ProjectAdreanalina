package it.polimi.controller;


import it.polimi.model.*;
//chiedere perche devo importare tutto
import it.polimi.model.Exception.*;
import it.polimi.model.PowerUp.Newton;
import it.polimi.model.PowerUp.TagBackGrenade;
import it.polimi.model.PowerUp.TargetingScope;
import it.polimi.model.PowerUp.Teleporter;
import it.polimi.model.Weapon.LockRifle;
import it.polimi.model.Weapon.PlasmaGun;
import it.polimi.view.RemoteView;

import java.rmi.RemoteException;
import java.util.ArrayList;


public class FunctionController {
    
    WeaponController weaponController;
    FunctionModel functionModel;
    
    public FunctionController(FunctionModel functionModel){
        
        this.functionModel=functionModel;
        weaponController = new WeaponController(this);
        
    }
    
    public void lobby(){
    
        GameModel gameModel = this.functionModel.getGameModel();
    
        try {
            //todo marco guarda che ho spostato la creazione del player dentro l'add observer del modello.
            //ricordati di sistemare la cosa del colore
            System.out.println(gameModel.getActualPlayer().toString());
            
            if (gameModel.getPlayers(true).size() == 3) {
             
                //gestione quando non ci sono abbastanza player
                } else if (gameModel.getPlayers(true).size() == 1) {
                
                    // game can start
                    drawnPowerUp();
                    //for the moment add another player for testing army
                    Player player1 = new Player(2,"andrea",EnumColorPlayer.PINK);
                    Player player2 = new Player(3,"simone",EnumColorPlayer.BLU);
                    Player player3 = new Player(4,"niko",EnumColorPlayer.YELLOW);
                    Player player4 = new Player(5,"teo",EnumColorPlayer.GREEN);
                    
                    //add on square
                    try {
                        gameModel.getMap().addPlayerOnSquare(gameModel.getMap().getSquare(1,0),player1);
                        gameModel.getMap().addPlayerOnSquare(gameModel.getMap().getSquare(2,1),player2);
                        gameModel.getMap().addPlayerOnSquare(gameModel.getMap().getSquare(1,0),player3);
                        gameModel.getMap().addPlayerOnSquare(gameModel.getMap().getSquare(0,0),player4);
                        gameModel.getPlayers(true).add(player1);
                        gameModel.getPlayers(true).add(player2);
                        gameModel.getPlayers(true).add(player3);
                        gameModel.getPlayers(true).add(player4);
                    } catch (MapException e) {
                        e.printStackTrace();
                    }
                    
                    gameModel.getPlayers(true).get(0).getPlayerBoard().addWeapon(new LockRifle());
                    
                    gameModel.setState(State.SPAWNPLAYER);
                    
                } else {
                
                    gameModel.setState(State.LOBBY);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
    }
    
    public void drawnPowerUp () throws RemoteException {
        
        GameModel gameModel = this.functionModel.getGameModel();
        Player actual = gameModel.getActualPlayer();
            
        ArrayList<PowerUpCard> tempPowerUp = new ArrayList<>();
        tempPowerUp.add(gameModel.getPowerUpDeck().drawnPowerUpCard());
        tempPowerUp.add(gameModel.getPowerUpDeck().drawnPowerUpCard());
        System.out.println(tempPowerUp.toString());
        actual.setPowerUpCardsSpawn(tempPowerUp);
        
        System.out.println(gameModel.getActualPlayer().toString());
    }
    
    
    public void choseAction(RemoteView view){
    
        int choice;
        
        try {
            
            choice = view.getIndex();
            
            switch (choice){
                
                case -1:
                    this.functionModel.getGameModel().setState(State.MENU);
                    break;
                case 1:
                    this.functionModel.getGameModel().setState(State.SELECTRUN);
                    break;
                case 2:
                    this.functionModel.getGameModel().setState(State.SELECTGRAB);
                    break;
                case 3:
                    
                    if(this.functionModel.getGameModel().getActualPlayer().getPlayerBoard().getPlayerWeapons().size()>0) {
                        
                        this.functionModel.getGameModel().setState(State.SELECTWEAPON);
                    } else {
                        
                        this.functionModel.getGameModel().setMessageToCurrentView("YOU HAVE NOT WEAPON TO SHOOT");
                        this.functionModel.getGameModel().setBeforeError(this.functionModel.getGameModel().getState());
                        this.functionModel.getGameModel().setState(State.ERROR);
                        
                    }
                    break;
                case 4:
                    
                    if(this.functionModel.getGameModel().getActualPlayer().getPlayerBoard().getPlayerPowerUps().size()>0){
    
                        this.functionModel.getGameModel().setState(State.SELECTPOWERUP);
                    } else {
        
                        this.functionModel.getGameModel().setMessageToCurrentView("YOU HAVE NOT POWER UP TO USE");
                        this.functionModel.getGameModel().setBeforeError(this.functionModel.getGameModel().getState());
                        this.functionModel.getGameModel().setState(State.ERROR);
                
                    }
            }
            
            
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    
    }
    
    
    public void errorState() throws RemoteException {
        
        System.out.println("ERROR STATE-->\n"+"ERROR MESSAGE: "+ functionModel.getGameModel().getErrorMessage() +"\nRESTART IN STATE CHOICE STATE-->");
       
        switch (this.functionModel.getGameModel().getBeforeError()){
            
            case SELECTEFFECT:
                this.functionModel.getGameModel().setState(State.SELECTEFFECT);
                break;
            case SHOOT:
                this.functionModel.getGameModel().setState(State.SELECTEFFECT);
                break;
                
            default:
                this.functionModel.getGameModel().setState(State.CHOSEACTION);
                break;
                
            
        }
    }
    
    public void startTurn() throws RemoteException {
        
        //set the lowest id to the current player
        this.functionModel.getGameModel().setActualPlayer(this.functionModel.getGameModel().getPlayers(true).get(0));
    
        //put ammo and weapon card
        refreshMapEndTurn();
        
        //now game can start
        this.functionModel.getGameModel().setState(State.CHOSEACTION);
        
    }
    
    private void refreshMapEndTurn(){
    
        //PUT CARD
        this.functionModel.refreshMapAmmoCard();
        this.functionModel.refreshMapWeaponCard();
        
    }
    
    
    //state gestor and map error gestor
    public void setErrorState(String string){
        
        if (functionModel.getGameModel().getAvailableEffect().contains(WeaponsEffect.BaseEffect) ||functionModel.getGameModel().getAvailableEffect().contains(WeaponsEffect.BaseMode)){
            functionModel.getGameModel().getAvailableEffect().removeAll(functionModel.getGameModel().getAvailableEffect());
        }
        this.functionModel.getGameModel().setErrorMessage(string);
        this.functionModel.getGameModel().setBeforeError(this.functionModel.getGameModel().getState());
        this.functionModel.getGameModel().setState(State.ERROR);
    }
    
    public void mapErrorGestor() throws RemoteException {
        
        this.functionModel.getGameModel().setMessageToCurrentView("YOUR INPUT IS NOT VALID");
        this.functionModel.getGameModel().setBeforeError(this.functionModel.getGameModel().getState());
        this.functionModel.getGameModel().setState(State.ERROR);
    }
    
    
    public void runActionController (RemoteView view) throws RemoteException {
        
        //take necessary
        Map map= this.functionModel.getGameModel().getMap();
        Square inputSquare;
        
        try {
            
            inputSquare = map.getSquare(view.getRow(),view.getColumn());
            
            if(map.existInMap(inputSquare)) {
                
                this.functionModel.runFunctionModel(inputSquare);
                view.resetInput();
                this.functionModel.getGameModel().setMessageToAllView("CURRENT PLAYER " + this.functionModel.getGameModel().getActualPlayer().getName().toString() +" MOVED IN SQUARE: " + inputSquare.toString());
                this.functionModel.getGameModel().setState(State.RUN);
                
            }
        } catch (MapException e) {
    
            mapErrorGestor();
            
        } catch (RunActionMaxDistLimitException e) {
            
            this.functionModel.getGameModel().setMessageToCurrentView("YOUR MOVE EXCED MAX DISTANCE LIMIT");
            this.functionModel.getGameModel().setBeforeError(this.functionModel.getGameModel().getState());
            this.functionModel.getGameModel().setState(State.ERROR);
        }
        
    }
    
    public void run() throws RemoteException {
    
        this.functionModel.getGameModel().setState(State.CHOSEACTION);
        
    }
    
    
    public void grabActionController (RemoteView view) throws RemoteException{
    
        //take necessary
        Map map = this.functionModel.getGameModel().getMap();
        
        
        //answer to view an input Square
        Square inputSquare;
        
        try {
            inputSquare = map.getSquare(view.getRow(), view.getColumn());
    
            int indexWeapon = -1;
    
            //temp variables
            if (map.existInMap(inputSquare)) {
        
            try {
                
                //guardo se la square è di generation, se si devo chidere alla view l'index dell'arma
                if (map.isGenerationSquare(inputSquare)) {
                    
                    //effective catch gia con l'index giusto se è una Generation Square
                    indexWeapon = view.getIndex();
                    this.functionModel.grabActionModel(inputSquare, indexWeapon);
                    view.resetInput();
                    this.functionModel.getGameModel().setState(State.GRAB);
                    
                } else {
    
                    NormalSquare asNormal = (NormalSquare) inputSquare;
                    if (asNormal.containAmmoCard()) {
    
                        this.functionModel.grabActionModel(inputSquare, indexWeapon);
                        view.resetInput();
                        this.functionModel.getGameModel().setState(State.GRAB);
                    } else {
                        
                        setErrorState("THIS SQUARE NOT CONTAIN AMMO CARD TO GRAB");
                    }
                }
        
                
                } catch(GrabActionMaxDistLimitException catchActionMaxDistExpetion){
    
                    this.functionModel.getGameModel().setMessageToCurrentView("YOUR MOVE FOR GRAB EXCED MAX DISTANCE LIMIT");
                    this.functionModel.getGameModel().setBeforeError(this.functionModel.getGameModel().getState());
                    this.functionModel.getGameModel().setState(State.ERROR);
    
                } catch(GrabActionFullObjException e){
    
                    this.functionModel.getGameModel().setMessageToCurrentView("YOU DON'T HAVE MORE SPACE FOR GRAB OBJECT");
                    this.functionModel.getGameModel().setBeforeError(this.functionModel.getGameModel().getState());
                    this.functionModel.getGameModel().setState(State.ERROR);
                } catch(MapException e){
    
                    mapErrorGestor();
                }
            }else{
    
                mapErrorGestor();
            }
        } catch(MapException e){
    
            mapErrorGestor();
        }
        
    }
    
    
    
    public void grab() throws RemoteException {
    
        this.functionModel.getGameModel().setState(State.CHOSEACTION);
    }
    
    public void selectPowerUp(RemoteView view) throws RemoteException {
        
        PowerUpCard powerUpCard;
        
        int i;
        
        i = view.getIndex();
        
        if (this.functionModel.getGameModel().getActualPlayer().getPlayerBoard().getPlayerPowerUps().get(i) != null) {
            
            powerUpCard = this.functionModel.getGameModel().getActualPlayer().getPlayerBoard().getPlayerPowerUps().get(i);
            
            if(!powerUpCard.getNameCard().equals("TAGBACK GRENADE")) {
                
                this.functionModel.getGameModel().setPowerUpSelected(powerUpCard);
                this.functionModel.getGameModel().setState(State.SELECTPOWERUPINPUT);
            } else {
                
                this.functionModel.getGameModel().setMessageToCurrentView("YOU CAN'T USE TAGBACK GRENADE IN YOUR TURN");
                this.functionModel.getGameModel().setBeforeError(this.functionModel.getGameModel().getState());
                this.functionModel.getGameModel().setState(State.ERROR);
            }
        } else {
            
            mapErrorGestor();
        }
        
    }
    
    public void usePowerUpController(RemoteView view) throws RemoteException{

        //NEWTON
        if (Newton.class.equals(this.functionModel.getGameModel().getPowerUpSelected().getClass())) {
            Player targetPlayer;
            Square targetSquare;
            
            try {
                //get input
                targetPlayer = this.functionModel.getGameModel().getPlayerById(view.getTarget1());
                targetSquare = this.functionModel.getGameModel().getMap().getSquare(view.getRow(), view.getColumn());
                //effect
                    this.functionModel.usePowerUpNewton((Newton)this.functionModel.getGameModel().getPowerUpSelected(),targetPlayer, targetSquare);
            } catch (NotInSameDirection notInSameDirection) {


            } catch (NotValidDistance notValidDistance) {

  
            } catch (MapException e) {
    
            }
            //TAGBACK GRANATE
        } else if (this.functionModel.getGameModel().getPowerUpSelected().getClass().equals(TagBackGrenade.class)) {
    
            Player targetPlayer;
            try {
                
                //get input
                targetPlayer = this.functionModel.getGameModel().getPlayerById(view.getTarget1());
                //effect
                this.functionModel.usePowerUpTagBackGrenade((TagBackGrenade) this.functionModel.getGameModel().getPowerUpSelected(), targetPlayer);
            } catch (NotVisibleTarget notVisibleTarget) {

            } catch (MapException e) {
            
            }
    
    
            //TELEPORTER
        } else if (this.functionModel.getGameModel().getPowerUpSelected().getClass().equals(Teleporter.class)) {
    
            Square targetSquare;
            try {
    
                //get input
                targetSquare = this.functionModel.getGameModel().getMap().getSquare(view.getRow(), view.getColumn());
                //effect
                this.functionModel.usePowerUpTeleporter((Teleporter) this.functionModel.getGameModel().getPowerUpSelected(), targetSquare);
            }catch (MapException e) {
                e.printStackTrace();
            }
    
            //TARGETING SCOPE
        } else if (this.functionModel.getGameModel().getPowerUpSelected().getClass().equals(TargetingScope.class)) {
    
            Player targetPlayer;
            
            try {
                
                //get input
                targetPlayer = this.functionModel.getGameModel().getPlayerById(view.getTarget1());
                //effect
                this.functionModel.usePowerUpTargetingScope((TargetingScope) this.functionModel.getGameModel().getPowerUpSelected(), targetPlayer);
            } catch (MapException e) {
            
            }
        }
    }
    
    public void rechargeController(Player player, ArrayList<WeaponCard> weapon,RemoteView view){
    
        int viewSelection;
        WeaponCard weaponToCharge;
        
        try {
        //fino a che ho armi disponibili
        while (weapon.size()>0) {

            // faccio scegliere al player quali armi sono scariche,
            // mi tornerà un index, che setto qui sotto
            viewSelection = view.getIndex();
            
            if(weapon.get(viewSelection)!=null) {
    
                weaponToCharge = weapon.get(viewSelection);
                //TODO AVANTI DA QUI
                //prendo il costo di ricarica
                ArrayList<EnumColorCardAndAmmo> rechargeCost = weaponToCharge.getRechargeCost();
                
                payAmmoController(player, rechargeCost,weaponToCharge,view);
                weaponToCharge.setCharge(true);
                weapon.remove(0);
            } else {
                
                //errore input
            }
            
        }
        } catch (NotValidAmmoException e) {
        
        } catch (NoPowerUpAvailable noPowerUpAvailable) {
            noPowerUpAvailable.printStackTrace();
        } catch (RemoteException e) {
        
        }
    }
    
    public void payAmmoController (Player player, ArrayList<EnumColorCardAndAmmo> ammoToPay,WeaponCard weaponCard,RemoteView view) throws NotValidAmmoException, NoPowerUpAvailable, RemoteException {

        //prendo playerboard
        PlayerBoard playerBoard = player.getPlayerBoard();

        //var temporanee
        ArrayList<EnumColorCardAndAmmo> availableAmmo = new ArrayList<>(playerBoard.getAmmo());
        ArrayList<EnumColorCardAndAmmo> availablePowerUpAsAmmo = new ArrayList<>();
    
        if (playerBoard.getAmmo().containsAll(weaponCard.getRechargeCost())) {
            
            //pago e rendo carica l'arma
            playerBoard.decreaseAmmo(ammoToPay);

        } else {

            // non bastano le semplici ammo
            //verifico allora se usando i power up può pagare,
            if (availablePowerUpAsAmmo.size()==0){
        
                throw  new NoPowerUpAvailable();
            } else {
                
                for (PowerUpCard a : playerBoard.getPlayerPowerUps()) {
        
                    availablePowerUpAsAmmo.add(a.getColorPowerUpCard());
                }
            }

            ArrayList<EnumColorCardAndAmmo> tempAvaible = new ArrayList<>();
            tempAvaible.addAll(availableAmmo);
            tempAvaible.addAll(availablePowerUpAsAmmo);
            
            if (tempAvaible.containsAll(ammoToPay)) {

                // posso pagare usando ammo e power up

                // chiedo alla view se lo vuole fare
                Boolean viewAnswer = view.isBooleanChose();

                if (viewAnswer) {
    
                    for (int i = 0; i < ammoToPay.size(); i++) {
                        EnumColorCardAndAmmo a = ammoToPay.get(i);
        
                        if (availableAmmo.contains(a)) {
            
                            playerBoard.decreaseAmmo(a);
                        } else if (availablePowerUpAsAmmo.contains(a)) {
            
                            playerBoard.getPlayerPowerUps().remove(a);
                        } else {
                            throw new NotValidAmmoException();
                        }
                    }
                }
            } else {
                throw new NotValidAmmoException();
            }
        }
        if(weaponCard!=null){
            weaponCard.setCharge(true);
        }
    }
    
    public void respawnPlayerController ( RemoteView view){
        
        int chosedPowerUp;
        EnumColorSquare colorSquare;
        PowerUpCard  powerUpCard;
        Player player = this.functionModel.getGameModel().getActualPlayer();
        
        
        try {
            
            chosedPowerUp = view.getIndex();
            
            if(player.getPowerUpCardsSpawn().get(chosedPowerUp)!=null){
                
                //get the color to respawn
                colorSquare = player.getPowerUpCardsSpawn().get(chosedPowerUp).getColorRespawn();
                player.getPowerUpCardsSpawn().remove(chosedPowerUp);
                //add player on generation square of the color chosed
                this.functionModel.getGameModel().getMap().addPlayerOnSquare(this.functionModel.getGameModel().getMap().getGenerationSquare(colorSquare),player);
                
                //add the other power up to player list
                powerUpCard = player.getPowerUpCardsSpawn().get(0);
                player.getPlayerBoard().getPlayerPowerUps().add(powerUpCard);
                player.getPowerUpCardsSpawn().remove(0);
    
                this.functionModel.getGameModel().setState(State.STARTTURN);
                
            } else {
                
                //errore di input
            }
            
        } catch (RemoteException e) {
        
        } catch (MapException e) {
        
        }
    }
    
    
    public void scoringPlayerBoardController (){
        
        //get dead Player
        ArrayList<Player> deadPlayer = this.functionModel.getGameModel().getDeadPlayers();
        
        for (Player a:deadPlayer){
            //incasso una plancia alla volta e gestisco le mort
            this.functionModel.scoringPlayerBoard(a);
        }
    
    }
    
    
}


