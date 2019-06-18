package it.polimi.model;

import java.io.Serializable;

public enum State implements Serializable {
    
    LOBBY, DRAWNPOWERUP,PLAYERSETUP,SPAWNPLAYER,STARTTURN, CHOSEACTION,SELECTPOWERUP,SELECTPOWERUPINPUT,USEPOWERUP,RUN,SELECTRUN,GRAB,SELECTGRAB,SELECTEFFECT, SELECTSHOOTINPUT,SHOOT,
    ENDACTION,RECHARGE,PASSTURN,DEADPLAYER,SCORINGPLAYERBOARD,RESPWANPLAYER,ENDTURN,FINALSCORING,CHECKILLSHOOT,ERROR,SELECTWEAPON,
 
}
