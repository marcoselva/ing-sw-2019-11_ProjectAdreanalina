package it.polimi.controller;

import it.polimi.model.*;
import it.polimi.model.Exception.ControllerException.RoudControllerException.SquareNotExistException;
import it.polimi.model.Exception.ModelException.RoundModelException.NoPowerUpAvailable;

import java.util.ArrayList;

public class ManagerController {
    
    private ActionController actionController;
    private ActionModel actionModel;
    private GameModel gameModel= actionModel.getGameModel();
    
    
    public void turn(){
        
        Player actualPlayer = gameModel.getActualPlayer();
        PlayerBoard actualPlayerBoard = actualPlayer.getPlayerBoard();
        
        //2 action and multiple power up use
        while ((actionModel.checkActionCount() || actualPlayerBoard.getPlayerPowerUps().size()>0) && !gameModel.getState().equals(State.ENDACTION)){
            //scegli una mossa, chiedi alla view
            State mossa = State.RUN  ;
            switch (mossa) {
                case RUN:
                    try {
                        
                        actionController.runActionController(actionModel, gameModel.getMap());
                    } catch (SquareNotExistException e) {
                        
                        //TODO
                    }
                case GRAB:
                    try {
                        
                        actionController.grabActionController(actionModel, gameModel.getMap());
                    } catch (SquareNotExistException e) {
                        
                        //TODO
                    }
                case POWERUP:
                    //chiedo che power up vuole usare
                    ArrayList<PowerUpCard> powerUpAvailable = gameModel.getActualPlayer().getPlayerBoard().getPlayerPowerUps();
    
                    //chiedo al player qualche vuole usare
                    PowerUpCard usedPowerUp = powerUpAvailable.get(0);
                    try {
                        
                        actionController.usePowerUpController(actionModel,usedPowerUp);
                    } catch (NoPowerUpAvailable noPowerUpAvailable) {
                        
                        //TODO
                    }
                case SHOOT:
                    //prendo le armi che ho, le mostro alla vieee che decide cosa usare
                    
                    //es
                    String string="Lock Rifle ";
                    
                    
                    switch (string){
                        case "ELECTOSCYTHE":
                            //Electroscythe electroscythe = (Electroscythe) getWeaponPlayer(gameModel.getActualPlayer(),string);//todo ti commento perchè npn mi compila
                           // actionController.ElectroscytheWeapon(gameModel,electroscythe);
                            
                        case "LOCKRIFLE":
                           // LockRifle lockRifle = (LockRifle) getWeaponPlayer(gameModel.getActualPlayer(),string);//todo ti commento perchè npn mi compila
                           // actionController.LockRifleweapon(gameModel,lockRifle);
                            
                    }
    
                case ENDACTION:
                    gameModel.setState(State.ENDACTION);
    
                default:
                    //TODO
            }
        }
        
        if(gameModel.getState().equals(State.ENDACTION)){
            
            //vedo se posso ricaricare ricarica
            if(actualPlayerBoard.getWeaponToCharge().size()>0){
                
                //chiedi alla view se vuoi ricaricare??
                State recharge=State.RECHARGE;
                //nel caso la view voglia ricaricare
                
                gameModel.setState(State.RECHARGE);
                // se si chiama metodo che verfica se puoi ricarcaire, lui ricaciehraà
                
                if(recharge==State.RECHARGE){
                    
                    actionController.rechargeController(actualPlayer,actualPlayerBoard.getWeaponToCharge());
                }
                gameModel.setState(State.PASSTURN);
            }
        }
        if(gameModel.getState().equals(State.PASSTURN)){
            //verifico se ci sono givaori mortI, se si, processo di respawn
            
            if(gameModel.getDeadPlayers().size()>0){
                gameModel.setState(State.PLAYERBOARDSCORING);
                //PRIMA INCASSO PLANCE DI TUTTI POI RIANIMO TUTTI
    
                // fase incasso plancie
                actionController.scoringPlayerBoardController(actionModel);
                
                //fase di rianimazione
                actionController.respawnPlayerController(actionModel);
                
            }
        }
    }
    
    public GameModel getGameModel(){
        return this.gameModel;
    }
}
