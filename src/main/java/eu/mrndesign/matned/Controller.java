package eu.mrndesign.matned;

import eu.mrndesign.matned.blob.Blob;
import eu.mrndesign.matned.blob.IBlob;
import eu.mrndesign.matned.blob.IBlobPoint;
import eu.mrndesign.matned.brush.BrushCursor;
import eu.mrndesign.matned.brush.BrushSize;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.util.Pair;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ComboBox<String> brushSizeComboBox;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private AnchorPane canvasAnchor;
    @FXML
    private VBox consoleVbox;
    @FXML
    private Label mouseXLabel;
    @FXML
    private Label mouseYLabel;

    private BrushCursor brushCursor;
    private boolean paintingPossibility;
    private CubicCurve startPoint;
    private CubicCurve controlA;
    private CubicCurve controlB;
    private CubicCurve end;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initComboBox();
        initCanvas();
        paintingPossibility = true;
        colorPicker.setValue(Color.BLACK);

    }

    @FXML
    public void onMouseClickedOnCanvas(MouseEvent event) {
        draw(event);
    }

    @FXML
    public void onClearConsoleClick() {

    }

    @FXML
    public void onBrushSizeValueSet() {
        brushCursor.setRadius(BrushSize.getFromFulName(brushSizeComboBox.getValue()).getSize());
    }

    @FXML
    public void onBrushColorSet() {
        if (brushCursor != null) {
            brushCursor.setStroke(colorPicker.getValue());
        }
    }

    @FXML
    public void onRemoveLastLogClick(ActionEvent actionEvent) {

    }

    @FXML
    public void onModifyButtonClick(ActionEvent actionEvent) {

    }

    @FXML
    public void onPaintButtonClick(ActionEvent actionEvent) {

    }

    @FXML
    public void ocClearButtonClick() {
        canvasAnchor.getChildren().clear();
    }

    private void draw(MouseEvent event) {
        if (isInCanvas(event) && paintingPossibility) {
            IBlob blob = new Blob(BrushSize.getFromFulName(brushSizeComboBox.getValue()), colorPicker.getValue(), event.getX(), event.getY());
            Pair<List<CubicCurve>, List<IBlobPoint>> blobData = blob.get();
            blobData.getKey().forEach(cubicCurve -> canvasAnchor.getChildren().add(cubicCurve));
            blobData.getValue().forEach(point -> {
                Circle newPoint = new Circle(point.getX(), point.getY(), point.isBuilder()? 3: 2, colorPicker.getValue());
                newPoint.setCursor(Cursor.HAND);
                newPoint.setOnMousePressed(e -> {
                    findCurvePoints(blobData, point);
                });
                newPoint.setOnMouseEntered(mouseEvent -> {
                    brushCursor.setVisible(false);
                    paintingPossibility = false;
                    findCurvePoints(blobData, point);
                });
                newPoint.setOnMouseExited(mouseEvenv -> {
                    brushCursor.setVisible(true);
                    paintingPossibility = true;
                     });
                newPoint.setOnMouseReleased(e -> {
                    startPoint = null;
                    controlA = null;
                    controlB = null;
                    end = null;
                });
                newPoint.setOnMouseDragged(mouseEvent -> {

                    paintingPossibility = false;
                    newPoint.setCenterX(mouseEvent.getX());
                    newPoint.setCenterY(mouseEvent.getY());

                    if (startPoint != null && controlA == null && controlB == null ) {
                        startPoint.setStartX(mouseEvent.getX());
                        startPoint.setStartY(mouseEvent.getY());
                    }
                    if (controlA != null && startPoint == null && controlB == null && end == null) {
                        controlA.setControlX1(mouseEvent.getX());
                        controlA.setControlY1(mouseEvent.getY());
                    }
                    if (controlB != null && startPoint == null && controlA == null && end == null) {
                        controlB.setControlX2(mouseEvent.getX());
                        controlB.setControlY2(mouseEvent.getY());
                    }
                    if (end != null && controlB == null && controlA == null) {
                        end.setEndX(mouseEvent.getX());
                        end.setEndY(mouseEvent.getY());
                    }


                });

                canvasAnchor.getChildren().add(newPoint);
            });
        }
    }

    private void findCurvePoints(Pair<List<CubicCurve>, List<IBlobPoint>> blobData, IBlobPoint point) {
        if (startPoint == null) {
            startPoint = blobData.getKey().stream()
                    .filter(curve -> isInPoint(curve.getStartX(), curve.getStartY(), point))
                    .findFirst()
                    .orElse(null);
            if (startPoint != null){
                controlA = null;
                controlB = null;
            }
        }
        if (controlA == null) {
            controlA = blobData.getKey().stream()
                    .filter(curve -> isInPoint(curve.getControlX1(), curve.getControlY1(), point))
                    .findFirst()
                    .orElse(null);
            if (controlA != null){
                startPoint = null;
                end = null;
                controlB = null;
            }
        }
        if (controlB == null) {
            controlB = blobData.getKey().stream()
                    .filter(curve -> isInPoint(curve.getControlX2(), curve.getControlY2(), point))
                    .findFirst()
                    .orElse(null);
            if (controlB != null){
                startPoint = null;
                controlA = null;
                end = null;
            }
        }
        if (end == null)  {
            end = blobData.getKey().stream()
                    .filter(curve -> isInPoint(curve.getEndX(), curve.getEndY(), point))
                    .findFirst()
                    .orElse(null);
            if (end != null){
                controlA = null;
                controlB = null;
            }
        }
    }

    private boolean isInPoint(double endX, double endY, IBlobPoint point) {
            double dx = Math.abs(endX-point.getX());
            if (    dx >  3 ) return false;
            double dy = Math.abs(endY-point.getY());
            if (    dy >  3 ) return false;
            if ( dx+dy <= 3 ) return true;
            return ( dx*dx + dy*dy <= 3*3 );
    }

    private boolean isInCanvas(MouseEvent event) {
        return event.getX() < canvasAnchor.getWidth() && event.getY() < canvasAnchor.getHeight() && event.getX() >= 0 && event.getY() > 0;
    }

    private void initComboBox() {
        brushSizeComboBox.getItems().addAll(BrushSize.getAllNames());
        brushSizeComboBox.getSelectionModel().select(2);
    }

    private void initCanvas() {
        canvasAnchor.setOnMouseMoved(mouseEvent -> {
            if(isInCanvas(mouseEvent)) {
                refreshCanvas(mouseEvent);
            }
        });
        canvasAnchor.setOnMouseDragged(mouseEvent -> {

            draw(mouseEvent);
            if(isInCanvas(mouseEvent)) {
                refreshCanvas(mouseEvent);
                blobModification(mouseEvent);
            }
        });

    }

    private void blobModification(MouseEvent mouseEvent) {

    }

    private void refreshCanvas(MouseEvent mouseEvent) {
        refreshMousePositionLabels(mouseEvent);
        refreshBrushCursor(mouseEvent);
    }

    private void refreshMousePositionLabels(MouseEvent mouseEvent) {
        mouseXLabel.setText(String.valueOf(mouseEvent.getX()));
        mouseYLabel.setText(String.valueOf(mouseEvent.getY()));
    }

    private void refreshBrushCursor(MouseEvent mouseEvent) {
        if (brushCursor != null) {
            brushCursor.setRadius(BrushSize.getFromFulName(brushSizeComboBox.getValue()).getSize());
            brushCursor.setLayoutX(mouseEvent.getX());
            brushCursor.setLayoutY(mouseEvent.getY());
        } else {
            String selectedItem = brushSizeComboBox.getSelectionModel().getSelectedItem();
            brushCursor =
                    new BrushCursor(BrushSize.getFromFulName(selectedItem),
                            mouseEvent.getX(),
                            mouseEvent.getY(),
                            colorPicker.getValue());
            brushCursor.setStroke(Color.BLACK);
            brushCursor.setVisible(true);
            canvasAnchor.getChildren().add(brushCursor);
        }
    }
}
