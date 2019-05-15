package org.unideb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;
import db.GamerDao;
import guice.PersistenceModule;
import lombok.extern.slf4j.Slf4j;


/**
 * A játék állapotát reprezentáló osztály.
 */
@Slf4j
public class State {

    public State(int[][] stateOfGame) {
        this.stateOfGame=stateOfGame;
    }


    /**
     * A perzisztencia műveletek végrehajtására
     * szolgálaó {@code GamerDao} osztály egy objektuma.
     */
    private static Injector injector = Guice.createInjector(new PersistenceModule("test"));
    private static GamerDao gmd=injector.getInstance(GamerDao.class);

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
     * A Rókával játszó játékos pontszáma.
     */
    private int scoreOfFox =0;

    /**
     * A Kutyákkal játszó játékos pontszáma.
     */
    private int scoreOfDogs =0;


    /**
     * Azon játékos figuráját tárolja amely utoljára lépett.
     */
    private int actualRound;


    /**
     * A Rókával játszó játékost reprezentáló {@code Gamer} osztály egy objektuma.
     */
    private Gamer gamerWithFox;

    /**
     * A Kutyákkal játszó játékost reprezentáló {@code Gamer} osztály egy objektuma.
     */
    private Gamer gamerWithDog;




    /**
     * Az játék állapotának {@code stateOfGame} egy lista reprezentációja.
     * @return A mátrix elemeinek egy String reprezentációját tartalmazó lista.
     * A 0-val jelölt (üres) mezők egy üres String-ként jelennek meg.
     */
    public List<String> getStateOfGame() {

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

    public Gamer getGamerWithFox() {
        return gamerWithFox;
    }

    public Gamer getGamerWithDog() {
        return gamerWithDog;
    }

    public int getScoreOfFox() {
        return scoreOfFox;
    }

    public int getScoreOfDogs() {
        return scoreOfDogs;
    }

    /**
     * Az adatbázisban lévő össze játékos lekérése.
     * @return {@code Gamer} objektumok.
     */
    public List<Gamer> getAllGamers() {
        return gmd.findAll();
    }


    /**
     * A paraméterül kapott két {@code Gamer} osztály objektumát beállítja ha nem szerepelnek az adatbázisban,
     * ha valamelyik játékos szerepel az adatbázisban (játszott valaha),akkor
     * lekéri és azt állítja be.
     * @param gamer A Rókával játszó játékost reprezentáló {@code Gamer} osztály objektuma.
     * @param gamer2 A Kutyákkal játszó játékost reprezentáló {@code Gamer} osztály objektuma.
     */
    public void addTwoGamer(Gamer gamer, Gamer gamer2) {

        List<Gamer> firstGamerFromDB=gmd.findByName(gamer.getName());
        List<Gamer> SecondGamerFromDB=gmd.findByName(gamer2.getName());

        if (!firstGamerFromDB.isEmpty()) {
            gamerWithFox =firstGamerFromDB.get(0);
        } else {
            gamerWithFox =gamer;
            gmd.persist(gamerWithFox);
        }

        if (!SecondGamerFromDB.isEmpty()) {
            gamerWithDog =SecondGamerFromDB.get(0);
        } else {
            gamerWithDog =gamer2;
            gmd.persist(gamerWithDog);
        }
        log.info("Added two Gamer: Fox:{}, and Dog: {}", gamerWithFox.getName(), gamerWithDog.getName());

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

        if (actualRound==3) {
            actualRound=4;
        } else {
            actualRound=3;
        }

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
     * kalkulálja a figura mely pozíciókra léphet.
     * @param clickedPosX A felhasználó által kijelölt X koordináta.
     * @param clickedPosY A felhasználó által kijelölt Y koordináta.
     * @return Egy Stringeket tartalmazó listával tér vissza, melynek egy eleme
     * a felhasználó által kijelölt pozíción lévő figura, mely pozíciókra léphet, formája: "btnXY"
     * ahol XY az állapottérmátrix egy pozíciója.
     */
    public List<String> enabledOperators(int clickedPosX, int clickedPosY) {
        List<String> list = new ArrayList<>();
        int figure=stateOfGame[clickedPosX][clickedPosY];
        if(figure!=3 && figure!=4) return list; //Ha nem valamelyik figurával lépünk akkor nem tudunk lépni
        //két forciklus a
        for(int i=-1; i<2;){
            for(int j=-1; j<2;) {
                int stepToPosX=clickedPosX+i;
                int stepToPosY=clickedPosY+j;
                String temp= (stepToPosX) +String.valueOf(stepToPosY);
                try {
                    int stepToFig=stateOfGame[stepToPosX][stepToPosY];
                    log.info("enabledOperators, belépett a try ágba");
                    if(stepToFig==0) {
                        list.add("btn"+temp);
                        actualFigurePosX=clickedPosX;
                        actualFigurePosY=clickedPosY;
                    }
                } catch (Exception e) {
                    log.error("Exception a mátrix nem létező pozícójára hivatkozás miatt");
                }
                //logger.debug("for i:{}  for j:{}",i,j);
                j=j+2;
            }

            if (figure==4) break;
            i=i+2;
        }
        return list;

    }

    /**
     * Ellenőrzi, hogy az aktuális állapot célállapot-e.
     * @return {@code true} ha az aktuális állapot célállapot, {@code false} ha az aktuális állapot nem célállapot.
     */
    public boolean isGoal() {
        int lowestPosXOfDogs=7;
        int positionXOfFox=0;
        int positionYOfFox=0;
        for (int i = 0; i < stateOfGame.length; i++) {
            for (int j = 0; j <stateOfGame[i].length ; j++) {
                if(stateOfGame[i][j]==4) {
                    lowestPosXOfDogs=i; //a legkisebb sor ahol 4-es található
                } else if(stateOfGame[i][j]==3) {
                    positionXOfFox=i; //x pozíciója a  rókának
                    positionYOfFox=j; //y pozíciója a rókának
                }

            }


        }
        int score;
        if(enabledOperators(positionXOfFox,positionYOfFox).isEmpty()) {
            score = gamerWithDog.getScore()+1;
            gamerWithDog.setScore(score);
            gmd.update(gamerWithDog);
            scoreOfDogs++;
            log.info("Nincs alkalmazható operátor a rókára!, pozíciója: x:{} y:{}",positionXOfFox,positionYOfFox);
            log.info("gamerWithFox pontszáma:"+ gamerWithFox.getScore());
            return true;
        } else if (positionXOfFox>lowestPosXOfDogs) {
            score = gamerWithFox.getScore()+1;
            gamerWithFox.setScore(score);
            gmd.update(gamerWithFox);
            scoreOfFox++;
            log.info("A Róka háta mögött van kutya");
            log.info("gamerWithDogs pontszáma:"+ gamerWithDog.getScore()+"score:  "+score);
            return true;
        }
        return false;
    }


    /**
     * A játékot és a hozzá tartozó minden komponenst kezdőállapotra állítja.
     */
    public void exitState() {
        restartState();
        scoreOfFox =0;
        scoreOfDogs =0;
        gamerWithFox =null;
        gamerWithDog =null;
        actualRound=0;
    }



}





