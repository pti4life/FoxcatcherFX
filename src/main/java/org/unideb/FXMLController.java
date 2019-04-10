package org.unideb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FXMLController {
    
    @FXML
    private Label label;

    List<Button>  buttons=new ArrayList<>();
    State state;
    @FXML
    private GridPane gPane;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }



    
    public void initialize() {

        state=new State();

        System.out.println(gPane.getChildren().isEmpty());

        for (Node node : gPane.getChildren()) {
            if (node instanceof Button) {
                buttons.add((Button) node);
            }
        }

        System.out.println(buttons.size());




        LinkedList<String> listOfFigures=state.getStateOfGame();
        for (int i = 0; i < 64; i++) {
            //String temp="-fx-content-display:"+listOfFigures.get(i)+";";
            buttons.get(i).setText(listOfFigures.get(i));

        }









    }    
}
