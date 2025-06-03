import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Player {

    private Polygon hitBox;   // Rotated hitbox polygon
    private int speed;
    private JPanel panel;
    private double angle = 0;
    private int width;
    private int height;
    private int x;
    private int y;
    private boolean player2;
    public int health;
    private boolean shotgun;

    // Base hitbox points relative to the center (before rotation)
    private int[] baseXPoints;
    private int[] baseYPoints;

    public Player(int x, int y, int w, int h, boolean player2, JPanel panel) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.panel = panel;
        this.speed = 5;
        this.player2 = player2;
        health = 100;

        // Define base hitbox polygon points relative to center (0,0)
        // Here, a rectangle the size of the ship, centered at (0,0)
        baseXPoints = new int[] {-25, h-30, h-30, -25};
        baseYPoints = new int[] {25, 25, w+25, w+25};

        updateHitBox();
    }

    // Update the rotated hitbox polygon points
    private void updateHitBox() {
        int nPoints = baseXPoints.length;
        int[] rotatedX = new int[nPoints];
        int[] rotatedY = new int[nPoints];

        double centerX = x + width / 2.0;
        double centerY = y + height / 2.0;

        for (int i = 0; i < nPoints; i++) {
            // Translate points so center is origin
            double relX = baseXPoints[i] - width / 2.0;
            double relY = baseYPoints[i] - height / 2.0;

            // Rotate points around origin by angle
            double rotatedPointX = relX * Math.cos(angle) - relY * Math.sin(angle);
            double rotatedPointY = relX * Math.sin(angle) + relY * Math.cos(angle);

            // Translate back to absolute coordinates
            rotatedX[i] = (int) Math.round(centerX + rotatedPointX);
            rotatedY[i] = (int) Math.round(centerY + rotatedPointY);
        }

        hitBox = new Polygon(rotatedX, rotatedY, nPoints);
    }

    public void draw(Graphics2D g2) {
        updateHitBox();

        AffineTransform old = g2.getTransform();

        // Translate and rotate graphics context to player center and angle
        g2.translate(x + width / 2, y + height / 2);
        g2.rotate(angle);

        int shipLength = height;
        int shipWidth = width;

        g2.setColor(new Color(80, 40, 10));
        Polygon hull = new Polygon();
        hull.addPoint(-shipLength / 2, -shipWidth / 2);
        hull.addPoint(shipLength / 2 - 15, -shipWidth / 2);
        hull.addPoint(shipLength / 2, 0);
        hull.addPoint(shipLength / 2 - 15, shipWidth / 2);
        hull.addPoint(-shipLength / 2, shipWidth / 2);
        g2.fillPolygon(hull);

        g2.setColor(new Color(160, 110, 60));
        g2.fillPolygon(new Polygon(
                new int[]{-shipLength / 2 + 4, shipLength / 2 - 18, shipLength / 2 - 4, shipLength / 2 - 18, -shipLength / 2 + 4},
                new int[]{-shipWidth / 2 + 4, -shipWidth / 2 + 4, 0, shipWidth / 2 - 4, shipWidth / 2 - 4},
                5
        ));

        g2.setColor(new Color(120, 70, 40));
        for (int i = -shipLength / 2 + 15; i < shipLength / 2 - 15; i += 12) {
            g2.drawLine(i, -shipWidth / 2 + 6, i, shipWidth / 2 - 6);
        }

        g2.setColor(Color.GRAY);
        g2.fillOval(-8, -8, 16, 16);

        if(player2){
            g2.setColor(new Color(29, 65, 128));
        }else
            g2.setColor(new Color(120, 30, 30)); // dark red
        g2.fillRoundRect(-shipLength / 2 + 6, -shipWidth / 3, shipLength / 6, shipWidth * 2 / 3, 10, 10);

        g2.setColor(Color.BLACK);
        int numCannons = 3;
        int cannonLength = 12;
        int cannonWidth = 4;
        for (int i = 0; i < numCannons; i++) {
            int offset = (i + 1) * shipLength / (numCannons + 1) - shipLength / 2;

            g2.fillRect(offset - cannonLength / 2, -shipWidth / 2 - cannonLength + 2, cannonWidth, cannonLength);
            g2.fillRect(offset - cannonLength / 2, shipWidth / 2 - 2, cannonWidth, cannonLength);
        }

        g2.setColor(Color.BLACK);
        g2.fillRect(shipLength / 2 - 8, -3, 6, 6); // tiny post at front

        g2.setTransform(old);

//        // Draw rotated hitbox semi-transparent for debugging
//        g2.setColor(new Color(0, 102, 255, 121));
//        g2.fillPolygon(hitBox);
    }

    public void move(int dx, int dy) {
        x += dx * speed;
        y += dy * speed;

        // Keep inside panel bounds
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x + width > panel.getWidth()) x = panel.getWidth() - width;
        if (y + height > panel.getHeight()) y = panel.getHeight() - height;

        updateHitBox();
    }

    public void rotate(double delta) {
        angle += delta;
        updateHitBox();
    }

    public void moveForward(double moveSpeed) {
        x += (int) (Math.cos(angle) * moveSpeed);
        y += (int) (Math.sin(angle) * moveSpeed);

        // Keep inside panel bounds
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x + width > panel.getWidth()) x = panel.getWidth() - width;
        if (y + height > panel.getHeight()) y = panel.getHeight() - height;

        updateHitBox();
    }

    public boolean intersects(Player other) {
        Area area1 = new Area(this.hitBox);
        Area area2 = new Area(other.hitBox);
        area1.intersect(area2);
        return !area1.isEmpty();
    }


    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public double getAngle() { return angle; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Polygon getHitBox() { return hitBox; }
    public int getCenterX() {
        return x + width / 2;
    }
    public int getCenterY() {
        return y + height / 2;
}

    public boolean isShotgun() {
        return shotgun;
    }

    public void setShotgun(boolean shotgun) {
        this.shotgun = shotgun;
    }
}
