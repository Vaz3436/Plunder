import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Bomb extends Sprite {

    SoundPlayer sp = new SoundPlayer();
    int size = 1;
    private Timer timer;
    private Graphics2D g2;
    public boolean isExploded = false;
    boolean blink = false;
    boolean deleteMe = false;
    int frames = 0;
    int bombTimer = 0;
    private Rectangle hitBox;
    private Point originalLoc;
    public boolean boom;

    public Bomb(int x, int y) {

        super(Resources.bomb, new Point(x, y), true);

        sp.addSound("explosion", "./sound/explosion.wav");
        hitBox = new Rectangle(x, y, Resources.bomb.getWidth(), Resources.bomb.getHeight());
        originalLoc  = new Point(x, y);
    }

    public void explode(Graphics2D g2) {
//        if(!isExploded) {
            isExploded = true;
            this.g2 = g2;
            //sp.playSound("explosion");
//            g2.setColor(Color.RED);
//            g2.drawOval(x, y, size, size);

//            timer = new Timer(1000 / 30, e -> startExplode());
//            timer.start();
//        }
    }

    public boolean isExploded() {
        return isExploded;
    }

    public void setExploded(boolean exploded) {
        isExploded = exploded;
    }


    public boolean isDeleteMe() {
        return deleteMe;
    }

    public void damage(Player player){
        player.health = Math.max(player.health-50, 0);
    }

    public Rectangle getHitBox(){
        return hitBox;
    }



    @Override
    public void draw(Graphics2D g2) {
        if(isExploded) bombTimer++;
        
        frames++;
        g2.setColor(Color.RED);
        if(isExploded && size<300){
//            g2.drawOval(getX()+size/2+getWidth()/2, getY()+size/2+getHeight()/2, size, size);
            if(size<300 ) {
                size += 4;
                move(-2, -2);

            }
            g2.drawOval(getX()+getWidth()/2, getY()+getHeight()/2, size, size);
            blink = true;
        }



        else if(blink && size>=300){

            if ((frames % 60) < 30) {
                g2.setColor(Color.RED);
            } else {
                g2.setColor(new Color(0, 0, 0, 0));
            }
            g2.drawOval(getX()+getWidth()/2, getY()+getHeight()/2, 300, 300);

            System.out.println("work");

        if(bombTimer == 499)
            blink = false;
        } else if (!blink && bombTimer==500) {
            boom = true;
            System.out.println("BOOM!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            sp.playSound("explosion");
            deleteMe = true;
            return;
            
        }

        //else if(onFire){

        //}
        Point temp = getLocation();
        setLoc(originalLoc);
        super.draw(g2);
        setLoc(temp);
    }

    public int getSize() {
        return size;
    }
}
