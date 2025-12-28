package src;

import java.util.ArrayList;
import java.util.List;

public class Network {
    // Attributes
    private final List<Antenna> antennas = new ArrayList<>();

    // Methods
    public void addAntenna(double x, double y, double radius, int capacity) {
        if (antennas.isEmpty()) {
            antennas.add(new Antenna(x, y, radius, capacity));
            System.out.println("Antenna added successfully.");
            return;
        }

        for (Antenna a : antennas) {
            double dist = Math.sqrt(
                    Math.pow(a.getX() - x, 2) +
                            Math.pow(a.getY() - y, 2)
            );

            if (dist <= a.getRadius() + radius) {
                antennas.add(new Antenna(x, y, radius, capacity));
                System.out.println("Antenna added successfully.");
                return;
            }
        }
        System.out.println("Antenna coverage is separate: not added to this network.");
    }


    public Antenna findNearestAntenna(double px, double py) {
        Antenna nearest = null;
        double minDist = Double.MAX_VALUE;
        for (Antenna a : antennas) {
            if (!a.isInCoverage(px, py)) continue;
            if (!a.canAcceptNewCall()) continue;
            double dist = Math.sqrt(
                    Math.pow(px - a.getX(), 2) +
                    Math.pow(py - a.getY(), 2)
            );
            if (dist < minDist) {
                minDist = dist;
                nearest = a;
            }
        }
        return nearest;
    }
}
