module com.taxipark {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    exports com.taxipark.gui;
    opens com.taxipark.gui to javafx.fxml;
    exports com.taxipark.component;
    opens com.taxipark.component to javafx.fxml;
    exports com.taxipark.gui.menu;
    opens com.taxipark.gui.menu to javafx.fxml;
    exports com.taxipark.component.car;
    opens com.taxipark.component.car to javafx.fxml;
    exports com.taxipark.component.action;
    opens com.taxipark.component.action to javafx.fxml;
    exports com.taxipark;
    opens com.taxipark to javafx.fxml;
}