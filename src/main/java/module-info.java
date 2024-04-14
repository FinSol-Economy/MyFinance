module com.myfinance {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.myfinance to javafx.fxml;
    exports com.myfinance;
    opens com.myfinance.entities to javafx.base;
    exports com.myfinance.controllersView;
    opens com.myfinance.controllersView to javafx.fxml;

}