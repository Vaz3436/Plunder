import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Bomb extends Sprite {
    int x;
    int y;
    Point loc;
    SoundPlayer sp = new SoundPlayer();
    int size = 1;
    private Timer timer;
    private Graphics2D g2;
    boolean isExploded = false;


    public Bomb(int x, int y) {

        super(Resources.bomb, new Point(x, y), true);
        this.x = x;
        this.y = y;
        Point loc = new Point(x, y);
        //sp.addSound("explosion", "./sound/explosion.wav");

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

//    private void startExplode(){
//        if(size<300 && isExploded){
//            size+=2;
//            x--;
//            y--;
////            g2.drawOval(x+size/2+20, y+size/2+20, size, size);
//
//        }
//    }

    @Override
    public void draw(Graphics2D g2) {
        if(isExploded){
//            g2.drawOval(x+size/2+20, y+size/2+20, size, size);
            if(size<300 ) {
                size += 4;
                x-=2;
                y-=2;
            }
            g2.setColor(Color.RED);
            g2.drawOval(x, y, size, size);
        }
        //else if(onFire){

        //}
        super.draw(g2);
    }
}
