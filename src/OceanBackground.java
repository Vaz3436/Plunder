import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OceanBackground extends JPanel implements ActionListener {

    private Timer timer;
    private float phase = 0f;

    public OceanBackground() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(new Color(25, 40, 70));
        timer = new Timer(60, this); // lower frame rate for more pixel feel
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPixelatedSlantedWaves((Graphics2D) g);
    }

    private void drawPixelatedSlantedWaves(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();

        // Settings for pixelated arcs
        int arcWidth = 16;         // Width of each arc
        int arcHeight = 6;         // Height of each arc
        int spacingX = 32;         // Horizontal gap between waves
        int spacingY = 24;         // Vertical gap between wave rows
        int pixelSnap = 2;         // Snap movement to this pixel grid

        // Disable antialiasing for pixel style
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        // Color for pixel waves
        g2.setColor(new Color(140, 200, 255, 180));
        g2.setStroke(new BasicStroke(1));

        for (int y = 0; y < height + spacingY; y += spacingY) {
            for (int x = -spacingX; x < width + spacingX; x += spacingX) {
                // Slanted movement: move X + Y together
                int xOffset = (int)((phase * 10 + y * 0.3) % spacingX);
                int yOffset = (int)((phase * 2 + x * 0.02) % spacingY);

                int drawX = x + xOffset;
                int drawY = y + yOffset;

                // Snap to pixel grid
                drawX = (drawX / pixelSnap) * pixelSnap;
                drawY = (drawY / pixelSnap) * pixelSnap;

                // Draw upward arc like a pixelated ∩
                g2.drawArc(drawX, drawY, arcWidth, arcHeight, 0, 180);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        phase += 0.3f;
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pixelated Slanted Ocean Background");
        OceanBackground oceanPanel = new OceanBackground();
        frame.add(oceanPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
