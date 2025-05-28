import java.awt.*;

public class Capture {
    private double x, y;
    private double vx, vy;
    int dist;
    private Rectangle hitBox;


    public Capture(double x, double y) {
        this.x = x;
        this.y = y;
        this.dist = 0;
        this.hitBox = new Rectangle(520,200,400,400);

    }

    public void draw(Graphics2D g2) {

        g2.setColor(new Color(255, 0, 0, 121));
        g2.drawOval(520,200,400,400);
        Rectangle temp = this.hitBox;
        g2.fill(temp);


    }
    public Rectangle getRect(){
        return hitBox;
    }

    public int getX() {
        return (int)x;
    }

    public int getWidth() {
        return 400;
    }

    public int getHeight(){
        return 400;
    }

    public int getY() {
        return (int)y;
    }
}
