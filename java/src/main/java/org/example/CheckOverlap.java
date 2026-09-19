package org.example;

public class CheckOverlap {

    public boolean checkOverlap(int radius, int xCenter, int yCenter, int x1, int y1, int x2, int y2) {

        int closestX = Math.max(x1, Math.min(xCenter, x2));
        int closestY = Math.max(y1, Math.min(yCenter, y2));

        if(((closestX - xCenter) * (closestX - xCenter) + (closestY - yCenter) * (closestY - yCenter)) <= radius * radius) return true;

        return false;
    }

    public static void main(String[] args) {
        CheckOverlap c = new CheckOverlap();
        System.out.println(c.checkOverlap(1, 0, 0, 1, -1, 3, 1));
    }
}
