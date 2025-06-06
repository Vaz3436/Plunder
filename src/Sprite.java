import java.awt.*;
import java.awt.image.BufferedImage;

/* This class represents a Sprite in a game.
   Sprites have an image and location, and can
   detect collision/intersection with another Sprite.
   This class is set up to have rectangular collision
   detection - rectangles intersecting rectangles.
 */

public class Sprite {

    private BufferedImage image;
    private Point location;
    private boolean isObstacle;

    public Sprite(BufferedImage image, Point location, boolean isObstacle) {
        this.image = image;
        this.location = location;
        this.isObstacle = isObstacle;
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, location.x, location.y, null);
    }

    public boolean intersects(Player other){
        Rectangle hitBox = new Rectangle(location.x, location.y, image.getWidth(), image.getHeight());
        Rectangle otherHitBox = new Rectangle(other.getX(), other.getY(), other.getWidth(), other.getHeight());
        return hitBox.intersects(otherHitBox);
    }

    public void move(int dx, int dy){
        location.translate(dx, dy);  //moves this sprite ...
    }

    public void move(int dx){
        location.translate(dx,0);
    }

    public void move(){}

    public int getX() {return location.x;}
    public int getY() {return location.y;}
    public int getWidth() {return image.getWidth();}
    public int getHeight() {return image.getHeight();}
    public void setX(int x){
        location.x = x;
    }
    public void setY(int y){
        location.y = y;
    }

    public void setLoc(Point location) {
        this.location = location;
    }

    public boolean isObstacle(){return isObstacle;}

    public void setImage(BufferedImage image) { this.image = image; }

    public Point getLocation() {
        return location;
    }
}

