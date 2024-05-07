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


public class AuthorPageController {
    @FXML
    private ImageView B1 ;
    @FXML
    private TableView table_Author;
    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn NameColumn;
    @FXML
    private TableColumn FamilyNameColumn;
    @FXML
    private TableColumn TypeColumn;
    @FXML
    private TableColumn EmailColumn;
    @FXML
    private TableColumn PNumberColumn;
    @FXML
    private TableColumn DOBColumn;


    @FXML
    private ObservableList<Author> authors = FXCollections.observableArrayList();


    @FXML
    private TextField IDTF;
    @FXML
    private TextField IDTextDel;
    @FXML
    private TextField IdtEXTScerch;


    @FXML
    private TextField NameTF;
    @FXML
    private TextField FamilyNameTF;
    @FXML
    private TextField TypeTF;
    @FXML
    private TextField EmailTF;
    @FXML
    private TextField PNumberTF;
    @FXML
    private TextField DOBTF;

    @FXML
    private ImageView addImageView;
    @FXML
    private ImageView listImageView;
    private ImageView searchImageView;
    @FXML
    private ImageView deleteImageView;
    @FXML
    private ImageView back;
    @FXML
    private ImageView updateImageView;
//@FXML




    int index = -1;
    @FXML
    void getSelectCell(MouseEvent event) {
        index = table_Author.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        IDTF.setText(idColumn.getCellData(index).toString());
        NameTF.setText(NameColumn.getCellData(index).toString());
        FamilyNameTF.setText(FamilyNameColumn.getCellData(index).toString());
        TypeTF.setText(TypeColumn.getCellData(index).toString());
        EmailTF.setText(EmailColumn.getCellData(index).toString());
        PNumberTF.setText(PNumberColumn.getCellData(index).toString());
        DOBTF.setText(DOBColumn.getCellData(index).toString());
    }

    public void initialize() throws ClassNotFoundException {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("AID"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("AName"));
        FamilyNameColumn.setCellValueFactory(new PropertyValueFactory<>("familyName"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        EmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        PNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        DOBColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        loadAuthors();
        table_Author.setItems(authors);
    }


    private void loadAuthors() throws ClassNotFoundException {
        authors.clear();
        try {
            Connection con = Connector.a.connectDB();
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM author;");
            while (result.next()) {
                authors.add(new Author(result.getInt("aID"), result.getString("aName"), result.getString("FamilyName"), result.getString("Type"), result.getString("Email"), result.getInt("phoneNumber"), result.getString("date_of_birth")));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void Back() {
        try {
            Stage stage = (Stage) B1.getScene().getWindow();
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
    public void deleteAuthor() throws ClassNotFoundException {
        if (IDTextDel.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("error");
            alert.setContentText("Please enter the ID of the author you want to delete");
            alert.showAndWait();
            return;
        }else{
            try{
                int id = Integer.parseInt(IDTextDel.getText());
                authors.clear();
                Connection con = Connector.a.connectDB();
                PreparedStatement astmt = con.prepareStatement("DELETE  FROM author WHERE aID = ?");
                astmt.setInt(1, id);
                astmt.executeUpdate();
                con.close();
            }catch (SQLException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("failed!");
                alert.setTitle("error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            loadAuthors();
            table_Author.refresh();
        }
    }


    @FXML
    public void addAuthor() throws ClassNotFoundException {

            try {
                int id = Integer.parseInt(IDTF.getText());
                int phoneNumber = Integer.parseInt(PNumberTF.getText());
                String name = NameTF.getText();
                String familyName = FamilyNameTF.getText();
                String type = TypeTF.getText();
                String email = EmailTF.getText();
                String dateOfBirth = DOBTF.getText();
                Connection con = Connector.a.connectDB();
                PreparedStatement astmt = con.prepareStatement("INSERT INTO author(aID,aName, FamilyName, Type, Email, phoneNumber, Date_of_birth)VALUES (?,?,?,?,?,?,?)");///////////////continue this/////////////////////
                astmt.setInt(1, id);
                astmt.setString(2, name);
                astmt.setString(3, familyName);
                astmt.setString(4, type);
                astmt.setString(5, email);
                astmt.setInt(6, phoneNumber);
                astmt.setString(7, dateOfBirth);
                astmt.executeUpdate();
                con.close();


                IDTF.clear();
                NameTF.clear();
                FamilyNameTF.clear();
                TypeTF.clear();
                EmailTF.clear();
                PNumberTF.clear();
                DOBTF.clear();


                loadAuthors();
                table_Author.refresh();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("failed!");
                alert.setTitle("error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("failed!");
                alert.setTitle("error");
                alert.setContentText("Please enter the correct information");
                alert.showAndWait();
            }


        }

    @FXML
    public void searchAuthor() throws ClassNotFoundException{
        String searchInput = IdtEXTScerch.getText();
        if(searchInput.isEmpty()){
            loadAuthors();
            return;
        }
        try{
            authors.clear();
            Connection con = Connector.a.connectDB();
            PreparedStatement astmt = con.prepareStatement("SELECT * FROM author A WHERE aID = ?");
            astmt.setInt(1, Integer.parseInt(searchInput));
            ResultSet result = astmt.executeQuery();
            while (result.next()) {
                authors.add(new Author(result.getInt("aID"), result.getString("aName"), result.getString("FamilyName"), result.getString("Type"), result.getString("Email"), result.getInt("phoneNumber"), result.getString("Date_of_birth")));
            }
            con.close();
        }catch (SQLException | NumberFormatException e){
            e.printStackTrace();
        }
    }
    @FXML
    public void updateAuthor() throws ClassNotFoundException {
        Author selectedAuthor = (Author) table_Author.getSelectionModel().getSelectedItem();
        if (selectedAuthor == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No Author selected");
            alert.setTitle("error");
            alert.setContentText("Please select the author you want to update");
            alert.showAndWait();
            return;
        }
// Get the updated values from the input fields
        String idStr = IDTF.getText();
        String name = NameTF.getText();
        String familyName = FamilyNameTF.getText();
        String type = TypeTF.getText();
        String email = EmailTF.getText();
        String phoneNumber = PNumberTF.getText();
        String dateOfBirth = DOBTF.getText();


// Validate id and name fields
        if (idStr.trim().isEmpty() || name.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Failed to update author");
            alert.setTitle("error");
            alert.setContentText("Please enter all the information");
            alert.showAndWait();
            return;
        }
        try {
            int id = Integer.parseInt(idStr);
            int phone = Integer.parseInt(phoneNumber);
// Update the author
            Connection con = Connector.a.connectDB();
            PreparedStatement astmt = con.prepareStatement("UPDATE author \n" +
                    "SET \n" +
                    "\taID=?,\n" +
                    "\taName =?,\n" +
                    "      FamilyName=?,\n" +
                    "      Type=?,\n" +
                    "      Email=?,\n" +
                    "      phoneNumber = ?,\n" +
                    "      Date_of_birth=?\n" +
                    "WHERE\n" +
                    "    aID = ?;");
            astmt.setInt(1, id);
            astmt.setString(2, name);
            astmt.setString(3, familyName);
            astmt.setString(4, type);
            astmt.setString(5, email);
            astmt.setInt(6, phone);
            astmt.setString(7, dateOfBirth);
            astmt.setInt(8, selectedAuthor.getAID());
            astmt.executeUpdate();
            con.close();
// Clear the fields
            IDTF.clear();
            NameTF.clear();
            FamilyNameTF.clear();
            TypeTF.clear();
            EmailTF.clear();
            PNumberTF.clear();
            DOBTF.clear();
// Refresh the table view
            loadAuthors();
            table_Author.refresh();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("failed!");
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            e.printStackTrace();


        }catch (NumberFormatException e){
// Handle the case when the user enters invalid numbers
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Failed to update author");
            alert.setTitle("error");
            alert.setContentText("Please enter the correct information");
            alert.showAndWait();
        }






    }


}

