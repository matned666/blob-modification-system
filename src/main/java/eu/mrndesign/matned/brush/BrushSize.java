package eu.mrndesign.matned.brush;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BrushSize {

    B10,
    B20,
    B30,
    B40,
    B50,
    B60,
    B70,
    B80,
    B90,
    B100,
    B150,
    B200,
    B250;

    public double getSize() {
        switch (this) {
            case B10:
                return 10;
            case B20:
                return 20;
            case B40:
                return 40;
            case B50:
                return 50;
            case B60:
                return 60;
            case B70:
                return 70;
            case B80:
                return 80;
            case B90:
                return 90;
            case B100:
                return 100;
            case B150:
                return 150;
            case B200:
                return 200;
            case B250:
                return 250;
            default:
                return 30;
        }
    }

    public static List<String> getAllNames() {
        return Stream.of(B10, B20, B30, B40, B50, B60, B70, B80, B90, B100, B150, B200, B250).map(BrushSize::fullName).collect(Collectors.toList());
    }

    public static BrushSize getFromFulName(String fullName) {
        switch (fullName) {
            case "Brush size 10":
                return B10;
            case "Brush size 20":
                return B20;
            case "Brush size 40":
                return B40;
            case "Brush size 50":
                return B50;
            case "Brush size 60":
                return B60;
            case "Brush size 70":
                return B70;
            case "Brush size 80":
                return B80;
            case "Brush size 90":
                return B90;
            case "Brush size 100":
                return B100;
            case "Brush size 150":
                return B150;
            case "Brush size 200":
                return B200;
            case "Brush size 250":
                return B250;
            default:
                return B30;
        }
    }

    public static String fullName(BrushSize brushSize) {
        switch (brushSize) {
            case B10:
                return "Brush size 10";
            case B20:
                return "Brush size 20";
            case B40:
                return "Brush size 40";
            case B50:
                return "Brush size 50";
            case B60:
                return "Brush size 60";
            case B70:
                return "Brush size 70";
            case B80:
                return "Brush size 80";
            case B90:
                return "Brush size 90";
            case B100:
                return "Brush size 100";
            case B150:
                return "Brush size 150";
            case B200:
                return "Brush size 200";
            case B250:
                return "Brush size 250";
            default:
                return "Brush size 30";
        }
    }
}
