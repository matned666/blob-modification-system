package eu.mrndesign.matned.blob;

import eu.mrndesign.matned.brush.BrushCursor;
import eu.mrndesign.matned.brush.BrushSize;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Blob implements IBlob {

    private List<IBlobPoint> points;
    private List<CubicCurve> cubicCurves;

    private double centerX;
    private double centerY;

    private Color color;



    public Blob(BrushSize brushSize, Color color, double x, double y) {
        this.color = color;
        points = new LinkedList<>();
        cubicCurves = new ArrayList<>();
        centerX = x;
        centerY = y;
        initFirstCirclePoints(brushSize.getSize());
    }

    @Override
    public Pair<List<CubicCurve>, List<IBlobPoint>>  get() {
        addCurves();
        return new Pair<>(cubicCurves, points);
    }

    private void addCurves() {
        cubicCurves.clear();
        IBlobPoint start = null;
        IBlobPoint controlA = null;
        IBlobPoint controlB = null;
        IBlobPoint end = null;
        for (int i = 0; i <= points.size(); i++) {
            if (start == null && i < points.size()) start = points.get(i);
            else {
                if (controlA == null && i < points.size()) controlA = points.get(i);
                else {
                    if (controlB == null && i < points.size()) controlB = points.get(i);
                    else {
                        if ( i < points.size()) end = points.get(i);
                        else end = points.get(0);
                        addCurve(start, controlA, controlB, end);
                        start = end;
                        controlA = null;
                        controlB = null;
                        end = null;
                    }
                }
            }
        }
    }

    @Override
    public Blob modify(BrushCursor cursor) {

        return this;
    }

    private void addCurve(IBlobPoint start, IBlobPoint controlA, IBlobPoint controlB, IBlobPoint end) {
        CubicCurve curve = new CubicCurve(start.getX(), start.getY(), controlA.getX(), controlA.getY(), controlB.getX(), controlB.getY(), end.getX(), end.getY());
        curve.setStroke(color);
        curve.setFill(Color.TRANSPARENT);
        curve.setVisible(true);
        cubicCurves.add(curve);
    }

    private void initFirstCirclePoints(double radius)
    {
        double slice = 2 * Math.PI / 12;
        for (int i = 0; i < 12; i++)
        {
            double angle = slice * i;
            double newX = centerX + radius * Math.cos(angle);
            double newY = centerY + radius * Math.sin(angle);
            points.add(new BlobPoint(newX, newY, (i == 0 || (i != 0 && i%3 == 0))));
        }
    }


}
