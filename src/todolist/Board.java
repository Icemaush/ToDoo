package todolist;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Board implements Serializable {
    public final transient ToDoListController controller;
    public final transient Stage stage;
    private final List<Column> columnList;
    private final List<Card> cardList;
    private double colWidth;
    private static int cardCount = 0;
    
    public Board(ToDoListController controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;
        columnList = new ArrayList();
        cardList = new ArrayList();
    }
    
    // Add column to board
    public void addColumn() {
        if (columnList.size() == 4) {
            Alert alert = new Alert(AlertType.ERROR, "Maximum number of columns reached.");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            Column column = new Column(stage, this);
            columnList.add(column);
            
            // Assign colour to new column
            switch (columnList.size()) {
                case 1 -> column.setColour(Column.Colour.Blue);
                case 2 -> column.setColour(Column.Colour.Green);
                case 3 -> column.setColour(Column.Colour.Yellow);
                case 4 -> column.setColour(Column.Colour.Red);
                default -> column.setColour(Column.Colour.Blue);
            }
            clearColumns(false);
            displayColumns();
        }
    }
    
    // Remove column
    public void removeColumn(Column column) {
        if (columnList.size() == 1 && !cardList.isEmpty()) {
                ButtonType yes = new ButtonType("Yes");
                ButtonType no = new ButtonType("No");
                Alert alert = new Alert(AlertType.CONFIRMATION, "Removing all columns will clear all cards!\nDo you want to continue?", yes, no);
                alert.setTitle("Warning!");
                alert.setHeaderText(null);
                alert.showAndWait().ifPresent(response -> {
                    if (response == yes) {
                        clearCards();
                        clearColumns(false);
                        columnList.remove(column);
                        displayColumns();
                    }
                });
        } else {
            clearColumns(false);
            columnList.remove(column);
            displayColumns();
        }
    }
    
    // Remove card
    public void removeCard(Card card) {
        try {
            controller.boardPane.getChildren().remove(card.grid);
            cardList.remove(card);
            cardCount--;
        } catch (NullPointerException ex) {
            
        }
    }
    
    // Add card to board 
    public void addCard() {
        if (columnList.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR, "Add a column first!");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            if (cardCount == 40) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Card limit reached!\nRemove some cards to add new ones.");
                alert.setTitle("Card Limit Reached");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                Card card = new Card(stage, this);
                controller.boardPane.getChildren().add(card.grid);
                card.focusCard();
                cardList.add(card);
                cardCount++;
            }
        }
    }
    
    // Display cards
    public void displayCards() {
        if (!cardList.isEmpty()) {
            for (Card card : cardList) {
                card.grid.setLayoutX(card.layoutX);
                card.grid.setLayoutY(card.layoutY);
                controller.boardPane.getChildren().add(card.grid);
            }
        }
    }
    
    // Display columns
    public void displayColumns() {
        if (!columnList.isEmpty()) {
            int posX = 0;
            colWidth = (stage.getWidth() - 16) / columnList.size();

            for (Column column : columnList) {
                column.posX = posX;
                column.colWidth = colWidth;
                column.adjustSize(posX, colWidth);
                controller.boardPane.getChildren().add(column.grid);
                posX += colWidth;
            }
            
            for (Card card : cardList) {
                card.adjustSize(colWidth);
            }
        }
        controller.setButtonColours(columnList.size());
    }
    
    // Clear columns
    public void clearColumns(boolean clearColumnList) {
        try {
            for (Column column : columnList) {
                controller.boardPane.getChildren().remove(column.grid);
            }
            if (clearColumnList) {
                columnList.clear();
            }
            
        } catch (NullPointerException ex) {
            
        }
    }
    
    // Remove all cards
    public void clearCards() {
        try {
            for (Card card : cardList) {
                controller.boardPane.getChildren().remove(card.grid);
            }
            cardList.clear();
            cardCount = 0;
        } catch (NullPointerException ex) {
            
        }
    }
    
    // Load board
    public void loadBoard() {
        try (FileInputStream fileIn = new FileInputStream("board.bin")) {
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            Board loadedBoard = (Board)objIn.readObject();
            
            // Load columns
            clearColumns(true);
            for (Column column : loadedBoard.columnList) {
                Column loadedColumn = new Column(stage, this, column.header, column.bgColour, column.posX, column.colWidth);
                columnList.add(loadedColumn);
            }
            displayColumns();
            
            // Load cards
            clearCards();
            for (Card card : loadedBoard.cardList) {
                Card loadedCard = new Card(stage, this, card.layoutX, card.layoutY, card.taskName, card.taskDescription);
                cardList.add(loadedCard);
            }
            displayCards();
            
        } catch (IOException | ClassNotFoundException | NullPointerException e) {

        }
    }
    
    // Save board
    public void saveBoard() {
        try (FileOutputStream fileOut = new FileOutputStream("board.bin")) {
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Board saved.");
            alert.setTitle("Save Board");
            alert.setHeaderText(null);
            alert.show();
        } catch (IOException | NullPointerException e) {

        }
    }
    
    // Save prompt on program close
    public void savePromptOnClose(Event event) {
        if (!columnList.isEmpty()) {
            ButtonType yes = new ButtonType("Yes");
            ButtonType no = new ButtonType("No");
            ButtonType cancel = new ButtonType("Cancel");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to save the current board?", yes, no, cancel);
            alert.setTitle("Save Board");
            alert.setHeaderText(null);
            alert.showAndWait().ifPresent(response -> {
                if (response == yes) {
                    saveBoard();
                    Platform.exit();
                } else if (response == no) {
                    Platform.exit();
                } else {
                    event.consume();
                }
            });
        } else {
            Platform.exit();
        }
    }
    
    public List<Column> getColumnList() {
        return columnList;
    }
    
    public List<Card> getCardList() {
        return cardList;
    }
    
    public double getColWidth() {
        return colWidth;
    }
    
    public int getCardCount() {
        return cardCount;
    }
}
