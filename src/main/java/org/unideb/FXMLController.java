package org.unideb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class FXMLController {

    @FXML
    private Label matchInfoFox;

    @FXML
    private Label matchInfoDog;

    @FXML
    private TextField nameOfFox;

    @FXML
    private TextField nameOfDog;

    @FXML
    private Pane rankPane;

    @FXML
    private Pane startPane;

    @FXML
    private Pane endGameWindow;

    @FXML
    private GridPane gPane;


    @FXML
    private Button startbutt;

    @FXML
    private Button exitButton;

    @FXML
    private Button restartButton;


    @FXML
    private Button rankExitButt;

    @FXML
    private TableView rankTable;

    @FXML
    private TableColumn rankName;

    @FXML
    private TableColumn rankScore;





    private Image whitePawn=new Image(getClass().getResourceAsStream("/images/white_pawn.png"));
    private Image blackPawn=new Image(getClass().getResourceAsStream("/images/black_pawn.png"));

    private List<Button>  gameButtons=new ArrayList<>();
    private List<String> operators=new ArrayList<>();
    private State state;



    //A gridpane gombjaival dolgozik
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

            }

            operators=new ArrayList<>();


            if(state.isGoal()) {
             endgameView();
            }
        }





    }


    @FXML
    private void rankingButtonAction(ActionEvent event) {
        rankPane.setVisible(true);
        startPane.setVisible(false);
        gPane.setVisible(false);
        ObservableList<Gamer> ObserlistOfGamers= FXCollections.observableList(state.getAllGamers());

        rankName.setCellValueFactory(new PropertyValueFactory<>("name"));
        rankScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        rankTable.setItems(ObserlistOfGamers);

        rankExitButt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                rankTable.getItems().clear();
                rankPane.setVisible(false);
                startPane.setVisible(true);
                gPane.setVisible(true);
            }
        });


    }


    //Inicíalizálja a játékot
    public void initialize() {

        state=new State();

        //System.out.println(gPane.getChildren().isEmpty()); //debug
        gPane.getChildren().forEach(node -> gameButtons.add((Button)node)); //get buttons
        //System.out.println(buttons.size()); //debug
        initializeGamers();
        System.out.println("LEFUT MIELŐTT ELINDULNA A JÁTÉK");


    }




    private void updateState() {
        List<String> list=state.getStateOfGame();
        for (int i = 0; i <64; i++) {
            String actualButtText=list.get(i);
            System.out.println(actualButtText);
            if (actualButtText.equals("4")) {
                System.out.println("rápászít a 4esre a feketét");
                gameButtons.get(i).setGraphic(new ImageView(blackPawn));
            } else if (actualButtText.equals("3")) {
                System.out.println("rápászít a 3esre a fehéret");
                gameButtons.get(i).setGraphic(new ImageView(whitePawn));
            } else {
                gameButtons.get(i).setGraphic(null);
            }
        }

        String fox=state.getFirstGamer().getName()+" (Róka) pontszáma:"+state.getScoreOfFirstGamer();
        matchInfoFox.setText(fox);

        String dog=state.getSecondGamer().getName()+" (Kutyák) pontszáma:"+state.getScoreOfSecondGamer();
        matchInfoDog.setText(dog);


    }

    private void initializeGamers() {

        startbutt.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                String nameOfGamer1= nameOfFox.getText().replaceAll("\\s+", "");
                String nameOfGamer2= nameOfDog.getText().replaceAll("\\s+", "");
                System.out.println("DEBUG");
                if(!nameOfGamer1.equals("") && !nameOfGamer2.equals("")) {
                    Gamer g1=Gamer.builder()
                            .name(nameOfGamer1)
                            .build();
                    Gamer g2=Gamer.builder()
                            .name(nameOfGamer2)
                            .build();
                    state.addTwoGamer(g1,g2);
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
        updateState();
        gPane.setDisable(true);
        gPane.setOpacity(0.3);
        endGameWindow.setVisible(true);
        System.out.println("debug");

        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                restartToDos();

            }
        });
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                restartToDos();
                exitToDos();


            }
        });
    }


    private void restartToDos() {
        state.restartState();
        updateState();
        gPane.setDisable(false);
        gPane.setOpacity(1);
        endGameWindow.setVisible(false);

    }

    private void exitToDos() {
        state.exitState();
        nameOfFox.setText("");
        nameOfDog.setText("");
        matchInfoFox.setText("");
        matchInfoDog.setText("");
        startPane.setVisible(true);
    }




}





