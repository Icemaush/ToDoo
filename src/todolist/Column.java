package todolist;

import java.io.Serializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Column implements Serializable {
    public transient AnchorPane grid;
    public String header;
    public Colour bgColour;
    public enum Colour {
        Blue, Green, Red, Yellow
    }
    private transient TextField textName;
    private transient Label lblRemove;
    private transient Label lblChangeColour;
    private Board board;
    public int posX;
    public double colWidth;
    
    public Column(Stage stage, Board board) {
        createColumn(stage, board);
    }
    
    // Constructor for loading board
    public Column(Stage stage, Board board, String header, Colour bgColour, int posX, double colWidth) {
        createColumn(stage, board);
        this.header = header;
        this.posX = posX;
        this.colWidth = colWidth;
        textName.setText(this.header);
        setColour(bgColour);
    }
    
    // Create column
    private void createColumn(Stage stage, Board board) {
        this.board = board;
        grid = new AnchorPane();
        grid.setViewOrder(10.0);
        grid.setId("column");
        grid.setPrefHeight(stage.getHeight());

        // Add column header field
        textName = new TextField();
        textName.setAlignment(Pos.CENTER);
        textName.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20));
        AnchorPane.setTopAnchor(textName, 20.0);
        AnchorPane.setLeftAnchor(textName, 50.0);
        AnchorPane.setRightAnchor(textName, 50.0);
        grid.getChildren().add(textName);
        
        addColumnButtons();
        
        grid.setOnMouseClicked(event -> {
            grid.requestFocus();
        });
        
        textName.setOnKeyTyped(event -> {
            this.header = textName.getText();
        });
    }
    
    // Adjust size of columns when adding new columns/removing columns
    public void adjustSize(int position, double width) {
        grid.setLayoutX(position);
        grid.setPrefWidth(width);
        
        // adjust size of the rest of the controls
        textName.setPrefWidth(grid.getPrefWidth() / 1.2);
    }
    
    // Set column background colour 
    public final void setColour(Colour colour) {
        switch (colour) {
            case Blue -> {
                grid.setStyle("-fx-background-color: #CCE5FF");
                bgColour = Colour.Blue;
            }
            case Green -> {
                grid.setStyle("-fx-background-color: #D4EDDA");
                bgColour = Colour.Green;
            }
            case Red -> {
                grid.setStyle("-fx-background-color: #F8D7DA");
                bgColour = Colour.Red;
            }
            case Yellow -> {
                grid.setStyle("-fx-background-color: #FFF3CD");
                bgColour = Colour.Yellow;
            }
            default -> {
                grid.setStyle("-fx-background-color: #CCE5FF");
                bgColour = Colour.Blue;
            }
        }
    }
    
    // Add close and colour change buttons to column
    private void addColumnButtons() {
        // Add remove column button
        lblRemove = new Label();
        lblRemove.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 12));
        lblRemove.setTextFill(Color.web("#343a40"));
        lblRemove.setPadding(new Insets(0, 5, 0, 5));
        lblRemove.setText("X");
        AnchorPane.setTopAnchor(lblRemove, 0.0);
        AnchorPane.setRightAnchor(lblRemove, 0.0);
        grid.getChildren().add(lblRemove);
        
        lblRemove.setOnMouseClicked(event -> {
            board.removeColumn(this);
        });
        lblRemove.setOnMouseEntered(event -> {
            lblRemove.setTextFill(Color.web("#dc3545"));
        });
        lblRemove.setOnMouseExited(event -> {
            lblRemove.setTextFill(Color.web("#343a40"));
        });
        lblRemove.setCursor(Cursor.HAND);
        
        // Add change colour button
        lblChangeColour = new Label();
        lblChangeColour.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 12));
        lblChangeColour.setTextFill(Color.web("#343a40"));
        lblChangeColour.setPadding(new Insets(0, 5, 0, 5));
        lblChangeColour.setText("Colour");
        AnchorPane.setTopAnchor(lblChangeColour, 0.0);
        AnchorPane.setRightAnchor(lblChangeColour, 20.0);
        grid.getChildren().add(lblChangeColour);
        
        lblChangeColour.setOnMouseClicked(event -> {
            cycleColour();
        });
        lblChangeColour.setOnMouseEntered(event -> {
            lblChangeColour.setTextFill(Color.web("#dc3545"));
        });
        lblChangeColour.setOnMouseExited(event -> {
            lblChangeColour.setTextFill(Color.web("#343a40"));
        });
        lblChangeColour.setCursor(Cursor.HAND);
    }
    
    private void cycleColour() {
        switch (bgColour) {
            case Blue -> setColour(Colour.Green);
            case Green -> setColour(Colour.Yellow);
            case Yellow -> setColour(Colour.Red);
            case Red -> setColour(Colour.Blue);
        }
    }
    
}
