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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FXMLController {
    
    @FXML
    private Label label;

    @FXML
    private Pane fPane;

    @FXML
    private TextField foxname;

    @FXML
    private TextField dogname;

    @FXML
    private Button startbutt;




    List<Button>  buttons=new ArrayList<>();
    List<String> operators=new ArrayList<>();
    State state;
    @FXML
    private GridPane gPane;

    int gamerNumber=0;



    @FXML
    private void handleButtonAction(ActionEvent event) {

        Button btn=(Button)event.getSource();
        if (operators.isEmpty()) {
            int figureX=Character.getNumericValue(btn.getId().charAt(3));
            int figureY=Character.getNumericValue(btn.getId().charAt(4));
            operators=state.enabledOperators(figureX,figureY);
            buttons.stream().filter(button -> operators.contains(button.getId()))
                    .forEach(button -> button.setStyle("-fx-border-color: red; -fx-border-width: 3px;"));
            //System.out.println("üres->aktuális gomb:"+"btn"+figureX+figureY);
        } else {
            buttons.stream().filter(button -> operators.contains(button.getId()))
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


            if(state.isGoal()) System.exit(1);
        }





    }


    
    public void initialize() {

        state=new State();

        //System.out.println(gPane.getChildren().isEmpty()); //debug


        gPane.getChildren().forEach(node -> buttons.add((Button)node)); //get buttons


        //System.out.println(buttons.size()); //debug

        initializeGamers();
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

    private void initializeGamers() {
        startbutt.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                String nameOfGamer1=foxname.getText().replaceAll("\\s+", "");
                String nameOfGamer2=dogname.getText().replaceAll("\\s+", "");
                System.out.println("DEBUG");
                if(!nameOfGamer1.equals("") && !nameOfGamer2.equals("")) {
                    state.addTwoGamer(new Gamer(nameOfGamer1),new Gamer(nameOfGamer2));
                    fPane.setVisible(false);
                    System.out.println("Belép");
                } else {
                    System.out.println("Rossz nevet adtál meg!");
                }


            }
        });


    }

}





