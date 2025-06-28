module org.example.atmfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens org.example.atmfx to javafx.fxml;
    exports org.example.atmfx;
}