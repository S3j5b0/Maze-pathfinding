package SnakeLogic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateTest {

    Coordinate coordinate = new Coordinate(2,2);
    Coordinate otherCoordinate = new Coordinate(3,3);
    Coordinate sameCoordinate = new Coordinate(2,2);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void equals() {
        Assert.assertTrue(coordinate.equals(sameCoordinate));
        Assert.assertFalse(coordinate.equals((otherCoordinate)));
    }
}