module PuntaElPozo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;

    opens PuntaElPozo to javafx.fxml;
    opens PuntaElPozo.Controller to javafx.fxml;

    exports PuntaElPozo;
    exports PuntaElPozo.Controller;
    exports PuntaElPozo.Model;
    exports PuntaElPozo.Dao;
    exports PuntaElPozo.Persistence;
}