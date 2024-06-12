module me.qiushui.fromletterguesssongs {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires java.sql;
    requires java.net.http;
    requires org.jsoup;
    requires annotations;
    requires org.apache.commons.lang3;
    requires java.desktop;


    opens me.qiushui.fromletterguesssongs to javafx.fxml;
    exports me.qiushui.fromletterguesssongs;
    exports me.qiushui.fromletterguesssongs.controller;
    opens me.qiushui.fromletterguesssongs.controller to javafx.fxml;
}