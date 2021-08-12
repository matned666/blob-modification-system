package eu.mrndesign.matned.blob;

import eu.mrndesign.matned.brush.BrushCursor;
import javafx.scene.shape.CubicCurve;
import javafx.util.Pair;

import java.util.List;

public interface IBlob {

    Pair<List<CubicCurve>, List<IBlobPoint>> get();

    Blob modify(BrushCursor cursor);



}
