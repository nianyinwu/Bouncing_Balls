package hw4;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BouncingBall {

    public static void main(String[] args) {
        BouncingBall bouncingball = new BouncingBall();
        bouncingball.init();
    }

    void init() {
    	JFrame window = new JFrame();
    	DrawPanel drawPanel = new DrawPanel();
    	window.getContentPane().add(drawPanel);
    	window.setTitle("Bouncing Balls");
    	window.pack();
    	window.setVisible(true);
    	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    class DrawPanel extends JPanel {
    	
    	private java.util.List<Ball> Balls;
        public DrawPanel() { // constructor
        	Balls = new ArrayList<>();
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent mouse) {
                    Ball ball = new Ball(
                            // the position where the mouse clicked
                    		mouse.getPoint().x,
                    		mouse.getPoint().y,                               
                            // Random size 
                            (int) Math.floor(Math.random() * 30) + 30,
                            // Random light RGB colors 
                            new Color(Color.HSBtoRGB((float) Math.random(), (float) Math.random(), 0.5F + ((float) Math.random())/2F)).brighter(),
                            // Random velocities 
                            (int) Math.floor((Math.random() * 16) - 8),
                            (int) Math.floor((Math.random() * 16) - 8)
                    );
                    Balls.add(ball); // add the set ball
                }
            });
        }

        private Timer timer;
        @Override
        public void addNotify() {
            super.addNotify();
            if (timer != null)	timer.stop();
            timer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    for (Ball b : Balls)	b.move(getSize());
                    repaint();
                }
            });
            timer.start();
        }

        @Override
        public void removeNotify() {
            super.removeNotify();
            if (timer == null)	return;
            timer.stop();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(800, 640); // set the size of window
        }
        
        @Override
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            super.setBackground(Color.black); // set the color of window's background
            for (Ball b : Balls) 
                b.draw(graphics);

        }
    }

    class Ball {

        private int posX, posY, size,velX,velY;
        private Color color;


        public Ball(int posX, int posY, int size, Color color, int vx, int vy) {
            this.posX = posX;
            this.posY = posY;
            this.size = size;
            this.color = color;
            this.velX = vx;
            this.velY = vy;
        }

        void draw(Graphics g) {
            g.setColor(color);
            g.fillOval(posX, posY, size, size);
        }
        
        void move(Dimension bounds) {

            if (posX + size > bounds.width || posX < 0 ) 
            	velX = velX * -1; // hits the edge => continue in opposite direction

            if (posY + size > bounds.height || posY < 0 ) 
            	velY = velY * -1; // hits the edge => continue in opposite direction

            if (posX < 0)	posX = 0;
            if (posY < 0)	posY = 0;
            
            if (posY + size > bounds.height)	posY = bounds.height - size;
            // (the y position of mouse click + the size of ball) over the height of window
            if (posX + size > bounds.width ) 	posX = bounds.width - size;
            // (the x position of mouse click + the size of ball) over the width of window
            
            this.posX += velX; // move
            this.posY += velY; // move

        }
    }
}