/*
Author: Reece Pieri
ID: M087496
Date: 25/09/2020
Assessment: Java III - Portfolio AT2 Q4
*/

package todolist;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

public class ToDoListController implements Serializable {
    @FXML public AnchorPane pane;
    @FXML public AnchorPane boardPane;
    @FXML public Button btnAddCard;
    @FXML public Button btnAddColumn;
    @FXML public Label lblAddCard;
    @FXML public Label lblAddColumn;
    @FXML public Label lblLogo;
    @FXML public MenuItem menuLoadBoard;
    @FXML public MenuItem menuSaveBoard;
    @FXML public MenuItem menuClose;
    @FXML public MenuItem menuClearCards;
    @FXML public MenuItem menuClearBoard;
    @FXML public MenuItem menuHowTo;
    @FXML public MenuItem menuAbout;
    private Board currentBoard;
    
    // Add new task
    @FXML private void btnAddCard_OnClick(ActionEvent event) {
        currentBoard.addCard();
    }
    
    @FXML private void btnAddColumn_OnClick(ActionEvent event) {
        currentBoard.addColumn();
    }
    
    public void setBoard(Board board) {
        currentBoard = board;
    }
    
    // Clear board
    public void clearBoard() {
        boardPane.getChildren().clear();
    }
    
    // Add styles to GUI controls
    public void setUpGUI() {
        // Background
        pane.setStyle("-fx-background-color: #343a40;");
        setMenuItems();
        noColumnsButtons();
        
        lblAddCard.setOnMouseClicked(event -> {
            currentBoard.addCard();
        });
        
        lblAddColumn.setOnMouseClicked(event -> {
            currentBoard.addColumn();
        });
        
        btnAddCard.setViewOrder(-10.0);
        btnAddColumn.setViewOrder(-10.0);
        lblAddCard.setViewOrder(-10.0);
        lblAddColumn.setViewOrder(-10.0);
        boardPane.setViewOrder(10.0);
        lblLogo.setViewOrder(10.0);
    }
    
    // Set actions for menu items
    private void setMenuItems() {
        menuLoadBoard.setOnAction(event -> {
            currentBoard.loadBoard();
        });
        
        menuSaveBoard.setOnAction(event -> {
            currentBoard.saveBoard();
        });
        
        menuClose.setOnAction(event -> {
            currentBoard.savePromptOnClose(event);
        });
        
        menuClearCards.setOnAction(event -> {
            currentBoard.clearCards();
        });
        
        menuClearBoard.setOnAction(event -> {
            currentBoard.clearCards();
            currentBoard.clearColumns(true);
            setButtonColours(0);
        });
        
        menuHowTo.setOnAction(event -> {
            try {
                File file = new java.io.File("./HowTo.html").getAbsoluteFile();
                Desktop.getDesktop().open(file); 
            } catch (IOException ex) {
                
            }
        });
        
        menuAbout.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                    "ToDoo!\n"
                            + "Developed to assist in task management.\n\n"
                            + "Created by Reece Pieri (M087496).\n"
                            + "Date: 2/10/2020\n"
                            + "South Metropolitan TAFE\n"
                            + "Diploma in Software Development, Java III - Portfolio Activity 5");
            alert.setTitle("About");
            alert.setHeaderText(null);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initOwner(currentBoard.stage);
            alert.showAndWait();
        });
    }
    
    // Button styles for empty board
    private void noColumnsButtons() {
        //Buttons
        //btnAddCard
        btnAddCard.setStyle("-fx-background-color: #343a40; "
                + "-fx-text-fill: white; "
                + "-fx-border-color: white;");
        btnAddCard.setOnMouseEntered(event -> {
            btnAddCard.setStyle("-fx-background-color: #dc3545;"
                + "-fx-text-fill: white; "
                + "-fx-border-color: white;");
            lblAddCard.setStyle("-fx-text-fill: #dc3545");
        });
        btnAddCard.setOnMouseExited(event -> {
            btnAddCard.setStyle("-fx-background-color: #343a40;"
                + "-fx-text-fill: white; "
                + "-fx-border-color: white;");
            lblAddCard.setStyle("-fx-text-fill: white");
        });

        //btnAddColumn
        btnAddColumn.setStyle("-fx-background-color: #343a40; "
                + "-fx-text-fill: white; "
                + "-fx-border-color: white;");
        btnAddColumn.setOnMouseEntered(event -> {
            btnAddColumn.setStyle("-fx-background-color: #dc3545;"
                + "-fx-text-fill: white; "
                + "-fx-border-color: white;");
            lblAddColumn.setStyle("-fx-text-fill: #dc3545");
        });
        btnAddColumn.setOnMouseExited(event -> {
            btnAddColumn.setStyle("-fx-background-color: #343a40;"
                + "-fx-text-fill: white; "
                + "-fx-border-color: white;");
            lblAddColumn.setStyle("-fx-text-fill: white");
        });
        
        //lblAddCard
        lblAddCard.setStyle("-fx-text-fill: white");
        lblAddCard.setOnMouseEntered(event -> {
            lblAddCard.setStyle("-fx-text-fill: #dc3545");
            btnAddCard.setStyle("-fx-background-color: #dc3545;"
                + "-fx-text-fill: white; "
                + "-fx-border-color: white;");
        });
        lblAddCard.setOnMouseExited(event -> {
            lblAddCard.setStyle("-fx-text-fill: white");
            btnAddCard.setStyle("-fx-background-color: #343a40;"
                + "-fx-text-fill: white; "
                + "-fx-border-color: white;");
        });
        
        //lblAddColumn
        lblAddColumn.setStyle("-fx-text-fill: white");
        lblAddColumn.setOnMouseEntered(event -> {
            lblAddColumn.setStyle("-fx-text-fill: #dc3545");
            btnAddColumn.setStyle("-fx-background-color: #dc3545;"
                + "-fx-text-fill: white; "
                + "-fx-border-color: white;");
        });
        lblAddColumn.setOnMouseExited(event -> {
            lblAddColumn.setStyle("-fx-text-fill: white");
            btnAddColumn.setStyle("-fx-background-color: #343a40;"
                + "-fx-text-fill: white; "
                + "-fx-border-color: white;");
        });
    }
    
    // Button styles for populated board
    private void columnsButtons() {
        //Buttons
        //btnAddCard
        btnAddCard.setStyle("-fx-background-color: #343a40; "
                + "-fx-text-fill: white; "
                + "-fx-border-color: black;");
        btnAddCard.setOnMouseEntered(event -> {
            btnAddCard.setStyle("-fx-background-color: #dc3545;"
                + "-fx-text-fill: white; "
                + "-fx-border-color: black;");
            lblAddCard.setStyle("-fx-text-fill: #dc3545");
        });
        btnAddCard.setOnMouseExited(event -> {
            btnAddCard.setStyle("-fx-background-color: #343a40;"
                + "-fx-text-fill: white; "
                + "-fx-border-color: black;");
            lblAddCard.setStyle("-fx-text-fill: black");
        });

        //btnAddColumn
        btnAddColumn.setStyle("-fx-background-color: #343a40; "
                + "-fx-text-fill: white; "
                + "-fx-border-color: black;");
        btnAddColumn.setOnMouseEntered(event -> {
            btnAddColumn.setStyle("-fx-background-color: #dc3545;"
                + "-fx-text-fill: white; "
                + "-fx-border-color: black;");
            lblAddColumn.setStyle("-fx-text-fill: #dc3545");
        });
        btnAddColumn.setOnMouseExited(event -> {
            btnAddColumn.setStyle("-fx-background-color: #343a40;"
                + "-fx-text-fill: white; "
                + "-fx-border-color: black;");
            lblAddColumn.setStyle("-fx-text-fill: black");
        });
        
        //lblAddCard
        lblAddCard.setStyle("-fx-text-fill: black");
        lblAddCard.setOnMouseEntered(event -> {
            lblAddCard.setStyle("-fx-text-fill: #dc3545");
            btnAddCard.setStyle("-fx-background-color: #dc3545;"
                + "-fx-text-fill: white; "
                + "-fx-border-color: black;");
        });
        lblAddCard.setOnMouseExited(event -> {
            lblAddCard.setStyle("-fx-text-fill: black");
            btnAddCard.setStyle("-fx-background-color: #343a40;"
                + "-fx-text-fill: white; "
                + "-fx-border-color: black;");
        });
        
        //lblAddColumn
        lblAddColumn.setStyle("-fx-text-fill: black");
        lblAddColumn.setOnMouseEntered(event -> {
            lblAddColumn.setStyle("-fx-text-fill: #dc3545");
            btnAddColumn.setStyle("-fx-background-color: #dc3545;"
                + "-fx-text-fill: white; "
                + "-fx-border-color: black;");
        });
        lblAddColumn.setOnMouseExited(event -> {
            lblAddColumn.setStyle("-fx-text-fill: black");
            btnAddColumn.setStyle("-fx-background-color: #343a40;"
                + "-fx-text-fill: white; "
                + "-fx-border-color: black;");
        });
    }
    
    public void setButtonColours(int columnNum) {
        if (columnNum == 0) {
            noColumnsButtons();
        } else {
            columnsButtons();
        }
    }

}
