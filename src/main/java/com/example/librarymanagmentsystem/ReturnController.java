package com.example.librarymanagmentsystem;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ReturnController {
    @FXML
    private ImageView BACK;
    @FXML
    private TextField bookIdTF;
    @FXML
    private TextField memberIdTF;
    @FXML
    private TextField DateTF;
    @FXML
    private TableView<Borrow> table_Return;
    @FXML
    private TableColumn<Borrow, Integer> borrowingIDColumn;
    @FXML
    private TableColumn<Borrow, Date> borrowingDateColumn;
    @FXML
    private TableColumn<Borrow, Date> dueDateColumn;
    @FXML
    private TableColumn<Borrow, Integer> memberIDColumn;
    @FXML
    private TableColumn<Borrow, Integer> bookIDColumn;
    @FXML
    private ImageView searchImage;
    @FXML
    private Button returnButton;

    private ObservableList<Borrow> returnList = FXCollections.observableArrayList();
    private int selectedIndex = -1;

    private void loadBooks() {
        returnList.clear();
        try {
            Connection con = Connector.a.connectDB();
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM borrowing");
            while (result.next()) {
                int id = result.getInt("id");
                int bookId = result.getInt("book_id");
                int memberId = result.getInt("member_id");
                Date dueDate = result.getDate("due_date");
                Date borrowingDate = result.getDate("borrowing_date");

                Borrow borrow = new Borrow(id, bookId, memberId, dueDate, borrowingDate);
                returnList.add(borrow);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) BACK.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        borrowingIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        borrowingDateColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        memberIDColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        bookIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));

        table_Return.setItems(returnList);

        loadBooks();
    }

    @FXML
    void getSelectedCell(MouseEvent event) {
        selectedIndex = table_Return.getSelectionModel().getSelectedIndex();
        if (selectedIndex <= -1) {
            return;
        }
    }

    @FXML
    private void searchBook() {
        String bookId = bookIdTF.getText();
        String memberId = memberIdTF.getText();

        if (!bookId.matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid book ID.");
            alert.showAndWait();
            return;
        }
        returnList.clear();
        try (Connection con = Connector.a.connectDB();
             PreparedStatement pstmt = con.prepareStatement("SELECT * FROM borrowing b WHERE b.book_id=? AND b.member_id=? AND b.id NOT IN (SELECT r.b_id FROM returnbook r)")) {
            pstmt.setInt(1, Integer.parseInt(bookId));
            pstmt.setInt(2, Integer.parseInt(memberId));
            ResultSet result = pstmt.executeQuery();

            if (result.next()) {
                int id = result.getInt("id");
                int bookIdValue = result.getInt("book_id");
                int memberIdValue = result.getInt("member_id");
                Date dueDate = result.getDate("due_date");
                Date borrowingDate = result.getDate("borrowing_date");

                Borrow borrow = new Borrow(id, bookIdValue, memberIdValue, dueDate, borrowingDate);
                returnList.add(borrow);
            } else {
                bookIdTF.setText("");
                memberIdTF.setText("");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Borrowing Found");
                alert.setHeaderText(null);
                alert.setContentText("No borrowing record found with the specified book ID and member ID.");
                alert.showAndWait();
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error occurred while searching for the borrowing process.", e);
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

    private int calculateLateFee(long daysLate) {
        int feePerDay = 2;
        return (int) (daysLate * feePerDay);
    }

    private void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
