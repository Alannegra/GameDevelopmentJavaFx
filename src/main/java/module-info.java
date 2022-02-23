module com.alan.gamedevelopmentjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.alan.gamedevelopmentjavafx to javafx.fxml;
    exports com.alan.gamedevelopmentjavafx;
}