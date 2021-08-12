package eu.mrndesign.matned.brush;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BrushCursor extends Circle {

    public BrushCursor(BrushSize brushSize, double startX, double startY, Color color) {
        super(brushSize.getSize()/2, color);
        relocate(startX, startY);
        setFill(Color.TRANSPARENT);
    }


}
