module com.tutadam.healthsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.lutadam.healthsystem to javafx.fxml;
    exports com.lutadam.healthsystem;
}