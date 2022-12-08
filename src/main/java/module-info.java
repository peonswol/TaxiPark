module com.taxipark {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    exports com.taxipark.gui;
    opens com.taxipark.gui to javafx.fxml;
    exports com.taxipark.gui.component;
    opens com.taxipark.gui.component to javafx.fxml;
}