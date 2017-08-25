
/**
 * This method creates the background of the game and 
 * controls the direction changes of the snake.
 * 
 * @author Gowtham Vuppala
 */

import objectdraw.*;
import java.awt.*;
import java.awt.event.*;

public class SnakeController extends WindowController implements KeyListener
{
    // The values of each direction
    private static final int NO_DIRECTION = 0;
    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    private static final int RIGHT = 4;
    
    // Camvas dimensions
    private static final int CANVAS_WIDTH = 400;
    private static final int CANVAS_HEIGHT = 675;
    
    //Rules dimensions
    private static final int RULES_WIDTH = 400;
    private static final int RULES_HEIGHT = 30;
    private static final int RULES_Y = 400;
    private static final int TEXT_X = 50;
    private static final int TEXT_Y = 410;
    
    // The snake logo dimensions
    private static final int SNAKE_X = 0;
    private static final int SNAKE_Y = 430;
    private static final int SNAKE_WIDTH = 400;
    private static final int SNAKE_HEIGHT = 200;
    
    // An instance of the snake
    private Snake snake;
    
    // Holds the snake game logo
    private VisibleImage snakePic;
    
    
    public void begin() 
    {
        setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        
        // Get the image of the background
        Image pic  = getImage("wallpaper.jpg");       
        
        // Create a snake object
        snake = new Snake(pic, canvas);
        
        // Display the snake logo
        Image snakeLogo = getImage("snakelogo.jpg");
        snakePic = new VisibleImage(snakeLogo, SNAKE_X, SNAKE_Y + 1, canvas);
        snakePic.setWidth(SNAKE_WIDTH);
        snakePic.setHeight(SNAKE_HEIGHT);
        
        //Displays the instructions
        new FilledRect(RULES_Y - RULES_Y, RULES_Y, RULES_WIDTH, RULES_HEIGHT, canvas);
        new Text("USE ARROW KEYS TO CONTROL THE GAME!", TEXT_X, TEXT_Y, canvas).setColor(Color.white);
        
        // Needed for activating keyListener
        requestFocus();
        addKeyListener(this);
        canvas.addKeyListener(this);
    }
    
    /*
     * This method helps the snake to move in a direction
     */
    public void keyPressed(KeyEvent e)
    {
        // Move the snake in the direction according to the input
        if (e.getKeyCode() == KeyEvent.VK_UP && snake.getDirection() != DOWN)
        {
            snake.setDirection(UP); 
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && snake.getDirection() != UP)
        {
            snake.setDirection(DOWN);
            
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && snake.getDirection() != LEFT)
        {
            snake.setDirection(RIGHT);
            
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && snake.getDirection() != RIGHT)
        {
            snake.setDirection(LEFT);
            
        }
    }
    
    public void keyTyped(KeyEvent e)
    {}

    public void keyReleased(KeyEvent e)
    {}
}
