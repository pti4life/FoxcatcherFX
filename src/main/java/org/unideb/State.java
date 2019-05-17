package org.unideb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;


/**
 * A játék állapotát reprezentáló osztály.
 */
@Slf4j
public class State {


    /**
     * Beállítja a paraméterül kapott állapotot kezdőállapotnak ha megfelel a követelményeknek.
     * @param stateOfGame A játék tábláját reprezentáló 2 dimenziós tömb.
     * @throws IllegalArgumentException Nem elfogadható tábla esetén.
     */
    public State(int[][] stateOfGame) {
        if (!isValid(stateOfGame)) {
            throw new IllegalArgumentException();
        }

        initBoard(stateOfGame);
    }




    /**
     * 8 soros és 8 oszlopos mátrix, ami a játékunk aktuális állapotát reprezentálja.
     * A mátrix elemeinek értéke 0, 3, 4,
     * 0: Az üres mezőket reprezentálja.
     * 3: A Rókát reprezentálja
     * 4: A Kutyákat reprezentálja
     */
    private int[][] stateOfGame;


    /**
     * Azon figurának a mátrixban lévő pozícióját tárolja amellyel lépni szeretnénk.
     * Az {@code enabledOperators} metódus által kerül inícializálásra és a {@code stepTo} metódus használja.
     */
    private int actualFigurePosX,actualFigurePosY;



    /**
     * Azon játékos figuráját tárolja amely utoljára lépett.
     */
    private int actualRound;




    /**
     * Az játék állapotának {@code stateOfGame} egy lista reprezentációja.
     * @return A mátrix elemeinek egy String reprezentációját tartalmazó lista.
     * A 0-val jelölt (üres) mezők egy üres String-ként jelennek meg.
     */
    public List<String> stateToListGetter() {

        LinkedList<String> list = new LinkedList<>();
        for(int i = 0; i < stateOfGame.length; i++) {
            for (int j = 0; j < stateOfGame[i].length; j++) {
                int figure=stateOfGame[i][j];
                switch (figure) {
                    case 3: list.add("3"); break;
                    case 4: list.add("4"); break;
                    default: list.add("");
                }
            }
        }
        return list;
    }

    public int getActualRound() {
        return actualRound;
    }

    /**
     * A játék állapotát visszaállítja a kezdőállapotra és
     * biztosítja, hogy a következő körben a másik játékos kezdjen.
     */
    public void restartState() {
        stateOfGame=new int[][]{
                {0,0,3,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,4,0,4,0,4,0,0},};



    }


    /**
     * Az állapotteret egy másik állapotba viszi, azaz egy lépést hajt végre
     * a paraméterül kapott pozícióra, ha valóban a lépni akaró játékos következik.
     * @param stepToX A lépés X koordinátája.
     * @param stepToY A lépés Y koordinátája.
     */
    public void stepping(int stepToX,int stepToY) {
        log.debug("actual figure:{}, previous round figure:{}",stateOfGame[actualFigurePosX][actualFigurePosY],actualRound);

        if(stateOfGame[actualFigurePosX][actualFigurePosY]==actualRound) {
            log.warn("NEM TE KÖVETKEZEL");
            return;
        }
        actualRound=stateOfGame[actualFigurePosX][actualFigurePosY];
        stateOfGame[stepToX][stepToY]=stateOfGame[actualFigurePosX][actualFigurePosY];
        stateOfGame[actualFigurePosX][actualFigurePosY]=0;
        log.info("Lépés megtéve");
    }


    /**
     * A felhasználótól bekért pozíción meghatározza, hogy milyen figura van, és ennek megfelelően
     * kalkulálja, hogy a figura mely pozíciókra léphet.
     * @param clickedPosX A felhasználó által kijelölt X koordináta.
     * @param clickedPosY A felhasználó által kijelölt Y koordináta.
     * @return Egy Stringeket tartalmazó listával tér vissza, melynek egy eleme
     * a felhasználó által kijelölt pozíción lévő figura, mely pozíciókra léphet, formája: "btnXY"
     * ahol XY az állapottérmátrix egy pozíciója.
     */
    public List<String> enabledOperators(int clickedPosX, int clickedPosY) {
        List<String> list = new ArrayList<>();
        int figure=stateOfGame[clickedPosX][clickedPosY];
        if(figure!=3 && figure!=4) return list;
        log.info("Clicked Position: X: {}  Y: {}",clickedPosX,clickedPosY);

        for(int i=-1; i<2;){
            for(int j=-1; j<2;) {
                int stepToPosX=clickedPosX+i;
                int stepToPosY=clickedPosY+j;
                String temp= (stepToPosX) +String.valueOf(stepToPosY);
                try {
                    int stepToFig=stateOfGame[stepToPosX][stepToPosY];
                    if(stepToFig==0) {
                        list.add("btn"+temp);
                        actualFigurePosX=clickedPosX;
                        actualFigurePosY=clickedPosY;
                    }
                } catch (Exception e) {
                    log.error("Exception a mátrix nem létező pozícójára hivatkozás miatt");
                }
                j=j+2;
            }

            if (figure==4) break;
            i=i+2;
        }
        return list;

    }

    /**
     * Ellenőrzi, hogy az aktuális állapot célállapot-e a Rókára nézve.
     * @return {@code true} ha az aktuális állapot célállapot, {@code false} ha az aktuális állapot nem célállapot.
     */
    public boolean isGoalFox() {
        int lowestPosXOfDogs=7;
        int positionXOfFox=0;
        for (int i = 0; i < stateOfGame.length; i++) {
            for (int j = 0; j <stateOfGame[i].length ; j++) {
                if(stateOfGame[i][j]==4) {
                    lowestPosXOfDogs=i;
                } else if(stateOfGame[i][j]==3) {
                    positionXOfFox=i;
                }
            }
        }
        if (positionXOfFox>lowestPosXOfDogs) {
            log.info("A Róka a kutyák háta mögött van");
            return true;
        }

        return false;
    }


    /**
     * Ellenőrzi, hogy az aktuális állapot célállapot-e a Kutyákra nézve.
     * @return {@code true} ha az aktuális állapot célállapot, {@code false} ha az aktuális állapot nem célállapot.
     */
    public boolean isGoalDog() {
        for (int i = 0; i < stateOfGame.length; i++) {
            for (int j = 0; j <stateOfGame[i].length ; j++) {
                if(stateOfGame[i][j]==3) {
                    log.info("Nincs alkalmazható operátor a rókára!, pozíciója: x:{} y:{}",i,j);
                    return enabledOperators(i,j).isEmpty();
                }
            }
        }
        return false;
    }



    /**
     * A megadott játékostábla helyességét ellenörző metódus.
     * @param gameBoard 2 dimenziós int tömb.
     * @return {@Code True} ha a mátrix megfelel a követelményeknek, {@Code False} ha nem.
     */
    private boolean isValid(int[][] gameBoard) {
        if (gameBoard==null || gameBoard.length!=8) {
            return false;
        }

        for (int i=0; i<gameBoard.length;i++) {
            if (gameBoard[i].length!=8) {
                return false;
            }

            for (int j=0; j<gameBoard[i].length;j++) {
                if (gameBoard[i][j]!=3 && gameBoard[i][j]!=4 && gameBoard[i][j]!=0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void initBoard(int[][] a) {
        this.stateOfGame= new int[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                this.stateOfGame[i][j]=a[i][j];
            }
        }
    }

}





