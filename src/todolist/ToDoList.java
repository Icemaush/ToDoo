/*
Author: Reece Pieri
ID: M087496
Date: 25/09/2020
Assessment: Java III - Portfolio AT2 Q5
*/

package todolist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ToDoList extends Application {
    private ToDoListController controller;
    private Board currentBoard;
    private Stage stage;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ToDoList.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        getController().setUpGUI();
        
        getController().pane.setOnMouseClicked(event -> {
            getController().pane.requestFocus();
        });
        
        stage.setTitle("ToDoo!");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            currentBoard.savePromptOnClose(event);
        });
        this.stage = stage;
        currentBoard = new Board(controller, stage);
        controller.setBoard(currentBoard);
        stage.show();
    }

    public ToDoListController getController() {
        return controller;
    }

    public Stage getStage() {
        return stage;
    }
    
}
