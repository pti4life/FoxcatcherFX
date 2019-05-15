package main;

import java.util.LinkedList;

public class main2 {
    public static void main(String[] args) {
        int[][] stateOfGame=new int[][] {
                {0,0,3,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,3,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,4,0,4,0,4,0,0}, };

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

        int ToX=7;
        int ToY=5;

        int index=(ToX+1)*8-(8-ToY);

        System.out.println(list.get(index)+" index:"+index);

        for (int i=0; i<list.size();i++) {
            System.out.println(i+". elem:"+list.get(i));
        }
        System.out.println("sor"+(ToX+1)*8+"oszlop "+(8-(ToY+1)));
    }

    }

