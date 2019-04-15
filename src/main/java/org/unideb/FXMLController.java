package org.unideb;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FXMLController {

    @FXML
    private Pane startPane;

    @FXML
    private TextField foxname;

    @FXML
    private TextField dogname;

    @FXML
    private Button startbutt;

    @FXML
    private GridPane gPane;

    @FXML
    private Pane restartPane;

    @FXML
    private Button exitButton;

    @FXML
    private Button restartButton;

    @FXML
    private Label foxinf;

    @FXML
    private Label doginf;




    List<Button>  gameButtons=new ArrayList<>();
    List<String> operators=new ArrayList<>();
    State state;




    @FXML
    private void handleButtonAction(ActionEvent event) {

        Button btn=(Button)event.getSource();
        if (operators.isEmpty()) {
            int figureX=Character.getNumericValue(btn.getId().charAt(3));
            int figureY=Character.getNumericValue(btn.getId().charAt(4));
            operators=state.enabledOperators(figureX,figureY);
            gameButtons.stream().filter(button -> operators.contains(button.getId()))
                    .forEach(button -> button.setStyle("-fx-border-color: red; -fx-border-width: 3px;"));
            //System.out.println("üres->aktuális gomb:"+"btn"+figureX+figureY);
        } else {
            gameButtons.stream().filter(button -> operators.contains(button.getId()))
                    .forEach(button -> button.setStyle(null));
            //System.out.println();
            //System.out.println("nem üres az operátor: "+operators);
            int stepToX=Character.getNumericValue(btn.getId().charAt(3));
            int stepToY=Character.getNumericValue(btn.getId().charAt(4));
            //System.out.println("Hova akarunk lépni"+"btn"+stepToX+stepToY);
            //System.out.println("honnan lépünk:"+"btn"+figureX+figureY);
            //System.out.println("A jelenlegi buttonünk");
            if(operators.contains(btn.getId())) {

                //System.out.println("Az operátorok halmazában van az adott gomb");
                //System.out.println("figureX:"+figureX+" figureY"+figureY+" steptoX"+stepToX+" steptoY"+stepToY);
                state.stepping(stepToX,stepToY);
                updateState();
                operators=new ArrayList<>();

            }

            operators=new ArrayList<>();


            if(state.isGoal()) {
             endgameView();
            }
        }





    }


    
    public void initialize() {

        state=new State();

        //System.out.println(gPane.getChildren().isEmpty()); //debug
        gPane.getChildren().forEach(node -> gameButtons.add((Button)node)); //get buttons
        //System.out.println(buttons.size()); //debug
        initializeGamers();


    }




    private void updateState() {
        List<String> list=state.getStateOfGame();
        for (int i = 0; i <64; i++) {
            gameButtons.get(i).setText(list.get(i));
        }
        String fox=state.getGamers().get(0).getName()+"(Róka) pontszáma:"+state.getGamers().get(0).getScore();
        foxinf.setText(fox);

        String dog=state.getGamers().get(1).getName()+"(Kutyák) pontszáma:"+state.getGamers().get(1).getScore();
        doginf.setText(dog);


    }

    private void initializeGamers() {

        startbutt.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                String nameOfGamer1=foxname.getText().replaceAll("\\s+", "");
                String nameOfGamer2=dogname.getText().replaceAll("\\s+", "");
                System.out.println("DEBUG");
                if(!nameOfGamer1.equals("") && !nameOfGamer2.equals("")) {
                    state.addTwoGamer(new Gamer(nameOfGamer1),new Gamer(nameOfGamer2));
                    startPane.setVisible(false);
                    updateState();
                    System.out.println("Belép");
                } else {
                    System.out.println("Rossz nevet adtál meg!");
                }


            }
        });


    }

    private void endgameView() {
        gPane.setDisable(true);
        gPane.setOpacity(0.3);
        restartPane.setVisible(true);
        System.out.println("debug");

        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gPane.setDisable(false);
                gPane.setOpacity(1);
                restartPane.setVisible(false);
                state.restartState();
                updateState();
            }
        });


        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                state.restartState();
                updateState();
                gPane.setDisable(false);
                gPane.setOpacity(1);
                restartPane.setVisible(false);
                foxname.setText(" ");
                dogname.setText(" ");
                startPane.setVisible(true);

            }
        });
    }




}





