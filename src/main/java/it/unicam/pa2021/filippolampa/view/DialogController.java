package it.unicam.pa2021.filippolampa.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DialogController {

    @FXML
    protected Button closeButton;

    public void handleClose(ActionEvent actionEvent) {
        doClose(actionEvent);
    }

    private void doClose(ActionEvent actionEvent) {
        ((Stage) ((javafx.scene.control.Button) actionEvent.getSource()).getScene().getWindow()).close();
    }

}
