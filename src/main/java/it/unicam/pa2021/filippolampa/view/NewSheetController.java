package it.unicam.pa2021.filippolampa.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class NewSheetController {

    @FXML
    protected Button confirmButton;

    @FXML
    protected Button cancelButton;

    @FXML
    protected TextField widthSizeInput;

    @FXML
    protected TextField heightSizeInput;

    protected boolean confirmFlag = false;

    public void handleConfirm(ActionEvent actionEvent) {
        try {
            if (!widthInserted() || !heightInserted()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML Files/newdrawingareaconfirmerror.fxml"));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(loader.load()));
                stage.showAndWait();
            }
            else {
                confirmFlag = true;
                doClose(actionEvent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        doClose(actionEvent);
    }

    private boolean widthInserted(){
        return !widthSizeInput.getText().isEmpty();
    }

    private boolean heightInserted(){
        return !heightSizeInput.getText().isEmpty();
    }

    private void doClose(ActionEvent actionEvent) {
        ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
    }

    public int getWidth() {
        return Integer.parseInt(widthSizeInput.getText());
    }

    public int getHeight() {
        return Integer.parseInt(heightSizeInput.getText());
    }

    public boolean isConfirmed() {
        return confirmFlag;
    }

}
