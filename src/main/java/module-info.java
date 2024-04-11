module com.myfinance {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.myfinance to javafx.fxml;
    exports com.myfinance;
    exports com.myfinance.controllersView;
    opens com.myfinance.controllersView to javafx.fxml;
}