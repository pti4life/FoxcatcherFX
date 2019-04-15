package org.unideb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class State {



    private int[][] stateOfGame ={
            {0,0,3,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,4,0},
            {0,0,0,0,0,4,0,0},
            {0,0,0,0,0,0,0,0},
            {0,4,0,4,0,4,0,0}, };


    //These are initialized by enabledOperators and use stepTo method
    private int actualFigurePosX;
    private int actualFigurePosY;


    private int actualRound;

    private LinkedList<Gamer> gamers=new LinkedList<>();



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

    public LinkedList<Gamer> getGamers() {
        return gamers;
    }

    public void addTwoGamer(Gamer gamer, Gamer gamer2) {
            gamers.add(gamer);
            gamers.add(gamer2);


    }

    public void restartState() {
        stateOfGame=new int[][]{
                {0,0,3,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,4,0},
                {0,0,0,0,0,4,0,0},
                {0,0,0,0,0,0,0,0},
                {0,4,0,4,0,4,0,0}, };
    }


    public void stepping(int stepToX,int stepToY) {
        /*
        if(stateOfGame[actualFigurePosX][actualFigurePosY]==3) {
            roundCounter++;
        } else {
            roundCounter--;
        }
        if(Math.abs(roundCounter)%2==0) {
            roundCounter=1;
            System.out.println("Nem te következel!");
            return;
        } else if(roundCounter<=-1) {
            roundCounter=-1;
            System.out.println("Nem te következel!");
            return;
        }
         */
        System.out.println("actual figure:"+stateOfGame[actualFigurePosX][actualFigurePosY]+" actround figure:"+actualRound);
        if(stateOfGame[actualFigurePosX][actualFigurePosY]==actualRound) {
            System.out.println("Nem te következel");
            return;
        }
        actualRound=stateOfGame[actualFigurePosX][actualFigurePosY];
        stateOfGame[stepToX][stepToY]=stateOfGame[actualFigurePosX][actualFigurePosY];
        stateOfGame[actualFigurePosX][actualFigurePosY]=0;

        System.out.println("nowactualroundifgure:"+actualRound);
        //System.out.println("Lépés megtéve");
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
                try {
                    int stepToFig=stateOfGame[stepToPosX][stepToPosY];
                    //System.out.println("Debug");
                    String temp= (figurePosX + i) +String.valueOf(figurePosY+j);;
                    if(stepToFig==0) {
                        list.add("btn"+temp);
                        actualFigurePosX=figurePosX;
                        actualFigurePosY=figurePosY;
                    }
                } catch (Exception e) {}
                //System.out.println("i:"+i+" j:"+j);
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
                        score = gamers.get(1).getScore()+1;
                        gamers.get(1).setScore(score);
                        return true;
                    } else if(PositionOf4X<i) {
                        score = gamers.get(0).getScore()+1;
                        gamers.get(0).setScore(score);
                        return true;
                    };

                }
            }

        }
        return false;
    }



}





