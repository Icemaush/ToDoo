/*
Author: Reece Pieri
ID: M087496
Date: 25/09/2020
Assessment: Java III - Portfolio AT2 Q4
*/

package todolist;

import java.io.Serializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Card implements Serializable {
    private Board board;
    public transient AnchorPane grid;
    private static int posX = 10;
    private static int posY = 90;
    public double layoutX;
    public double layoutY;
    public String taskName;
    public String taskDescription;
    private transient TextField textName;
    private transient TextArea textDescription;
    private transient Label lblRemove;
    
    public Card(Stage stage, Board board) {
        createCard(stage, board);
    }
    
    // Contructor for loading board
    public Card(Stage stage, Board board, double layoutX, double layoutY, String taskName, String taskDescription) {
        createCard(stage, board);
        this.layoutX = layoutX;
        this.layoutY = layoutY;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        textName.setText(taskName);
        textDescription.setText(taskDescription);
    }
    
    // Create card
    private void createCard(Stage stage, Board board) {
        this.board = board;
        grid = new AnchorPane();
        grid.setStyle("-fx-background-radius: 10;"
                + "-fx-background-color: #adb5bd;");
        grid.setId("card");
        grid.setMaxWidth(300);
        grid.setPrefWidth(board.getColWidth() / 2);
        
        // Set position
        if (board.getCardList().isEmpty()) {
            posX = 10;
            posY = 90;
        } else if (board.getCardCount() == 20) {
            posX = 100;
            posY = 90;
        }
        grid.setLayoutX(posX);
        grid.setLayoutY(posY);
        layoutX = posX;
        layoutY = posY;
        posX += 30;
        posY += 30;
        
        // Add task name field
        textName = new TextField();
        textName.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 16));
        textName.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(textName, 10.0);
        AnchorPane.setLeftAnchor(textName, 10.0);
        AnchorPane.setRightAnchor(textName, 10.0);
        textName.setPrefWidth(200);
        grid.getChildren().add(textName);
        
        // Add task description field
        textDescription = new TextArea();
        textDescription.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 12));
        textDescription.setWrapText(true);
        AnchorPane.setTopAnchor(textDescription, 47.0);
        AnchorPane.setLeftAnchor(textDescription, 10.0);
        AnchorPane.setRightAnchor(textDescription, 10.0);
        AnchorPane.setBottomAnchor(textDescription, 10.0);
        textDescription.setPrefWidth(grid.getPrefWidth() / 1.2);
        textDescription.setPrefRowCount(2);
        grid.getChildren().add(textDescription);
        
        addCloseButton();

// CLICK/DRAG EVENTS
// <editor-fold>
    //GRID
        //Bring card to front if clicked
        grid.setOnMouseClicked(event -> {
            focusCard();
        });
        
        grid.setOnMouseEntered(event -> {
            grid.setCursor(Cursor.OPEN_HAND);
        });
        
        grid.setOnMousePressed(event -> {
            grid.setCursor(Cursor.CLOSED_HAND);
        });
        
        grid.setOnMouseReleased(event -> {
            grid.setCursor(Cursor.OPEN_HAND);
        });
        
        grid.setOnMouseExited(event -> {
            grid.setCursor(Cursor.DEFAULT);
        });
        
        grid.setOnMouseDragged(event -> {
            grid.setCursor(Cursor.CLOSED_HAND);
            grid.setTranslateX(event.getX() + grid.getTranslateX());
            grid.setTranslateY(event.getY() + grid.getTranslateY());
            event.consume();
        });
        
        grid.setOnDragDetected(event -> {
            grid.startFullDrag();
        });
        
        grid.setOnMouseDragReleased(event -> {           
            layoutX = grid.localToScene(grid.getBoundsInLocal()).getMinX();
            layoutY = grid.localToScene(grid.getBoundsInLocal()).getMinY();
        });
    //NAME
        // Bring selected card to front
        textName.setOnMouseClicked(event -> {
            focusCard();
        });

        // Change cursor
        textName.setOnMousePressed(event -> {
            textName.setCursor(Cursor.CLOSED_HAND);
        });
        
        textName.setOnMouseReleased(event -> {
            textName.setCursor(Cursor.TEXT);
        });
        
        textName.setOnMouseDragged(event -> {
            grid.setTranslateX(event.getX() + grid.getTranslateX());
            grid.setTranslateY(event.getY() + grid.getTranslateY());
            event.consume();
        });
    //DESCRIPTION
        textDescription.setOnMouseClicked(event -> {
            focusCard();
        });
        
        textDescription.setOnMousePressed(event -> {
            textDescription.setCursor(Cursor.CLOSED_HAND);
        });
            
        textDescription.setOnMouseDragged(event -> {
            grid.setTranslateX(event.getX() + grid.getTranslateX());
            grid.setTranslateY(event.getY() + grid.getTranslateY());
            event.consume();
        });
        
// </editor-fold>

// TEXT CHANGE EVENTS
// <editor-fold>
        textName.setOnKeyTyped(event -> {
            taskName = textName.getText();
        });
        
        textDescription.setOnKeyTyped(event -> {
            taskDescription = textDescription.getText();
        });
        
// </editor-fold>
    
    }
    
    // Adjust size of cards when adding new columns/removing columns
    public void adjustSize(double width) {
        grid.setPrefWidth(width / 2);
        textName.setPrefWidth(grid.getPrefWidth() / 1.2);
        textDescription.setPrefWidth((grid.getPrefWidth() / 1.2));
    }
    
    // Focus selected card
    public void focusCard() {
        grid.setViewOrder(-1.0);
        for (Card card : board.getCardList()) {
            if (!card.equals(this)) {
                card.grid.setViewOrder(1.0);
            }
        }
    }
    
    // Add close and colour change buttons to column
    private void addCloseButton() {
        // Add remove column button
        lblRemove = new Label();
        lblRemove.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 8));
        lblRemove.setTextFill(Color.web("#343a40"));
        lblRemove.setText("X");
        AnchorPane.setTopAnchor(lblRemove, 0.0);
        AnchorPane.setRightAnchor(lblRemove, 4.0);
        grid.getChildren().add(lblRemove);
        
        lblRemove.setOnMouseClicked(event -> {
            board.removeCard(this);
        });
        lblRemove.setOnMouseEntered(event -> {
            lblRemove.setTextFill(Color.web("#dc3545"));
        });
        lblRemove.setOnMouseExited(event -> {
            lblRemove.setTextFill(Color.web("#343a40"));
        });
        lblRemove.setCursor(Cursor.HAND);
    }
    
}
