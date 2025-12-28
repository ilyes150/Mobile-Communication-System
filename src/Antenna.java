package src;

public class Antenna {
    // Attributes
    private final double x, y;
    private final double radius;
    private int activeCalls;
    private final int capacity;

    // Constructor
    public Antenna(double x, double y, double radius, int capacity){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.capacity = capacity;
        this.activeCalls = 0;
    } 

    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getRadius() { return radius; }
    public int getCapacity() { return capacity; }

    // Methods
    public boolean canAcceptNewCall() {
        return activeCalls < capacity;
    }

    public void decrementActiveCalls() {
        if (activeCalls != 0)
            activeCalls--;
    }

    public void incrementActiveCalls() {
        if (canAcceptNewCall())
            activeCalls++;
    }

    public boolean isInCoverage(double px, double py) {
        double dist = Math.sqrt(
                Math.pow(px - x, 2) +
                Math.pow(py - y, 2)
        );
        return dist <= radius;
    }
}
