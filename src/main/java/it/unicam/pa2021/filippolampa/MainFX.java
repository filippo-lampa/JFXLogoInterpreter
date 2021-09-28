package it.unicam.pa2021.filippolampa;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML Files/logointerpreter.fxml"));
        Parent root = loader.load();
        stage.setTitle("Logo Interpreter");
        stage.setScene(new Scene(root));
        stage.getIcons().add(new Image("Icons/logo_icon.png"));
        stage.show();
    }
}

