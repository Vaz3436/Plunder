import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;

public class Splash {
    private int x, y;
    private int timer;

    // Constructor - x and y are where the splash appears
    public Splash(int x, int y) {
        this.x = x;
        this.y = y;
        this.timer = 60; // Show for 60 frames (~1 second if 60 FPS)
    }

    // Call this once per frame to count down
    public void update() {
        timer--;
    }

    // Returns true when the splash should be removed
    public boolean isDone() {
        return timer <= 0;
    }

    // Draw the splash
    public void draw(Graphics g) {
        // Example: a blue splash with fading color based on timer
        int alpha = (int) ((timer / 60.0) * 255);
        alpha = Math.max(0, Math.min(alpha, 255));

        g.setColor(new Color(0, 100, 255, alpha));
        g.fillOval(x - 10, y - 10, 20, 20);
    }
}