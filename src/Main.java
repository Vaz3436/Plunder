import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Main extends JPanel{

    private Player player;
    private Player player2;
    private Timer timer;
    private boolean[] keys;
    public int sizeW;
    public int sizeL;
    private int lastCanonBall;
    private int lastCanonBall2;
    private ArrayList<CanonBall> canonBalls;
    private ArrayList<Medkit> medKits;
    private ArrayList<Shotgun> multiShot;
    private Capture capture;
    private int p1Points;
    private int frames;
    private boolean win1;
    private boolean win2;
    private boolean p1Shotgun;
    private boolean p2Shotgun;


    public Main(int w, int h){
        setSize(w, h);
        frames = 0;
        sizeW = 50;
        sizeL = 100;
        p1Points = 0;

        OceanBackground waterPanel = new OceanBackground();
        multiShot = new ArrayList<>();


        canonBalls = new ArrayList<>();
        medKits = new ArrayList<>();

        p1Shotgun = false;
        p2Shotgun = false;

        player = new Player(200, 400, sizeW, sizeL, false, this);
        player2 = new Player(1100, 400, sizeW, sizeL, true,this);

        capture = new Capture(600,400);

        keys = new boolean[256];

        win1 = false;
        win2 = false;

        timer = new Timer(1000/60, e->update());
        timer.start();

        for (int i = 0; i < 10; i++) {
            generateMedKit();
        }
        for (int i = 0; i < 3; i++) {
            generateMultiShot();
        }

        setupInput();
    }

    public void restart(){
        player = null;
        player2 = null;

        player = new Player(200, 400, sizeW, sizeL, false, this);
        player2 = new Player(1100, 400, sizeW, sizeL, true,this);

        win1 = false;
        win2 = false;

        //arraylist clearings
        canonBalls.clear();
        medKits.clear();
        multiShot.clear();

        timer.start();


        repaint();

    }

    public void update() {
        frames++;
        lastCanonBall += 1;
        lastCanonBall2 += 1;

        int dx = 0;
        int dy = 0;



        if (keys[KeyEvent.VK_A])
            player.rotate(-0.04);
        if (keys[KeyEvent.VK_D])
            player.rotate(0.04);
        if (keys[KeyEvent.VK_W])
            player.moveForward(3);
        if (keys[KeyEvent.VK_S])
            player.moveForward(-3);

        if (keys[KeyEvent.VK_J])
            player2.rotate(-0.04);
        if (keys[KeyEvent.VK_L])
            player2.rotate(0.04);
        if (keys[KeyEvent.VK_I])
            player2.moveForward(3);
        if (keys[KeyEvent.VK_K])
            player2.moveForward(-3);


        player.move(dx, dy);
        player2.move(dx, dy);


        if (keys[KeyEvent.VK_SHIFT]) {
            if (lastCanonBall > 50) {

                double angle = player.getAngle();
                double centerX = player.getCenterX();
                double centerY = player.getCenterY();

                // How far the cannons stick out from the ship center
                double sideOffset = player.getWidth() / 2.0 + 5;

                // Cannon spacing along the length of the ship (local Y offset)
                int[] cannonOffsets = {-30, 0, 30};
                if (p1Shotgun) {


                }

                for (int offsetY : cannonOffsets) {

                    // === LEFT SIDE ===
                    {
                        double localX = offsetY;
                        double localY = -sideOffset;

                        // Rotate local position to world coordinates
                        double worldX = centerX + (localX * Math.cos(angle) - localY * Math.sin(angle));
                        double worldY = centerY + (localX * Math.sin(angle) + localY * Math.cos(angle));

                        // Velocity is perpendicular to ship's angle (left side)
                        double vx = Math.sin(angle) * 8;
                        double vy = -Math.cos(angle) * 8;

                        canonBalls.add(new CanonBall(worldX, worldY, vx, vy));

                    }

                    // === RIGHT SIDE ===
                    {
                        double localX = offsetY;
                        double localY = sideOffset;

                        double worldX = centerX + (localX * Math.cos(angle) - localY * Math.sin(angle));
                        double worldY = centerY + (localX * Math.sin(angle) + localY * Math.cos(angle));

                        double vx = -Math.sin(angle) * 8;
                        double vy = Math.cos(angle) * 8;

                        canonBalls.add(new CanonBall(worldX, worldY, vx, vy));


                    }
                }

                lastCanonBall = 0;
            }

        }

        if (keys[KeyEvent.VK_PERIOD]) {
            if (lastCanonBall2 > 50) {

                double angle = player2.getAngle();
                double centerX = player2.getCenterX();
                double centerY = player2.getCenterY();

                // How far the cannons stick out from the ship center
                double sideOffset = player2.getWidth() / 2.0 + 5;

                // Cannon spacing along the length of the ship (local Y offset)
                int[] cannonOffsets = {-30, 0, 30};

                for (int offsetY : cannonOffsets) {

                    // === LEFT SIDE ===
                    {
                        double localX = offsetY;
                        double localY = -sideOffset;

                        // Rotate local position to world coordinates
                        double worldX = centerX + (localX * Math.cos(angle) - localY * Math.sin(angle));
                        double worldY = centerY + (localX * Math.sin(angle) + localY * Math.cos(angle));

                        // Velocity is perpendicular to ship's angle (left side)
                        double vx = Math.sin(angle) * 8;
                        double vy = -Math.cos(angle) * 8;

                        canonBalls.add(new CanonBall(worldX, worldY, vx, vy));

                    }

                    // === RIGHT SIDE ===
                    {
                        double localX = offsetY;
                        double localY = sideOffset;

                        double worldX = centerX + (localX * Math.cos(angle) - localY * Math.sin(angle));
                        double worldY = centerY + (localX * Math.sin(angle) + localY * Math.cos(angle));

                        double vx = -Math.sin(angle) * 8;
                        double vy = Math.cos(angle) * 8;

                        canonBalls.add(new CanonBall(worldX, worldY, vx, vy));


                    }
                }

                lastCanonBall2 = 0;
            }

        }




        for (int i = 0; i < canonBalls.size(); i++) {
            if (canonBalls.get(i).dist > 75) {
                canonBalls.remove(i);
                i--;
            }

        }


        for (CanonBall canonBall : canonBalls)
            canonBall.move();

        for (int i = 0; i < medKits.size(); i++) {
            if(medKits.get(i).intersects(player)){
                medKits.get(i).addHealth(player);
                medKits.remove(i);
            }

            if(medKits.get(i).intersects(player2)){
                medKits.get(i).addHealth(player2);
                medKits.remove(i);
            }
        }

        for (int i = 0; i < multiShot.size(); i++) {
            if(multiShot.get(i).intersects(player)){
                p1Shotgun = true;
                multiShot.remove(i);
            }

            if(multiShot.get(i).intersects(player2)){
                p2Shotgun = true;
                multiShot.remove(i);
            }
        }

        for (int i = 0; i < canonBalls.size(); i++) {
            if (canonBalls.get(i).intersects(player)) {
                canonBalls.remove(i);
                i--;

                if (player.health - 5 < 0)
                    player.health = 0;
                else
                    player.health -= 5;
                System.out.println(player.health);
            }

        }

        for (int i = 0; i < canonBalls.size(); i++) {
            if (canonBalls.get(i).intersects(player2)) {
                canonBalls.remove(i);
                i--;

                if (player2.health - 5 < 0)
                    player2.health = 0;
                else
                    player2.health -= 5;
                System.out.println(player2.health);
            }

        }

        for (int i = 0; i < canonBalls.size(); i++) {
            for (int j = i + 1; j < canonBalls.size(); j++) {
                if (canonBalls.get(i).intersects(canonBalls.get(j))) {
                    // Remove the higher index first to avoid shifting issues
                    canonBalls.remove(j);
                    canonBalls.remove(i);
                    i = -1; // restart the loop after modifying the list
                    break;
                }
            }
        }



        if (player.health == 0){
            win2 = true;
            timer.stop();
        }
        if (player2.health == 0){
            win1 = true;
            timer.stop();
        }



        repaint();




        //System.out.println(capture.getRect().x);
//        if(player.intersects(capture)) {
//            //System.out.println(p1Points);
//
//            if(frames%60==0)
//                p1Points++;
//
//        }




    }

    public void generateMedKit(){
        int randX = (int)(getWidth()*Math.random());
        int randY = (int)(getHeight()*Math.random());

        medKits.add(new Medkit(randX, randY));
    }
    public void generateMultiShot(){
        int randX = (int)(getWidth()*Math.random());
        int randY = (int)(getHeight()*Math.random());

        multiShot.add(new Shotgun(randX, randY));
    }

    public void setupInput(){
        addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                keys[e.getKeyCode()] = false;
            }

            public void keyPressed(KeyEvent e) {
                keys[e.getKeyCode()] = true;

                if(keys[KeyEvent.VK_R]){
                    restart();
                }

            }



        });
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.setFont(new Font("ComicSans", Font.PLAIN, 12));
        g2.drawString("lag remover", 2000, 2000);


        Color brown = new Color(139, 69, 19);
        g2.setColor(brown);
        player.draw(g2);
        player2.draw(g2);

        Color grey = new Color(179, 182, 175);
        g2.setColor(grey);
        for (CanonBall canonBall : canonBalls)
            canonBall.draw(g2);

//        g2.setStroke(new BasicStroke(20));
//        capture.draw(g2);

//        g2.setColor(Color.black);
//        g2.setFont(new Font("TimesRoman", Font.PLAIN, 100));
//        g2.drawString(""+p1Points, 100, 150);

        //draw player 1 health bar

        int health1 = player.health;
        int red1 = (int)(255 * (100 - health1) / 100.0); // more red as health drops
        int green1 = (int)(255 * (health1 / 100.0));     // less green as health drops
        g2.setColor(new Color(red1, green1, 0));

        g2.fillRect(100,75, player.health * 2,30);
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(5));
        g2.drawRect(100,75,200, 30);
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.red);
        g2.drawRect(97,72, 206, 36);

        //player 2 health bar
        int health = player2.health;
        int red = (int)(255 * (100 - health) / 100.0); // more red as health drops
        int green = (int)(255 * (health / 100.0));     // less green as health drops
        g2.setColor(new Color(red, green, 0));

        g2.fillRect(1150,75, player2.health * 2,30);
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(5));
        g2.drawRect(1150,75,200, 30);
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.blue);
        g2.drawRect(1147,72, 206, 36);

        for (int i = 0; i < medKits.size(); i++) {
            medKits.get(i).draw(g2);

        }
        for (int i = 0; i < multiShot.size(); i++) {
            multiShot.get(i).draw(g2);

        }

        if (win1){

            // Draw a weathered parchment circle
            g2.setColor(new Color(222, 184, 135)); // Light brown (parchment)
            g2.fillOval(350, 100, 700, 450);

            // Optional: Skull & Crossbones icon (very simple)
            g2.drawImage(Resources.skull, 630, 150, null);

            // Message
            g2.setColor(Color.RED);
            g2.setFont(new Font("Old English Text MT", Font.BOLD, 60)); // Pirate-looking font
            g2.drawString("Red Wins!!", 520, 350);

            // Optional: Add a pirate phrase
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Serif", Font.ITALIC, 28));
            g2.drawString("insanely tough", 470, 410);

            g2.setFont(new Font("", Font.BOLD, 28));
            g2.drawString("Press R to restart.", 530, 460);
            repaint();

        }
        if (win2){
// Draw a weathered parchment circle
            g2.setColor(new Color(222, 184, 135)); // Light brown (parchment)
            g2.fillOval(350, 100, 700, 450);

            // Optional: Skull & Crossbones icon (very simple)
            g2.drawImage(Resources.skull, 630, 150, null);

            // Message
            g2.setColor(Color.BLUE);
            g2.setFont(new Font("Old English Text MT", Font.BOLD, 60)); // Pirate-looking font
            g2.drawString("Blue Wins!!", 520, 350);

            // Optional: Add a pirate phrase
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Serif", Font.ITALIC, 28));
            g2.drawString("DO NOT REDEEM THE CARD", 470, 410);

            g2.setFont(new Font("", Font.BOLD, 28));
            g2.drawString("Press R to restart.", 530, 460);
            repaint();

        }

    }




    public static void main(String[] args) {
        // Create the window
        JFrame frame = new JFrame("Plunder");
        frame.setSize(1440, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1440, 900));

        // Ocean background (bottom layer)
        OceanBackground waterPanel = new OceanBackground();
        waterPanel.setBounds(0, 0, 1440, 900);

        // Main game panel (player, cannonballs, etc.)
        Main mainPanel = new Main(1440, 900);
        mainPanel.setBounds(0, 0, 1440, 900);
        mainPanel.setOpaque(false); // Let background show through
        mainPanel.setFocusable(true);
        mainPanel.requestFocusInWindow();

        // Add layers
        layeredPane.add(waterPanel, Integer.valueOf(0));   // Background
        layeredPane.add(mainPanel, Integer.valueOf(1));    // Foreground game

        // Add the layered pane to the frame
        frame.setContentPane(layeredPane);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

}

