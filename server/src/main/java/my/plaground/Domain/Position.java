package my.plaground.Domain;

import static java.lang.Math.*;

public class Position {

    private double x;
    private double y;
    private double z;

    public Position(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public double getZ() {
        return z;
    }

    public static Position at(double x, double y, double z) {
        return new Position(x, y, z);
    }

    public double distanceFrom(Position otherPosition) {
        double xDiff = otherPosition.getX() - this.x;
        double yDiff = otherPosition.getY() - this.y;

        return sqrt(pow(xDiff, 2) + pow(yDiff, 2));
    }
}
