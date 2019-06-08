package it.polimi.view;

import it.polimi.model.EnumCardinalDirection;
import it.polimi.model.EnumColorSquare;
import it.polimi.model.GameModel;
import it.polimi.model.WeaponsEffect;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteView extends Remote {
    
    
    int getIndex () throws RemoteException;

    int getChoicePlayer() throws RemoteException;

    int getChoicePlayer2() throws RemoteException;

    int getChoicePlayer3() throws RemoteException;

    int getRow() throws RemoteException;

    int getRow2() throws RemoteException;

    int getColumn() throws RemoteException;

    int getColumn2() throws RemoteException;

    int getTarget1() throws RemoteException;

    int getTarget2() throws RemoteException;

    int getTarget3() throws RemoteException;

    int getTarget4() throws RemoteException;
    
    boolean isUseSecondEffect () throws RemoteException;
    
    boolean isUseThirdEffect () throws RemoteException;
    
    WeaponsEffect getWeaponsEffect () throws RemoteException;
    
    boolean isOptionWeapon ();
    
    EnumColorSquare getColorRoom() throws RemoteException;

    EnumCardinalDirection getCardinalDirection() throws RemoteException;

    String getUser() throws RemoteException;
    
    boolean getOnline() throws RemoteException;
    
    boolean isBooleanChose ();
    
    void resetInput() throws RemoteException;
    
    void update(GameModel gameModel) throws RemoteException;
    
}
