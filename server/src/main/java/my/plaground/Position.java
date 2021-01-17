package my.plaground;

import static java.lang.Math.*;

public class Position {

    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static Position at(double x, double y) {
        return new Position(x, y);
    }

    public double distanceFrom(Position otherPosition) {
        double xDiff = otherPosition.getX() - this.x;
        double yDiff = otherPosition.getY() - this.y;

        return sqrt(pow(xDiff, 2) + pow(yDiff, 2));
    }
}
