package my.plaground;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTest {

    @Test public void
    ensure_calculated_distance_from_two_same_point_is_zero(){
        Position position1 = new Position(13,13);
        Position position2 = new Position(13,13);

        double distance = position1.distanceFrom(position2);

        assertEquals(0, distance);
    }


    @Test public void
    ensure_calculated_distance_from_point_is_valid(){
        Position position1 = new Position(10,0);
        Position position2 = new Position(5,0);

        double distance = position1.distanceFrom(position2);

        assertEquals(5, distance);
    }
}