package org.unideb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FXMLController {
    
    @FXML
    private Label label;

    List<List<Button>>  buttons=new ArrayList<>();
    State state;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }


    @FXML
    private GridPane gPane;
    
    public void initialize() {
        /*
        for (Node nde : gPane.) {
            if (nde instanceof Button)
                buttons.add((Button)nde);
        }

        System.out.println(buttons.size());


        for (int i = 0; i < 8; i++) {
            for(int j=0; j<buttons.size();j++) {
                buttons.get(i).setText(""+state.stateOfGame[i][j]);
            }

        }

         */





    }    
}
