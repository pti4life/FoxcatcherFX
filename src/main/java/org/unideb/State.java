package org.unideb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class State {



    public int[][] stateOfGame ={
            {0,0,3,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,4,0},
            {0,0,0,0,0,4,0,0},
            {0,0,0,0,0,0,0,0},
            {0,4,0,4,0,4,0,0}, };


    private int actualFigurePosX;
    private int actualFigurePosY;
    private int roundCounter=0;
    private ArrayList<Gamer> gamers=new ArrayList<>();



    public LinkedList<String> getStateOfGame() {

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

    public void addTwoGamer(Gamer gamer, Gamer gamer2) {
            gamers.add(gamer);
            gamers.add(gamer2);


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

        stateOfGame[stepToX][stepToY]=stateOfGame[actualFigurePosX][actualFigurePosY];
        stateOfGame[actualFigurePosX][actualFigurePosY]=0;
        //System.out.println("Lépés megtéve");
    }

    public List<String> enabledOperators(int figurePosX, int figurePosY) {
        List<String> list = new ArrayList<>();
        int figure=stateOfGame[figurePosX][figurePosY];
        if(figure!=3 && figure!=4) return list;
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
                    PositionOf4X=i;
                }
                if(stateOfGame[i][j]==3) {
                    return enabledOperators(i,j).isEmpty() || PositionOf4X<i;

                }
            }

        }
        return false;
    }



    public void printMatrix() {
        for(int i=0; i<stateOfGame.length;i++) {
            for(int j=0; j<stateOfGame[0].length;j++) {
                System.out.print(stateOfGame[i][j]+" ");
            }
            System.out.println();
        }
    }

}





