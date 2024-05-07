package com.example.librarymanagmentsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BorrowController {


    public ImageView searchImage;
    @FXML
    private ImageView BACK  ;
    @FXML
    private TextField memberIdTF;
    @FXML
    private TextField memberNameTF;
    @FXML
    private TextField memberEmailTF;
    @FXML
    private TextField bookIdTF;
    @FXML
    private TextField bookNameTF;
    @FXML
    private TextField categoryTF;
    @FXML
    private TextField searchBookTF;
    @FXML
    private TextField searchMemberTF;
    @FXML
    private TextField borrowingDateTF;
    @FXML
    private TextField DueDateTF;
    @FXML
    private ImageView searchMemberImage;
    @FXML
    private ImageView searchBookImage;
    @FXML
    private ImageView borrowingImage;
    private void initialize() {
        // Initialize the controller
    }
@FXML
    private void searchMember() {
        String memberId = searchMemberTF.getText();

        try (Connection con = Connector.a.connectDB();
             PreparedStatement pstmt = con.prepareStatement("SELECT id, name, email FROM member WHERE id = ?")) {
            pstmt.setInt(1, Integer.parseInt(memberId));
            ResultSet result = pstmt.executeQuery();

            if (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String email = result.getString("email");

                // Update the text fields in the VBox
                memberIdTF.setText(memberId);
                memberNameTF.setText(name);
                memberEmailTF.setText(email);
            } else {
                // Clear the text fields if no member is found
                memberIdTF.setText("");
                memberNameTF.setText("");
                memberEmailTF.setText("");

                // Display an alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Member Not Found");
                alert.setHeaderText(null);
                alert.setContentText("No member found with ID: " + memberId);
                alert.showAndWait();
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error occurred while searching for a member.", e);
        }
    }

    @FXML

    private void searchBook() {
        String bookId = searchBookTF.getText();
        System.out.println(bookId);
        if (!bookId.matches("\\d+")) {
            // Display an alert for invalid input
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid book ID.");
            alert.showAndWait();
            return;
        }

        try (Connection con = Connector.a.connectDB();
             PreparedStatement pstmt = con.prepareStatement("SELECT bookId, title, availability FROM books WHERE bookId = ?")) {
            pstmt.setInt(1, Integer.parseInt(bookId));
            ResultSet result = pstmt.executeQuery();

            if (result.next()) {
                int id = result.getInt("bookId");
                String title = result.getString("title");
                boolean availability = result.getBoolean("availability");

                // Update the text fields in the VBox
                bookIdTF.setText(bookId);
                bookNameTF.setText(title);
                categoryTF.setText(availability ? "Available" : "Not Available");
            } else {
                // Clear the text fields if no book is found
                bookIdTF.setText("");
                bookNameTF.setText("");
                categoryTF.setText("");

                // Display an alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Book Not Found");
                alert.setHeaderText(null);
                alert.setContentText("No book found with ID: " + bookId);
                alert.showAndWait();
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error occurred while searching for a book.", e);
        }
    }


    @FXML


    private void borrowBook() {
        // Get the input values
        String memberId = searchMemberTF.getText();
        String bookId = searchBookTF.getText();
        Date borrowingDate = parseDate(borrowingDateTF.getText());
        Date dueDate = parseDate(DueDateTF.getText());

        try (Connection con = Connector.a.connectDB()) {
            // Check the availability of the book and verify the existence of the book and member
            boolean borrowingPossible = isBorrowingPossible(con, memberId, bookId);
            if (!borrowingPossible) {
                System.out.println("Borrowing is not possible. Please check member ID, book ID, and availability.");
                return;
            }

            // Perform the borrowing operation
            boolean success = borrowBook(con, memberId, bookId, borrowingDate, dueDate);
            if (success) {
                // Decrement the book availability
                decrementBookAvailability(con, bookId);

                System.out.println("Book borrowed successfully!");

                // Clear the input fields
                memberIdTF.setText("");
                memberNameTF.setText("");
                memberEmailTF.setText("");
                bookIdTF.setText("");
                bookNameTF.setText("");
                categoryTF.setText("");
                borrowingDateTF.setText("");
                DueDateTF.setText("");

                // Display a success alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Borrowing Success");
                alert.setHeaderText(null);
                alert.setContentText("Book borrowed successfully!");
                alert.showAndWait();
            } else {
                System.out.println("Failed to borrow the book. Please try again.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error occurred while borrowing the book.", e);
        }
    }

    private boolean isBorrowingPossible(Connection con, String memberId, String bookId) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM books WHERE bookId = ? AND availability > 0";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(bookId));
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                int count = result.getInt("count");
                return count > 0;
            }
            return false;
        }
    }

    private boolean borrowBook(Connection con, String memberId, String bookId, Date borrowingDate, Date dueDate) throws SQLException {
        String query = "INSERT INTO borrowing (borrowing_date, due_date, member_id, book_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setDate(1, new java.sql.Date(borrowingDate.getTime()));
            pstmt.setDate(2, new java.sql.Date(dueDate.getTime()));
            pstmt.setInt(3, Integer.parseInt(memberId));
            pstmt.setInt(4, Integer.parseInt(bookId));
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    private void decrementBookAvailability(Connection con, String bookId) throws SQLException {
        String query = "UPDATE books SET availability = availability - 1 WHERE bookId = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(bookId));
            pstmt.executeUpdate();
        }
    }

    private Date parseDate(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    @FXML
    public void back() {
        try {
            Stage stage = (Stage) BACK.getScene().getWindow();
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

}



