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


public class PublisherController {
    @FXML
    private TextField publisherIDTF;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField addressTF;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField phoneTF;
    @FXML
    private TableView table_Publisher;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nameColumn;
    @FXML
    private TableColumn addressColumn;
    @FXML
    private TableColumn emailColumn;
    @FXML
    private TableColumn phoneColumn;
    @FXML
    private ImageView addImageView;
    @FXML
    private ImageView listImageView;
    @FXML
    private TextField searchPublisherTF;
    @FXML
    private ImageView searchImageView;
    @FXML
    private TextField deletePublisherTF;
    @FXML
    private ImageView deleteImageView;
    @FXML
    private ImageView back;
    @FXML
    private ImageView updateImageView;
    private ObservableList<Publisher> publisher1 = FXCollections.observableArrayList();
    int index = -1;
    @FXML
    void getSelectCell(MouseEvent event) {
        index = table_Publisher.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        publisherIDTF.setText(idColumn.getCellData(index).toString());
        nameTF.setText(nameColumn.getCellData(index).toString());
        addressTF.setText(addressColumn.getCellData(index).toString());
        emailTF.setText(emailColumn.getCellData(index).toString());
        phoneTF.setText(phoneColumn.getCellData(index).toString());
    }
    public void initialize() throws ClassNotFoundException {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("publisherId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        loadPublishers();
        table_Publisher.setItems(publisher1);
    }
    private void loadPublishers() throws ClassNotFoundException {
        publisher1.clear();
        try {
            Connection con = Connector.a.connectDB();
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM publishers");
            while (result.next()) {
                publisher1.add(new Publisher(result.getInt("Id"), result.getString("name"),
                        result.getString("address"), result.getString("email"), result.getString("phone")));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
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
    public void deletePublisher() throws ClassNotFoundException {

        if (deletePublisherTF.getText().trim().isEmpty()) {
            // display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("ID cannot be empty");
            alert.showAndWait();
        } else {
            try {
                int id = Integer.parseInt(deletePublisherTF.getText());
                publisher1.clear();
                Connection con = Connector.a.connectDB();
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM publishers P WHERE  P.id = ?");
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
            loadPublishers();
            table_Publisher.refresh();

        }

    }
    @FXML
    public void addPublisher() throws ClassNotFoundException {
        try {
            int publisherId = Integer.parseInt(publisherIDTF.getText());
            String name = nameTF.getText();
            String address = addressTF.getText();
            String email = emailTF.getText();
            String phone = phoneTF.getText();

            Connection con = Connector.a.connectDB();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO publishers (id, name,  address,email, phone ) VALUES (?, ?, ?, ?, ?)");
            pstmt.setInt(1, publisherId);
            pstmt.setString(2, name);
            pstmt.setString(3, address);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.executeUpdate();
            con.close();

            // Clear the input fields
            publisherIDTF.clear();
            nameTF.clear();
            addressTF.clear();
            emailTF.clear();
            phoneTF.clear();

            // Refresh the table view to display the new publisher
            loadPublishers();
            table_Publisher.refresh();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to add publisher");
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
    public void searchPublisher() throws ClassNotFoundException {
        String searchInput = searchPublisherTF.getText().trim();

        if (searchInput.isEmpty()) {
            // If the search input is empty, reload all publishers
            loadPublishers();
            return;
        }

        try {
            publisher1.clear();
            Connection con = Connector.a.connectDB();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM publishers WHERE id = ?");
            pstmt.setInt(1, Integer.parseInt(searchInput));
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                publisher1.add(new Publisher(result.getInt("Id"), result.getString("name"),
                        result.getString("address"), result.getString("email"), result.getString("phone")));
            }

            con.close();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void updatePublisher() throws ClassNotFoundException {
        Publisher selectedPublisher = (Publisher) table_Publisher.getSelectionModel().getSelectedItem();

        if (selectedPublisher == null) {
            // No Publisher selected in the TableView
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Publisher selected");
            alert.setContentText("Please select a publisher from the table to update.");
            alert.showAndWait();
            return;
        }

        // Get the updated values from the input fields
        String idStr = publisherIDTF.getText();
        String name = nameTF.getText();
        String address = addressTF.getText();
        String email = emailTF.getText();
        String phone = phoneTF.getText();

        // Validate id and name fields
        if (idStr.trim().isEmpty() || name.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("ID and Name fields cannot be empty.");
            alert.showAndWait();
            return;
        }

        try {
            int id = Integer.parseInt(idStr);

            Connection con = Connector.a.connectDB();
            PreparedStatement pstmt = con.prepareStatement("UPDATE publishers SET id=?, name=?, address=?, email=?, phone=? WHERE id=?");
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, address);
            pstmt.setString(4, email);
            pstmt.setString(5, phone);
            pstmt.setInt(6, selectedPublisher.getPublisherId());
            pstmt.executeUpdate();
            con.close();

            // Clear the input fields
            publisherIDTF.clear();
            nameTF.clear();
            addressTF.clear();
            emailTF.clear();
            phoneTF.clear();

            // Refresh the TableView to reflect the changes
            loadPublishers();
            table_Publisher.refresh();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to update Publisher");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        } catch (NumberFormatException e) {
            // Handle the case when the user enters invalid numbers
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please enter valid numeric values for ID.");
            alert.showAndWait();
        }
    }


}
