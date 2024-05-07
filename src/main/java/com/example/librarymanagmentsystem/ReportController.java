package com.example.librarymanagmentsystem;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportController {
    @FXML
    private Label totalSubscribers;
    @FXML
    private Label BMID;//borrowed member id
    @FXML
    private Label NBookBorrowed;//number of book borrowed
    @FXML
    private Label totalBook;
    @FXML
    private PieChart addressGroup;///??
    @FXML
    private PieChart Gender;
    @FXML
    private ImageView B;

    @FXML

    void Back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) B.getScene().getWindow(); // Get the current stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void initialize() {
        try {
            int num1 = 0;
            Connector.a.connectDB();
            PreparedStatement st = Connector.a.connectDB().prepareStatement("select count(*) from books;");
            ResultSet r = st.executeQuery();
            if (r.next()) {
                num1 = r.getInt(1);
            }

            this.totalBook.setText(String.valueOf(num1));
            int num2 = 0;
            PreparedStatement st1 = Connector.a.connectDB().prepareStatement("select count(*) from member");
            ResultSet r1 = st1.executeQuery();
            if (r1.next()) {
                num2 = r1.getInt(1);
            }

            this.totalSubscribers.setText(String.valueOf(num2));
            int num3 = 0;
            PreparedStatement st2 = Connector.a.connectDB().prepareStatement("select DISTINCT count(DISTINCT borrowing.id ) from borrowing");
            ResultSet r2 = st2.executeQuery();
            if (r2.next()) {
                num3 = r2.getInt(1);
            }

            this.BMID.setText(String.valueOf(num3));
            int num4 = 0;
            PreparedStatement st3 = Connector.a.connectDB().prepareStatement("select count(borrowing.id ) from borrowing");
            ResultSet r3 = st3.executeQuery();
            if (r3.next()) {
                num4 = r3.getInt(1);
            }

            this.NBookBorrowed.setText(String.valueOf(num4));
            int First = 0;
            int second = 0;
            int Third = 0;
            PreparedStatement st4 = Connector.a.connectDB().prepareStatement("SELECT COUNT(*) FROM borrowing WHERE due_date < CURDATE()");
            ResultSet r4 = st4.executeQuery();
            if (r4.next()) {
                First = r4.getInt(1);
            }

            PreparedStatement st5 = Connector.a.connectDB().prepareStatement("SELECT COUNT(*) FROM borrowing WHERE due_date > CURDATE()");
            ResultSet r5 = st5.executeQuery();
            if (r5.next()) {
                second = r5.getInt(1);
            }



            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data(" late", (double)First), new PieChart.Data("not late", (double)second));
            this.addressGroup.setData(pieChartData);
            int First1 = 0;
            int second2 = 0;
            PreparedStatement st7 = Connector.a.connectDB().prepareStatement(    "select count(*) from member S where S.gender like 'm%';");
            ResultSet r7 = st7.executeQuery();
            if (r7.next()) {
                First1 = r7.getInt(1);
            }

            PreparedStatement st8 = Connector.a.connectDB().prepareStatement("select count(*) from member S where S.gender like 'f%';");
            ResultSet r8 = st8.executeQuery();
            if (r8.next()) {
                second2 = r8.getInt(1);
            }

            ObservableList<PieChart.Data> pieChartData1 = FXCollections.observableArrayList(new PieChart.Data("male", (double)First1), new PieChart.Data("female", (double)second2));
            this.Gender.setData(pieChartData1);
            Connector.a.connectDB().close();
        } catch (ClassNotFoundException | SQLException var30) {
            var30.printStackTrace();
        }

    }
    }

