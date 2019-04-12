package org.unideb;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FXMLController {
    
    @FXML
    private Label label;

    List<Button>  buttons=new ArrayList<>();
    List<String> operators=new ArrayList<>();
    State state;
    int figureX=0;
    int figureY=0;
    @FXML
    private GridPane gPane;




    @FXML
    private void handleButtonAction(ActionEvent event) {

        Button btn=(Button)event.getSource();
        if (operators.isEmpty()) {
            figureX=Character.getNumericValue(btn.getId().charAt(3));
            figureY=Character.getNumericValue(btn.getId().charAt(4));
            operators=state.enabledOperators(figureX,figureY);
            //System.out.println("üres->aktuális gomb:"+"btn"+figureX+figureY);
        } else {
            System.out.println();
            System.out.println("nem üres az operátor: "+operators);
            int stepToX=Character.getNumericValue(btn.getId().charAt(3));
            int stepToY=Character.getNumericValue(btn.getId().charAt(4));
            //System.out.println("Hova akarunk lépni"+"btn"+stepToX+stepToY);
            //System.out.println("honnan lépünk:"+"btn"+figureX+figureY);
            //System.out.println("A jelenlegi buttonünk");
            if(operators.contains(btn.getId())) {
                //System.out.println("Az operátorok halmazában van az adott gomb");
                //System.out.println("figureX:"+figureX+" figureY"+figureY+" steptoX"+stepToX+" steptoY"+stepToY);
                state.stepping(figureX,figureY,stepToX,stepToY);
                updateState();
                operators=new ArrayList<>();
            }

            operators=new ArrayList<>();


        }




        /*

        System.out.println("megHÍVÓDOTT");
        Button btn=(Button)event.getSource();
        if(!operators.contains(btn)) {
            //abból indulunk ki, hogy ha az aktuális buttont tRTlmazza az operátorunk
            buttons.forEach(button -> button.setOnMouseClicked(null));
            System.out.println("belépett");
            figureX=Character.getNumericValue(btn.getId().charAt(3));
            figureY=Character.getNumericValue(btn.getId().charAt(4));
            operators=state.enabledOperators(figureX,figureY);

        }
        buttons.stream().filter(button -> operators.contains(button.getId())).forEach(button -> {
                    button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            System.out.println("ide");
                            Button btn2 =(Button)mouseEvent.getSource();
                            int stepToX=Character.getNumericValue(btn2.getId().charAt(3));
                            int stepToY=Character.getNumericValue(btn2.getId().charAt(4));
                            state.stepping(figureX,figureY,stepToX,stepToY);
                            updateState();
                            operators=new ArrayList<>();

                        }}); }
                );
         */

    }


    
    public void initialize() {

        state=new State();

        System.out.println(gPane.getChildren().isEmpty()); //debug


        gPane.getChildren().forEach(node -> buttons.add((Button)node)); //get buttons


        System.out.println(buttons.size()); //debug



        updateState();

        /*
        gPane.getChildren().forEach(item -> {
            item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("ez hívódik hamarabb HANDLER");
                    javafx.scene.Node source = (javafx.scene.Node)event.getSource();
                    actualPosX=GridPane.getRowIndex(source);
                    actualPosY=GridPane.getColumnIndex(source);
                    System.out.println("colindex:"+actualPosY+" rowindex:"+actualPosX);
                    operators=state.enabledOperators(actualPosX.intValue(),actualPosY.intValue());



                }


            });

        });
         */





        System.out.println("handler");










    }





    private Node setDisableButtons(int colIndex, int RowIndex) {
        for (Node node : gPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == colIndex && GridPane.getRowIndex(node) == RowIndex) {
                return node;
            }
        }
        return null;

    }

    private void updateState() {
        List<String> list=state.getStateOfGame();
        for (int i = 0; i <64; i++) {
            buttons.get(i).setText(list.get(i));
        }

    }
}


