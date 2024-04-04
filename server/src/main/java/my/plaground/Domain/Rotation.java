package my.plaground.Domain;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Rotation {

    private double x;
    private double y;
    private double z;

    public Rotation(double x, double y, double z) {
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

    public static Rotation at(double x, double y, double z) {
        return new Rotation(x, y, z);
    }
}
