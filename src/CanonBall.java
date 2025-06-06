import java.awt.*;
import java.awt.geom.Area;

public class CanonBall {
    private double x, y;
    private double vx, vy;
    int dist;
    private Rectangle hitBox;

    public CanonBall(double x, double y, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.dist = 0;
        this.hitBox = new Rectangle((int) x - 3, (int) y - 3, 6,6);
    }

    public void move() {
        x += vx;
        y += vy;
        dist++;
        // Reset hitBox position based on new x, y coordinates
        hitBox.setLocation((int) x - 3, (int) y - 3);
    }

    public boolean intersects(Player other) {
        Area area1 = new Area(this.hitBox);
        Area area2 = new Area(other.getHitBox());
        area1.intersect(area2);
        return !area1.isEmpty();
    }

    public boolean intersects(Bomb other){
        Area area1 = new Area(this.hitBox);
        Area area2 = new Area(other.getHitBox());
        area1.intersect(area2);
        return !area1.isEmpty();
    }


    public boolean intersects(CanonBall other){
        Area area1 = new Area(this.hitBox);
        Area area2 = new Area(other.getHitBox());
        area1.intersect(area2);
        return !area1.isEmpty();
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void draw(Graphics2D g2) {

            g2.setColor(Color.green);
            g2.fill(hitBox);


            g2.setColor(Color.lightGray);
            g2.fillOval((int) x - 3, (int) y - 3, 6, 6);
            g2.setColor(Color.red);
            g2.drawOval((int) x - 3, (int) y - 3, 6, 6);


    }
    public int getDist(){
        return dist;
    }
}
