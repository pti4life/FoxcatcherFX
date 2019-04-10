package org.unideb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class State {



    private int[][] stateOfGame ={
            {0,1,3,1,0,1,0,1},
            {1,0,1,0,1,0,1,0},
            {0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0},
            {0,1,0,1,0,1,4,1},
            {1,0,1,0,1,4,1,0},
            {0,1,0,1,0,1,0,1},
            {1,4,1,4,1,4,1,0}, };


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

    public boolean doingStep(int figurePosX, int figurePosY, int stepToX, int stepToY) {
        int actualFigure=stateOfGame[figurePosX][figurePosY]; //mozgatni kívánt bábu
        int toStepFigure=stateOfGame[stepToX][stepToY]; //a kijelölt helyen lévő bábu
        int stepDifferenceCol=Math.abs(figurePosY-stepToY); //a kijelölt hely
        int stepDifferenceRow=stepToX-figurePosX;

        if(toStepFigure!=0) {
            System.out.println("debug1");
            return false;

        } else if (actualFigure==4 && stepDifferenceCol!=1 && stepDifferenceRow!=-1) {
            stateOfGame[stepToX][stepToY]=4;
            stateOfGame[figurePosX][figurePosY]=0;
            System.out.println("debug2");
            return false;

        }  else if(actualFigure==3 && stepDifferenceCol!=1 && Math.abs(stepDifferenceRow)!=1) {
            stateOfGame[stepToX][stepToY]=3;
            stateOfGame[figurePosX][figurePosY]=0;
            System.out.println("debug3");
            return false;

        }


        return true;

    }

    public List<int[]> enabledOperators(int figurePosX, int figurePosY) {
        List<int[]> list = new ArrayList<>();
        for(int i=-1; i<2;){
            for(int j=-1; j<2;) {
                int stepToPosX=figurePosX+i;
                int stepToPosY=figurePosY+j;
                try {
                    int stepToFig=stateOfGame[stepToPosX][stepToPosY];
                    System.out.println("Debug");
                    if(stepToFig==0) list.add(new int[] {figurePosX+i,figurePosY+j});
                } catch (Exception e) {}
                System.out.println("i:"+i+" j:"+j);
                j=j+2;
            }
            int figure=stateOfGame[figurePosX][figurePosY];
            if (figure==4) break;
            i=i+2;
        }
        return list;

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


