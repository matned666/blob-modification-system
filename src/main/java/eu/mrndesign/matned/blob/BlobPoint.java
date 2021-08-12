package eu.mrndesign.matned.blob;

public class BlobPoint  implements IBlobPoint {

    private double x;
    private double y;
    private boolean isBuilder;

    public BlobPoint(double x, double y, boolean isBuilder) {
        this.x = x;
        this.y = y;
        this.isBuilder = isBuilder;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public boolean isBuilder() {
        return isBuilder;
    }

    @Override
    public void setX(double value) {
        this.x = value;
    }

    @Override
    public void setY(double value) {
        this.y = value;
    }


}
