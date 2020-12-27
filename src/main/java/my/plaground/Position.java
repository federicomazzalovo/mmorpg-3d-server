package my.plaground;

public class Position {

    private double x;
    private double y;

    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static Position at(double x, double y){
        return new Position(x, y);
    }

    public Position distanceFrom(Position position){
        return null;
    }
}
