package com.example.librarymanagmentsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class BookController {

    @FXML
    private TextField ISBNTF;
    @FXML
    private TextField EDITIONTF;
    @FXML
    private TextField AUTHORTF;
    @FXML
    private TextField PUBLISHERTF;
    @FXML
    private TextField bookIdTF;
    @FXML
    private TextField titleTF;
    @FXML
    private TextField NoofpagesTF;
    @FXML
    private TextField CategoryTF;
    @FXML
    private TextField AvailabilityTF;
    @FXML
    private TableView table_Book;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn titleCoulmn;
    @FXML
    private TableColumn isbnColumn;
    @FXML
    private TableColumn EditionNoColumn;
    @FXML
    private TableColumn noOfPagesColumn;
    @FXML
    private TableColumn authorColumn;
    @FXML
    private TableColumn publisherColumn;
    @FXML
    private TableColumn categoryColumn;
    @FXML
    private TableColumn availabilityColumn;
    @FXML
    private ImageView addImageView;
    @FXML
    private ImageView listImageView;
    @FXML
    private TextField updateBookTF;
    @FXML
    private ImageView updateImageView;
    @FXML
    private TextField searchBookTF;
    @FXML
    private ImageView searchImageView;
    @FXML
    private TextField deleteBookTF;
    @FXML
    private ImageView deleteImageView;
    @FXML
    private ImageView back;
    private ObservableList<Book> Book1 = FXCollections.observableArrayList();
    int index = -1;
    @FXML
    void getSelectCell(MouseEvent event) {
        index = table_Book.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        bookIdTF.setText(idColumn.getCellData(index).toString());
        titleTF.setText(titleCoulmn.getCellData(index).toString());
        ISBNTF.setText(isbnColumn.getCellData(index).toString());
        EDITIONTF.setText(EditionNoColumn.getCellData(index).toString());
        NoofpagesTF.setText(noOfPagesColumn.getCellData(index).toString());
        AUTHORTF.setText(authorColumn.getCellData(index).toString());
        PUBLISHERTF.setText(publisherColumn.getCellData(index).toString());
        CategoryTF.setText(categoryColumn.getCellData(index).toString());
        AvailabilityTF.setText(availabilityColumn.getCellData(index).toString());
    }
    public void initialize() throws ClassNotFoundException {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleCoulmn.setCellValueFactory(new PropertyValueFactory<>("title"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        EditionNoColumn.setCellValueFactory(new PropertyValueFactory<>("editionNo")); // Update the property name here
        noOfPagesColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfPages")); // Update the property name here
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("authorId"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisherId"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("Category"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
        loadBooks();
        table_Book.setItems(Book1);
    }
    private void loadBooks() throws ClassNotFoundException {
        Book1.clear();
        try {
            Connection con = Connector.a.connectDB();
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM Books");
            while (result.next()) {
                Book1.add(new Book(result.getInt("bookId"), result.getString("title"), result.getInt("ISBN"),
                        result.getInt("editionNo"), result.getInt("numberOfPages"), result.getInt("authorId"),
                        result.getInt("publisherId"), result.getString("category"),result.getInt("availability")));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void Back() {
        try {
            Stage stage = (Stage) back.getScene().getWindow();
            stage.close();

            AnchorPane root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
            Scene scene = new Scene(root, 1000, 700);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Properly handle the IOException
            e.printStackTrace();
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    @FXML
    public void deleteBook() throws ClassNotFoundException {

        if (deleteBookTF.getText().trim().isEmpty()) {
            // display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("ID cannot be empty");
            alert.showAndWait();
        } else {
            try {
                int id = Integer.parseInt(deleteBookTF.getText());
                Book1.clear();
                Connection con = Connector.a.connectDB();
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM Books B WHERE  B.bookId = ?");
                pstmt.setInt(1, id);
                pstmt.executeUpdate();

                con.close();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setHeaderText("failed!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            loadBooks();
            table_Book.refresh();

        }

    }
    @FXML
    public void addBook() throws ClassNotFoundException {
        try {
            int bookId = Integer.parseInt(bookIdTF.getText());
            String title = titleTF.getText();
            int ISBN = Integer.parseInt(ISBNTF.getText());
            int editionNo = Integer.parseInt(EDITIONTF.getText());
            int numberOfPages = Integer.parseInt(NoofpagesTF.getText());
            int authorId = Integer.parseInt(AUTHORTF.getText());
            int publisherId = Integer.parseInt(PUBLISHERTF.getText());
            String category = CategoryTF.getText();
            int availability = Integer.parseInt(AvailabilityTF.getText());

            Connection con = Connector.a.connectDB();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Books (bookId, title, ISBN, editionNo, numberOfPages, authorId, publisherId, category, availability) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, bookId);
            pstmt.setString(2, title);
            pstmt.setInt(3, ISBN);
            pstmt.setInt(4, editionNo);
            pstmt.setInt(5, numberOfPages);
            pstmt.setInt(6, authorId);
            pstmt.setInt(7, publisherId);
            pstmt.setString(8, category);
            pstmt.setInt(9, availability);
            pstmt.executeUpdate();
            con.close();

            // Clear the input fields
            bookIdTF.clear();
            titleTF.clear();
            ISBNTF.clear();
            EDITIONTF.clear();
            NoofpagesTF.clear();
            AUTHORTF.clear();
            PUBLISHERTF.clear();
            CategoryTF.clear();
            AvailabilityTF.clear();

            // Refresh the table view to display the new book
            loadBooks();
            table_Book.refresh();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to add Book");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (NumberFormatException e) {
            // Handle the case when the user enters invalid numbers
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please enter valid numeric values.");
            alert.showAndWait();
        }

    }
    @FXML
    public void searchBook() throws ClassNotFoundException {
        String searchInput = searchBookTF.getText().trim();

        if (searchInput.isEmpty()) {
            // If the search input is empty, reload all books
            loadBooks();
            return;
        }

        try {
            Book1.clear();
            Connection con = Connector.a.connectDB();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Books WHERE bookId = ?");
            pstmt.setInt(1, Integer.parseInt(searchInput));
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                Book1.add(new Book(
                        result.getInt("bookId"),
                        result.getString("title"),
                        result.getInt("ISBN"),
                        result.getInt("editionNo"),
                        result.getInt("numberOfPages"),
                        result.getInt("authorId"),
                        result.getInt("publisherId"),
                        result.getString("category"),
                        result.getInt("availability")
                ));
            }

            con.close();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void updateBook() throws ClassNotFoundException {
        Book selectedBook = (Book) table_Book.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            // No book selected in the TableView
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No book selected");
            alert.setContentText("Please select a book from the table to update.");
            alert.showAndWait();
            return;
        }

        try {
            // Get the updated values from the input fields
            int bookId = Integer.parseInt(bookIdTF.getText());
            String title = titleTF.getText();
            int ISBN = Integer.parseInt(ISBNTF.getText());
            int editionNo = Integer.parseInt(EDITIONTF.getText());
            int numberOfPages = Integer.parseInt(NoofpagesTF.getText());
            int authorId = Integer.parseInt(AUTHORTF.getText());
            int publisherId = Integer.parseInt(PUBLISHERTF.getText());
            String category = CategoryTF.getText();
            int availability = Integer.parseInt(AvailabilityTF.getText());

            Connection con = Connector.a.connectDB();
            PreparedStatement pstmt = con.prepareStatement("UPDATE Books SET bookId=?, title=?, ISBN=?, editionNo=?, numberOfPages=?, authorId=?, publisherId=?, category=?, availability=? WHERE bookId=?");
            pstmt.setInt(1, bookId);
            pstmt.setString(2, title);
            pstmt.setInt(3, ISBN);
            pstmt.setInt(4, editionNo);
            pstmt.setInt(5, numberOfPages);
            pstmt.setInt(6, authorId);
            pstmt.setInt(7, publisherId);
            pstmt.setString(8, category);
            pstmt.setInt(9, availability);
            pstmt.setInt(10, selectedBook.getBookId());
            pstmt.executeUpdate();
            con.close();

            // Clear the input fields
            bookIdTF.clear();
            titleTF.clear();
            ISBNTF.clear();
            EDITIONTF.clear();
            NoofpagesTF.clear();
            AUTHORTF.clear();
            PUBLISHERTF.clear();
            CategoryTF.clear();
            AvailabilityTF.clear();

            // Refresh the TableView to reflect the changes
            loadBooks();
            table_Book.refresh();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to update Book");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (NumberFormatException e) {
            // Handle the case when the user enters invalid numbers
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please enter valid numeric values.");
            alert.showAndWait();
        }
    }



}

