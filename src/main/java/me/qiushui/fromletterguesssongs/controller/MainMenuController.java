package me.qiushui.fromletterguesssongs.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.net.URI;

public class MainMenuController {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private ToolBar mainPaneToolBar;
    @FXML
    private Button mainPaneButtonBarMainMenu;
    @FXML
    private ImageView iconView;

    @FXML
    void onMainPaneButtonBarMainMenuClick(ActionEvent event) {

    }
    @FXML
    void onIconViewClicked(ActionEvent event) {
        if (Desktop.isDesktopSupported()) {
            try {
                URI uri = URI.create()
            }
        }
    }
}