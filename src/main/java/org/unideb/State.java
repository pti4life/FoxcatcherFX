package org.unideb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;
import db.GamerDao;
import guice.PersistenceModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class State {

    public State() {
        Injector injector = Guice.createInjector(new PersistenceModule("test"));
        gmd = injector.getInstance(GamerDao.class);
    }


    private GamerDao gmd;
    private int[][] stateOfGame ={
            {0,0,3,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,4,0,4,0,4,0,0}, };


    //These are initialized by enabledOperators and use stepTo method
    private int actualFigurePosX;
    private int actualFigurePosY;


    public int scoreOfFirstGamer=0;
    public int scoreOfSecondGamer=0;
    private int actualRound;

    private Gamer firstGamer;
    private Gamer secondGamer;

    private static Logger logger = LoggerFactory.getLogger(State.class);


    public List<String> getStateOfGame() {

        LinkedList<String> list = new LinkedList<>();
        for(int i = 0; i < stateOfGame.length; i++) {
            for (int j = 0; j < stateOfGame[i].length; j++) {
                int figure=stateOfGame[i][j];
                switch (figure) {
                    case 1: list.add(""); break;
                    case 0: list.add(""); break;
                    case 3: list.add("3"); break;
                    case 4: list.add("4"); break;
                }
            }
        }
        return list;
    }

    public Gamer getFirstGamer() {
        return firstGamer;
    }

    public Gamer getSecondGamer() {
        return secondGamer;
    }

    public int getScoreOfFirstGamer() {
        return scoreOfFirstGamer;
    }

    public int getScoreOfSecondGamer() {
        return scoreOfSecondGamer;
    }

    public void addTwoGamer(Gamer gamer, Gamer gamer2) {
        List<Gamer> list=gmd.findAll();

        for (Gamer g:list) {
            if (g.getName().equals(gamer.getName()) ) {
                logger.info("A NÉV ÉS A TÍPUS EGYEZIK"+gamer.getName());
                firstGamer=g;
            }
            if (g.getName().equals(gamer2.getName()) ) {
                logger.info("A NÉV ÉS A TÍPUS EGYEZIK"+gamer2.getName());
                secondGamer=g;
            }
        }

        if (firstGamer==null) {
            logger.info("firstgamerNULL");
            firstGamer=gamer;
            gmd.persist(firstGamer);
        }
        if (secondGamer==null) {
            logger.info("secondgamerNULL");
            secondGamer=gamer2;
            gmd.persist(secondGamer);
        }


        logger.info("Added two Gamer: Fox:{}, and Dog: {}",firstGamer.getName(),secondGamer.getName());
        System.out.println(gmd.findAll().get(0).getName());
    }

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


    public void stepping(int stepToX,int stepToY) {
        logger.debug("actual figure:{}, previous round figure:{}",stateOfGame[actualFigurePosX][actualFigurePosY],actualRound);

        if(stateOfGame[actualFigurePosX][actualFigurePosY]==actualRound) {
            logger.warn("NEM TE KÖVETKEZEL");
            return;
        }
        actualRound=stateOfGame[actualFigurePosX][actualFigurePosY];
        stateOfGame[stepToX][stepToY]=stateOfGame[actualFigurePosX][actualFigurePosY];
        stateOfGame[actualFigurePosX][actualFigurePosY]=0;
        logger.info("Lépés megtéve");
    }

    public List<String> enabledOperators(int figurePosX, int figurePosY) {
        List<String> list = new ArrayList<>();
        int figure=stateOfGame[figurePosX][figurePosY];
        if(figure!=3 && figure!=4) return list; //Ha nem valamelyik figurával lépünk akkor nem tudunk lépni
        //két forciklus a
        for(int i=-1; i<2;){
            for(int j=-1; j<2;) {
                int stepToPosX=figurePosX+i;
                int stepToPosY=figurePosY+j;
                String temp= (stepToPosX) +String.valueOf(stepToPosY);
                try {
                    int stepToFig=stateOfGame[stepToPosX][stepToPosY];
                    logger.info("enabledOperators, belépett a try ágba");
                    if(stepToFig==0) {
                        list.add("btn"+temp);
                        actualFigurePosX=figurePosX;
                        actualFigurePosY=figurePosY;
                    }
                } catch (Exception e) {
                    logger.error("Exception a mátrix nem létező pozícójára hivatkozás miatt");
                }
                //logger.debug("for i:{}  for j:{}",i,j);
                j=j+2;
            }

            if (figure==4) break;
            i=i+2;
        }
        return list;

    }


    boolean isGoal() {
        int PositionOf4X=7;
        for (int i = 0; i < stateOfGame.length; i++) {
            for (int j = 0; j <stateOfGame[i].length ; j++) {
                if(stateOfGame[i][j]==4 && i<PositionOf4X) {
                    PositionOf4X=i; //a legkisebb sor ahol 4-es található
                }

                //megnézi, hogy van e alkalmazható operátor a 3masra vagy van-e mögötte 4-es
                if(stateOfGame[i][j]==3) {
                    int score;
                    if(enabledOperators(i,j).isEmpty()) {
                        logger.info("Nincs alkalmazható operátor a rókára");
                        score = firstGamer.getScore()+1;
                        firstGamer.setScore(score);
                        gmd.update(firstGamer);
                        scoreOfFirstGamer++;
                        logger.info("FIRSTGAMER PONTSZÁMA:"+firstGamer.getScore());
                        return true;
                    } else if(PositionOf4X<i) {
                        logger.info("A Róka háta mögött van kutya");
                        score = secondGamer.getScore()+1;
                        secondGamer.setScore(score);
                        gmd.update(secondGamer);
                        scoreOfSecondGamer++;
                        logger.info("SECONDGAMER PONTSZÁMA:"+secondGamer.getScore()+"score:  "+score);
                        return true;
                    };

                }
            }

        }
        return false;
    }

    private void updateDB() {
        Gamer gamer1=gmd.find(firstGamer.getId()).orElse(null);
        Gamer gamer2=gmd.find(secondGamer.getId()).orElse(null);
        int actualScore;
        if (gamer1!=null) {
            actualScore=firstGamer.getScore()+gamer1.getScore();
            firstGamer.setScore(actualScore);
        }

        if (gamer2!=null) {
            actualScore=secondGamer.getScore()+gamer2.getScore();
            secondGamer.setScore(actualScore);
        }

        firstGamer.setScore(0);
        secondGamer.setScore(0);
    }

    public void exitState() {
        restartState();
        scoreOfFirstGamer=0;
        scoreOfSecondGamer=0;
        firstGamer=null;
        secondGamer=null;
        actualRound=0;
    }



}





