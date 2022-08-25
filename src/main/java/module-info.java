module com.edenrump.quicknote {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;
    requires java.sql;

    opens com.edencoding to javafx.fxml;
    opens com.edencoding.controllers to javafx.fxml;
    opens com.edencoding.models to com.google.gson;

    exports com.edencoding;
}