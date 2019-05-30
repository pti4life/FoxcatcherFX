package org.unideb;

import db.PersistenceOperations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
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

    @FXML
    private Label nextGamerLab;




    private Image whitePawn=new Image(getClass().getResourceAsStream("/images/white_pawn.png"));
    private Image blackPawn=new Image(getClass().getResourceAsStream("/images/black_pawn.png"));

    private List<Button>  gameButtons=new ArrayList<>();
    private List<String> operators=new ArrayList<>();
    private State state;
    private Gamer gamerWithFox;
    private Gamer gamerWithDog;
    private int actualScoreOfDog=0;
    private int actualScoreOfFox=0;

    private PersistenceOperations persistenceOperations =new PersistenceOperations();

    @FXML
    private void handleButtonAction(ActionEvent event) {

        Button btn=(Button)event.getSource();
        if (operators.isEmpty()) {
            int figureX=Character.getNumericValue(btn.getId().charAt(3));
            int figureY=Character.getNumericValue(btn.getId().charAt(4));
            operators=state.enabledOperators(figureX,figureY);
            gameButtons.stream().filter(button -> operators.contains(button.getId()))
                    .forEach(button -> button.setStyle("-fx-border-color: red; -fx-border-width: 3px;"));
        } else {
            gameButtons.stream().filter(button -> operators.contains(button.getId()))
                    .forEach(button -> button.setStyle(null));

            int stepToX=Character.getNumericValue(btn.getId().charAt(3));
            int stepToY=Character.getNumericValue(btn.getId().charAt(4));

            if(operators.contains(btn.getId())) {
                state.stepping(stepToX,stepToY);
                if (state.getActualRound()==3) {
                    nextGamerLab.setText(gamerWithDog.getName());
                } else if (state.getActualRound()==4) {
                    nextGamerLab.setText(gamerWithFox.getName());
                }

                updateState();

            }

            operators=new ArrayList<>();

            if(state.isGoalDog()) {
                int sumScoreDog=gamerWithDog.getScore()+1;
                gamerWithDog.setScore(sumScoreDog);
                actualScoreOfDog++;
                persistenceOperations.updateGamer(gamerWithDog);
                saveGameStation();
                updateState();
                endgameView();
            } else if (state.isGoalFox()) {

                int sumScoreFox = gamerWithFox.getScore()+1;
                gamerWithFox.setScore(sumScoreFox);
                actualScoreOfFox++;
                persistenceOperations.updateGamer(gamerWithFox);
                saveGameStation();
                updateState();
                endgameView();
            }
        }





    }


    @FXML
    private void rankingButtonAction(ActionEvent event) {
        rankPane.setVisible(true);
        startPane.setVisible(false);
        gPane.setVisible(false);
        ObservableList<Gamer> ObserlistOfGamers= FXCollections.observableList(persistenceOperations.getAllGamers());

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





    public void initialize() {

        state=new State(new int[][] {
                {0,0,3,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,4,0,4,0,4,0,0}, });


        gPane.getChildren().forEach(node -> gameButtons.add((Button)node));
        initializeGamers();


    }




    private void updateState() {
        List<String> list=convertStatetoList(state.getStateOfGame());
        for (int i = 0; i <64; i++) {
            String actualButtText=list.get(i);
            if (actualButtText.equals("4")) {
                gameButtons.get(i).setGraphic(new ImageView(blackPawn));
            } else if (actualButtText.equals("3")) {
                gameButtons.get(i).setGraphic(new ImageView(whitePawn));
            } else {
                gameButtons.get(i).setGraphic(null);
            }
        }

        String fox=gamerWithFox.getName()+" (Róka) pontszáma:"+actualScoreOfFox;
        matchInfoFox.setText(fox);

        String dog=gamerWithDog.getName()+" (Kutyák) pontszáma:"+actualScoreOfDog;
        matchInfoDog.setText(dog);


    }

    /**
     * Az játék állapotának {@code stateOfGame} egy lista reprezentációja.
     * @return A mátrix elemeinek egy String reprezentációját tartalmazó lista.
     * A 0-val jelölt (üres) mezők egy üres String-ként jelennek meg.
     */
    public List<String> convertStatetoList(int[][] stateOfGame) {
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
        return list;

    }

    private void initializeGamers() {

        startbutt.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                String nameOfGamer1= nameOfFox.getText().replaceAll("\\s+", "");
                String nameOfGamer2= nameOfDog.getText().replaceAll("\\s+", "");
                if(!nameOfGamer1.equals("") && !nameOfGamer2.equals("")) {
                    Gamer g1=Gamer.builder()
                            .name(nameOfGamer1)
                            .build();
                    Gamer g2=Gamer.builder()
                            .name(nameOfGamer2)
                            .build();
                    addTwoGamer(g1,g2);
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
        state=new State(new int[][] {
                {0,0,3,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,4,0,4,0,4,0,0}, },state.getActualRound());
        updateState();
        gPane.setDisable(false);
        gPane.setOpacity(1);
        endGameWindow.setVisible(false);
        if (state.getActualRound()==4) {
            nextGamerLab.setText(gamerWithFox.getName());
        } else {
            nextGamerLab.setText(gamerWithDog.getName());
        }

    }

    private void exitToDos() {
        state=new State(new int[][]{
                {0,0,3,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0},
                {0,4,0,4,0,4,0,0},});
        gamerWithDog=null;
        gamerWithFox=null;
        actualScoreOfDog=0;
        actualScoreOfFox=0;

        nextGamerLab.setText("");
        nameOfFox.setText("");
        nameOfDog.setText("");
        matchInfoFox.setText("");
        matchInfoDog.setText("");
        startPane.setVisible(true);
    }



    /**
     * A paraméterül kapott két {@code Gamer} osztály objektumát beállítja ha nem szerepelnek az adatbázisban,
     * ha valamelyik játékos szerepel az adatbázisban (játszott valaha),akkor
     * lekéri és azt állítja be.
     * @param gamer A Rókával játszó játékost reprezentáló {@code Gamer} osztály objektuma.
     * @param gamer2 A Kutyákkal játszó játékost reprezentáló {@code Gamer} osztály objektuma.
     */
    public void addTwoGamer(Gamer gamer, Gamer gamer2) {

        List<Gamer> firstGamerFromDB= persistenceOperations.findByName(gamer.getName());
        List<Gamer> secondGamerFromDB= persistenceOperations.findByName(gamer2.getName());


        if (!firstGamerFromDB.isEmpty()) {
            gamerWithFox =firstGamerFromDB.get(0);
        } else {
            gamerWithFox =gamer;
            persistenceOperations.persistGamer(gamerWithFox);
        }

        if (!secondGamerFromDB.isEmpty()) {
            gamerWithDog =secondGamerFromDB.get(0);
        } else {
            gamerWithDog =gamer2;
            persistenceOperations.persistGamer(gamerWithDog);
        }
        log.info("Added two Gamer: Fox:{}, and Dog: {}", gamerWithFox.getName(), gamerWithDog.getName());

    }

    private String stateStringRepr() {
        int[][] stateOfGame=state.getStateOfGame();
        String staterep="";
        for (int i=0; i<stateOfGame.length;i++) {
            for (int j=0; j<stateOfGame[i].length;j++) {
                staterep=staterep+stateOfGame[i][j];
            }
        }
        return staterep;
    }

    private void saveGameStation() {
        GameMembers gm = GameMembers.builder()
                .gamerWithFox(gamerWithFox)
                .scoreOfFox(actualScoreOfFox)
                .gamerWithDog(gamerWithDog)
                .scoreOfDog(actualScoreOfDog)
                .state(stateStringRepr())
                .build();
        persistenceOperations.persistGameStation(gm);
    }


}







