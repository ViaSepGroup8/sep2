package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    private Location item;
    static final String  string = "32A2902";
    @Test
    void fromString() {
        item = new Location(string);
        item = new Location("AAA1122");
        item = new Location("X1X3490");
    }
}