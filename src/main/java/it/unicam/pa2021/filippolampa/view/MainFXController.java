package it.unicam.pa2021.filippolampa.view;

import it.unicam.pa2021.filippolampa.controller.DefaultController;
import it.unicam.pa2021.filippolampa.model.logo.*;
import it.unicam.pa2021.filippolampa.model.parser.IllegalPathException;
import it.unicam.pa2021.filippolampa.model.parser.UnknownCommandException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainFXController implements CursorUpdateListener, DrawingAreaUpdateListener {

    private DefaultController controller;

    @FXML
    protected AnchorPane pane;

    @FXML
    protected AnchorPane canvasPane;

    @FXML
    protected Button selectFileButton;

    @FXML
    protected Button stepByStepButton;

    @FXML
    protected Canvas drawingAreaCanvas;

    @FXML
    protected ImageView cursor;

    private String currentProgramPath;

    private boolean sheetCreated = false;

    private boolean isFileSelected = false;

    private boolean programStarted = false;

    protected boolean SheetIsNotCreated(){
        return !sheetCreated;
    }

    protected boolean FileIsNotSelected(){ return !isFileSelected;}

    @FXML
    public void handleNewSheet(){
        try {
            showDialogWindow("/FXML Files/recommendedareasize.fxml");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML Files/newsheet.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(loader.load()));
            NewSheetController sheetController = loader.getController();
            stage.showAndWait();
            if (sheetController.isConfirmed()) {
                clearCanvas();
                drawingAreaCanvas.setWidth(sheetController.getWidth());
                drawingAreaCanvas.setHeight(sheetController.getHeight());
                sheetCreated = true;
            } else {
                System.out.println("Canceled");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showDialogWindow(String fxmlFilePath){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(loader.load()));
            stage.showAndWait();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void initializeCanvas(){
        initializeController();
        initializeCursor();
    }

    private void initializeCursor(){
        cursor.setX(drawingAreaCanvas.getWidth()/2);
        cursor.setY(drawingAreaCanvas.getHeight()/2);
        cursor.setOpacity(1);
        cursor.setRotate(0);
        firePenDimensionChanged(1);
        fireCursorLineColorChanged(new Color(0,0,0));
    }

    private void initializeController(){
        controller = new DefaultController();
        controller.setCursorListener(this);
        controller.setAreaListener(this);
        controller.newLogoIde((int)drawingAreaCanvas.getWidth(),(int)drawingAreaCanvas.getHeight());
    }

    private void clearCanvas(){
        drawingAreaCanvas.getGraphicsContext2D().clearRect(0,0,drawingAreaCanvas.getWidth(),drawingAreaCanvas.getHeight());
    }

    @FXML
    public void handleClose() {
        Platform.exit();
    }

    @FXML
    public void handleAbout() {
        showDialogWindow("/FXML Files/about.fxml");
    }

    @FXML
    public void handleSelectFile() {
            if(SheetIsNotCreated()) {
                showDialogWindow("/FXML Files/filechooserdisabled.fxml");
            }
            else {
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Choose a logo program");
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    clearCanvas();
                    initializeCanvas();
                    isFileSelected = true;
                    currentProgramPath = file.getPath();
                } else {
                    System.out.println("Canceled");
                }
            }
    }

    public void handleNextStep() {
        if (SheetIsNotCreated() || FileIsNotSelected() || !isProgramStarted()) {
            showDialogWindow("/FXML Files/nextstepdisabled.fxml");
        } else {
            try {
                controller.nextStep();
            } catch (UnknownCommandException e) {
                showDialogWindow("/FXML Files/unknowncommand.fxml");
            }
        }
    }

    private boolean isProgramStarted() {
        return programStarted;
    }

    public void handleStepByStep() {
        if (SheetIsNotCreated() || FileIsNotSelected()) {
            showDialogWindow("/FXML Files/stepbystepdisabled.fxml");
        } else {
            controller.switchStepByStep();
            if(controller.isStepByStep()) {
                stepByStepButton.setStyle("-fx-background-color: #8ecfd1");
                showDialogWindow("/FXML Files/stepbysteprepeat.fxml");
            }
            else stepByStepButton.setStyle(null);
        }
    }

    public void fireCursorMoved(Location newLocation){
        if(!(newLocation.getYaxis()>canvasPane.getHeight())) {
            cursor.setX(newLocation.getXaxis());
            cursor.setY(newLocation.getYaxis());
        }
    }

    public void fireCursorRotated(int degrees){
        cursor.setRotate(-degrees);
    }

    @Override
    public void fireSegmentDrawn(Segment segment) {
        double oldX = segment.getStartingPoint().getXaxis();
        double oldY = segment.getStartingPoint().getYaxis();
        double newX = segment.getDestinationPoint().getXaxis();
        double newY = segment.getDestinationPoint().getYaxis();
        this.drawingAreaCanvas.getGraphicsContext2D().strokeLine(oldX, oldY, newX, newY);
    }

    @Override
    public void fireCursorLineColorChanged(Color newCurrentLineColor) {
        javafx.scene.paint.Color fxColor = javafx.scene.paint.Color.rgb(newCurrentLineColor.getR(),newCurrentLineColor.getG(),newCurrentLineColor.getB());
        this.drawingAreaCanvas.getGraphicsContext2D().setStroke(fxColor);
    }

    @Override
    public void firePenDimensionChanged(int newPenDimension) {
        drawingAreaCanvas.getGraphicsContext2D().setLineWidth(newPenDimension);
    }

    @Override
    public <S extends Segment> void fireClosedArea(ArrayList<S> closedArea, Color areaColor) {
        Path closedAreaPath = initializeClosedAreaPath(closedArea);
        for(Segment segment : closedArea){
            addSegmentToClosedPath(segment,closedAreaPath);
        }
        javafx.scene.paint.Color fxColor = javafx.scene.paint.Color.rgb(areaColor.getR(),areaColor.getG(),areaColor.getB());
        fillClosedArea(closedAreaPath,fxColor);
    }

    private void fillClosedArea(Path closedAreaPath, javafx.scene.paint.Color fxColor){
        closedAreaPath.setFillRule(FillRule.EVEN_ODD);
        closedAreaPath.setFill(fxColor);
        Bounds pathBounds = closedAreaPath.getLayoutBounds();
        WritableImage snapshot = new WritableImage((int) pathBounds.getWidth(), (int) pathBounds.getHeight());
        SnapshotParameters snapshotParams = new SnapshotParameters();
        snapshot = closedAreaPath.snapshot(snapshotParams, snapshot);
        drawingAreaCanvas.getGraphicsContext2D().drawImage(snapshot, pathBounds.getMinX(), pathBounds.getMinY());
    }

    private void addSegmentToClosedPath(Segment segment, Path closedAreaPath){
        LineTo line = new LineTo();
        line.setX(segment.getDestinationPoint().getXaxis());
        line.setY(segment.getDestinationPoint().getYaxis());
        closedAreaPath.getElements().add(line);
    }

    private <S extends Segment> Path initializeClosedAreaPath(ArrayList<S>closedArea){
        MoveTo goToStartingPoint = new MoveTo();
        goToStartingPoint.setX(closedArea.get(0).getStartingPoint().getXaxis());
        goToStartingPoint.setY(closedArea.get(0).getStartingPoint().getYaxis());
        Path closedAreaPath = new Path();
        closedAreaPath.getElements().add(goToStartingPoint);
        return closedAreaPath;
    }

    @Override
    public void fireTwoClosedAreasAlert() {
        showDialogWindow("/FXML Files/twoclosedareasalert.fxml");
        drawingAreaCanvas.getGraphicsContext2D().setFill(Paint.valueOf("White"));
        drawingAreaCanvas.getGraphicsContext2D().fillRect(0,0,drawingAreaCanvas.getWidth(),drawingAreaCanvas.getHeight());
    }

    public void handleDeleteMenuItem() {
        clearCanvas();
    }

    @Override
    public void fireEmptyArea() {
        drawingAreaCanvas.getGraphicsContext2D().clearRect(0, 0, drawingAreaCanvas.getWidth(), drawingAreaCanvas.getHeight());
    }

    @Override
    public void fireBackgroundColorChanged(Color newBackgroundColor) {
        javafx.scene.paint.Color fxColor = javafx.scene.paint.Color.rgb(newBackgroundColor.getR(),newBackgroundColor.getG(),newBackgroundColor.getB());
        drawingAreaCanvas.getGraphicsContext2D().setFill(fxColor);
        drawingAreaCanvas.getGraphicsContext2D().setFillRule(FillRule.EVEN_ODD);
        drawingAreaCanvas.getGraphicsContext2D().fillRect(1,1,drawingAreaCanvas.getWidth(),drawingAreaCanvas.getHeight());
    }

    public void handleStartButton() {
            try {
                if (!isFileSelected || SheetIsNotCreated()) {
                    showDialogWindow("/FXML Files/startdisabled.fxml");
                } else {
                    programStarted = true;
                    controller.submitProgram(currentProgramPath);
                }
            } catch (IllegalPathException | UnknownCommandException e) {
                if (e instanceof IllegalPathException)
                    showDialogWindow("/FXML Files/unreachablepath.fxml");
                else showDialogWindow("/FXML Files/unknowncommand.fxml");
            }
        }
}
