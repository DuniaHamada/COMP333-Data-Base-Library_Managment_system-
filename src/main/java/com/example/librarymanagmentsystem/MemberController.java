package com.example.librarymanagmentsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class MemberController {

    @FXML
    private TextField ID;
    @FXML
    private TextField Name;
    @FXML
    private TextField Data_Birth;
    @FXML
    private TextField Email;
    @FXML
    private TextField Phone;
    @FXML
    private TextField address;
    @FXML
    private TextField gender;
    @FXML
    private TextField Data_Subscribing;
    @FXML
    private TextField Data_Canceling;
    @FXML
    private TableView<Member> table_Member;
    @FXML
    private TableColumn<Member, Integer> Col_id;
    @FXML
    private TableColumn<Member, String> Col_Name;
    @FXML
    private TableColumn<Member, String> Col_Date_Birth;
    @FXML
    private TableColumn<Member, String> Col_Email;
    @FXML
    private TableColumn<Member, Integer> Col_phone;
    @FXML
    private TableColumn<Member, String> Col_Address;
    @FXML
    private TableColumn<Member, String> Col_Gender;
    @FXML
    private TableColumn<Member, String> Col_DateOfSubscribing;
    @FXML
    private TableColumn<Member, String> Col_DateOfCanceling;
    @FXML
    private ImageView inseartimage;
    @FXML
    private ImageView deleteimage;
    @FXML
    private ImageView searchimage;
    @FXML
    private ImageView updateimage;
   
    @FXML
    private ImageView backimage;
    
    private ObservableList<Member> Member1 = FXCollections.observableArrayList();
    int index = -1;
    @FXML
    void getSelectCell(MouseEvent event) {
        index = table_Member.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        ID.setText(Col_id.getCellData(index).toString());
        Name.setText(Col_Name.getCellData(index).toString());
        Data_Birth.setText(Col_Date_Birth.getCellData(index).toString());
        Email.setText(Col_Email.getCellData(index).toString());
        Phone.setText(Col_phone.getCellData(index).toString());
        address.setText(Col_Address.getCellData(index).toString());
        gender.setText(Col_Gender.getCellData(index).toString());
        Data_Subscribing.setText(Col_DateOfSubscribing.getCellData(index).toString());
        Data_Canceling.setText(Col_DateOfCanceling.getCellData(index).toString());
    }
    public void initialize() throws ClassNotFoundException {
        Col_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Col_Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Col_Date_Birth.setCellValueFactory(new PropertyValueFactory<>("Date_Birth"));
        Col_Email.setCellValueFactory(new PropertyValueFactory<>("Email")); 
        Col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        Col_Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        Col_Gender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        Col_DateOfSubscribing.setCellValueFactory(new PropertyValueFactory<>("Date_Subscribing"));
        Col_DateOfCanceling.setCellValueFactory(new PropertyValueFactory<>("Date_Caneling"));
        loadMembers();
        table_Member.setItems(Member1);
    }
    private void loadMembers() throws ClassNotFoundException {
        Member1.clear();
        try {
            Connection con = Connector.a.connectDB();
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM Member");
            while (result.next()) {
                Member1.add(new Member(result.getInt("id"), result.getString("name"), result.getString("dataOfBirth"),
                        result.getString("email"), result.getInt("phone"), result.getString("address"),
                        result.getString("gender"), result.getString("dataOfSubscribing"),result.getString("dataOfCanceling")));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void Back() {
        try {
            Stage stage = (Stage) backimage.getScene().getWindow();
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
    public void deleteMember() throws ClassNotFoundException {

        if (ID.getText().trim().isEmpty()) {
            // display an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setHeaderText("ID cannot be empty");
            alert.showAndWait();
        } else {
            try {
                int id1 = Integer.parseInt(ID.getText());
                Member1.clear();
                Connection con = Connector.a.connectDB();
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM Member B WHERE  B.id = ?");
                pstmt.setInt(1, id1);
                pstmt.executeUpdate();

                con.close();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("error");
                alert.setHeaderText("failed to delete Member");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            loadMembers();
            table_Member.refresh();

        }

    }
    @FXML
    public void addMember() throws ClassNotFoundException {
        try {
            int Id = Integer.parseInt(ID.getText());
            String name = Name.getText();
            String Date_Birth = Data_Birth.getText();
            String email = Email.getText();
            int phone = Integer.parseInt(Phone.getText());
            String Address = address.getText();
            String Gender = gender.getText();
            String Date_Subscribing= Data_Subscribing.getText();
            String Date_Caneling = Data_Canceling.getText();

            Connection con = Connector.a.connectDB();
            PreparedStatement pstmt = con.prepareStatement("insert into Member(id, name , dataOfBirth,email,phone ,address ,gender, dataOfSubscribing ,dataOfCanceling) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, Id);
            pstmt.setString(2, name);
            pstmt.setString(3,Date_Birth);
            pstmt.setString(4, email);
            pstmt.setInt(5, phone);
            pstmt.setString(6, Address);
            pstmt.setString(7, Gender);
            pstmt.setString(8,Date_Subscribing);
            pstmt.setString(9, Date_Caneling);
            pstmt.executeUpdate();
            con.close();

            // Clear the input fields
            ID.clear();
            Name.clear();
            Data_Birth.clear();
            Email.clear();
            Phone.clear();
            address.clear();
            gender.clear();
            Data_Subscribing.clear();
            Data_Canceling.clear();

            // Refresh the table view to display the new member
            loadMembers();
            table_Member.refresh();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to add Member");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
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
    public void searchMember() throws ClassNotFoundException {
        String searchInput = ID.getText().trim();

        if (searchInput.isEmpty()) {
            // If the search input is empty, reload all books
            loadMembers();
            return;
        }

        try {
           Member1.clear();
            Connection con = Connector.a.connectDB();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Member WHERE id = ?");
            pstmt.setInt(1, Integer.parseInt(searchInput));
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
               Member1.add(new Member(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("dataOfBirth"),
                        result.getString("email"),
                        result.getInt("phone"),
                        result.getString("address"),
                        result.getString("gender"),
                        result.getString("dataOfSubscribing"),
                        result.getString("dataOfCanceling")
                ));
            }

            con.close();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void updateMember() throws ClassNotFoundException {
        Member selectedMember = (Member) table_Member.getSelectionModel().getSelectedItem();

        if (selectedMember == null) {
            // No book selected in the TableView
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No member selected");
            alert.setContentText("Please select a member from the table to update.");
            alert.showAndWait();
            return;
        }

        try {
            // Get the updated values from the input fields
        	int Id = Integer.parseInt(ID.getText());
            String name = Name.getText();
            String Date_Birth = Data_Birth.getText();
            String email = Email.getText();
            int phone = Integer.parseInt(Phone.getText());
            String Address = address.getText();
            String Gender = gender.getText();
            String Date_Subscribing= Data_Subscribing.getText();
            String Date_Caneling = Data_Canceling.getText();

            Connection con = Connector.a.connectDB();
            PreparedStatement pstmt = con.prepareStatement("UPDATE member SET id=?, name=?, dataOfBirth=?, email=?, phone=?, address =?, gender=?, dataOfSubscribing=?, dataOfCanceling=? WHERE id=?");
            pstmt.setInt(1, Id);
            pstmt.setString(2, name);
            pstmt.setString(3,Date_Birth);
            pstmt.setString(4, email);
            pstmt.setInt(5, phone);
            pstmt.setString(6, Address);
            pstmt.setString(7, Gender);
            pstmt.setString(8,Date_Subscribing);
            pstmt.setString(9, Date_Caneling);
            pstmt.setInt(10, selectedMember.getID());
            pstmt.executeUpdate();
            con.close();

            // Clear the input fields
            ID.clear();
            Name.clear();
            Data_Birth.clear();
            Email.clear();
            Phone.clear();
            address.clear();
            gender.clear();
            Data_Subscribing.clear();
            Data_Canceling.clear();


            // Refresh the TableView to reflect the changes
            loadMembers();
            table_Member.refresh();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to update Member");
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

