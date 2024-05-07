module com.example.librarymanagmentsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires mysql.connector.java;

    opens com.example.librarymanagmentsystem to javafx.fxml;
    exports com.example.librarymanagmentsystem;
}