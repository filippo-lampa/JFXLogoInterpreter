package it.unicam.pa2021.filippolampa.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FileChooserController {

    @FXML
    protected TextField pathTextField;

    @FXML
    protected Button confirmButton;

    @FXML
    protected Button cancelButton;

    protected boolean confirmFlag = false;

    public void handleConfirm(ActionEvent actionEvent) {
            confirmFlag = true;
            doClose(actionEvent);
    }

    public void handleCancel(ActionEvent actionEvent) {
        doClose(actionEvent);
    }

    private void doClose(ActionEvent actionEvent) {
        ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
    }

}
