module com.edenrump.quicknote {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;
    requires java.sql;

    opens com.edencoding.models to javafx.fxml;
    opens com.edencoding to javafx.fxml;
    opens com.edencoding.controllers to javafx.fxml;

    exports com.edencoding.models;
    exports com.edencoding;
}