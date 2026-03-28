module com.dushtin.shopinventorymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.desktop;
    requires java.logging;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.dushtin.shopinventorymanagementsystem to javafx.fxml;
    exports com.dushtin.shopinventorymanagementsystem;
}
