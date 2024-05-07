package com.example.librarymanagmentsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {

    @FXML
    private ImageView returnBook;
    @FXML
    private ImageView report;
    @FXML
    private ImageView book;

    @FXML
    private ImageView member;

    @FXML
    private ImageView author;

    @FXML
    private ImageView publisher;

    @FXML
    private ImageView borrowing;

    @FXML
    public void initialize() {
        // You can perform any initialization here if needed
        // Set up the image views as buttons
        book.setOnMouseClicked(this::handleBookClick);
        member.setOnMouseClicked(this::handleMemberClick);
       // author.setOnMouseClicked(event -> handleAuthorClick(event));
        publisher.setOnMouseClicked(event -> handlePublisherClick(event));
        borrowing.setOnMouseClicked(event -> handleBorrowingClick(event));
    }

    @FXML
    private void handleBookClick(MouseEvent event) {
        // Load the book screen FXML and set it as the root scene
        loadAndSetScene("book.fxml");

    }

    @FXML
    private void handleMemberClick(MouseEvent event) {
        // Load the member screen FXML and set it as the root scene
        loadAndSetScene("Member.fxml");
    }

    @FXML
    private void handleAuthorClick(MouseEvent event) {
        // Load the author screen FXML and set it as the root scene
        loadAndSetScene("AuthorPage.fxml");
    }

    @FXML
    private void handlePublisherClick(MouseEvent event) {
        // Load the publisher screen FXML and set it as the root scene
        loadAndSetScene("publisher.fxml");
    }

    @FXML
    private void handleBorrowingClick(MouseEvent event) {
        // Load the borrowing screen FXML and set it as the root scene
        loadAndSetScene("borrowBook.fxml");
    }

    public void loadAndSetScene(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) book.getScene().getWindow(); // Get the current stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
public void exit()
{
    exit();
}
    public void returnBook(MouseEvent mouseEvent) {
        loadAndSetScene("return.fxml");
    }

    public void report(MouseEvent mouseEvent) {
        loadAndSetScene("report.fxml");
    }
}
