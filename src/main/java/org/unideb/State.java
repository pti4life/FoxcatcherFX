package org.unideb;

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





    public void printMatrix() {
        for(int i=0; i<stateOfGame.length;i++) {
            for(int j=0; j<stateOfGame[0].length;j++) {
                System.out.print(stateOfGame[i][j]+" ");
            }
            System.out.println();
        }
    }

}


