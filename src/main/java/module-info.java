module com.example.gigiscoop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.gigiscoop to javafx.fxml;
    exports com.example.gigiscoop;
}