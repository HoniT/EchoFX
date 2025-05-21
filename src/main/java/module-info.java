module ge.mziuri.echofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens ge.mziuri.echofx to javafx.fxml;
    exports ge.mziuri.echofx;
    exports ge.mziuri.echofx.controllers;
    opens ge.mziuri.echofx.controllers to javafx.fxml;
    exports ge.mziuri.echofx.services;
    opens ge.mziuri.echofx.services to javafx.fxml;
}