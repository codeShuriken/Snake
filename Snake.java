
/**
 * This program is respomsible for the creation of the snake, its momemts 
 * its growth and also responsible to stop the game.
 * 
 * @author Gowtham Vuppala
 */

import objectdraw.*;
import java.awt.*;
import java.util.*;

public class Snake extends ActiveObject 
{
    // The snake dimensions
    private static final int SNAKE_HEIGHT = 20;
    private static final int SNAKE_WIDTH = 20;
    private static final int BOX_SIZE = 20;

    // The int value for the directions
    private static final int NO_DIRECTION = 0;
    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    private static final int RIGHT = 4;

    // The delay time for the run method
    private static final double DELAY_TIME = 50;

    // The font size
    private static final int FONT_SIZE = 25;

    // The canvas starting x and y values
    private static final int CANVAS_X = 0;
    private static final int CANVAS_Y = 0;

    // All the dimensions and sizes for the game over method
    private static final int RECT_WIDTH = 400;
    private static final int RECT_HEIGHT = 425;
    private static final int TEXT_X = 50;
    private static final int TEXT_Y = 100;
    private static final int TEXT_DIFF = 60;

    // Snake Points
    private static final int SNAKE_X = 10;
    private static final int SNAKE_Y = 2;

    //A random generator
    private Random r = new Random();

    // Tokeep track of the direction
    private int direction = 0;

    // To make sure we are in the game 
    private boolean inGame = true;

    // A variable to keep track of the score
    private int score = 0;

    // A linkedlist to keep track of all the snake locations and a reference to the 
    // snake's head
    private LinkedList<Location> snake;
    private Location head;

    // The location of the fruit and the headPoint of the snake 
    private Location fruit;
    private Location headPoint;

    // Needed for the image on the background
    private VisibleImage background; 
    private Image pic;

    // An instance of canvas
    public DrawingCanvas canvas;

    public Snake(Image pic , DrawingCanvas canvas)
    {
        // TO remember the local variables as instance variables
        this.canvas = canvas;
        this.pic = pic;

        // Create a snake and add its starting position
        snake = new LinkedList<Location>();
        snake.add(new Location(SNAKE_X, SNAKE_Y));
        snake.add(new Location(SNAKE_X, SNAKE_Y + 1));
        snake.add(new Location(SNAKE_X, SNAKE_Y + 2));

        // This statement generates the fruit
        fruitGenerator();

        // Time to start the game
        start();
    }

    /*
     * This method is one of the method which keeps on calling the 
     * drawSnake(), drawImage() and the drawFruit() function
     */
    public void draw()
    {
        // Clear all the extra objects created
        new FilledRect(CANVAS_X, CANVAS_Y, SNAKE_WIDTH * BOX_SIZE,
            SNAKE_HEIGHT * BOX_SIZE, canvas).setColor(Color.white);

        drawImage();
        drawSnake();
        drawFruit();

    }

    /*
     * This method draws the background of the game. 
     */
    public void drawImage()
    {
        background = new VisibleImage(pic, CANVAS_X, CANVAS_Y, canvas);
        background.setWidth(SNAKE_WIDTH * BOX_SIZE);
        background.setHeight(SNAKE_HEIGHT * BOX_SIZE);
    }

    /*
     * This method draws the snake each time it is called
     */
    public void drawSnake()
    {
        for (Location body: snake)
        {
            new FilledOval(body.getX() * SNAKE_WIDTH, body.getY()* SNAKE_HEIGHT,
                SNAKE_WIDTH,
                SNAKE_HEIGHT, canvas).setColor(Color.green);
        }
    }

    /*
     * This method draws the fruit and sets its color to red
     */
    public void drawFruit(){
        new FilledOval(fruit.getX() * SNAKE_WIDTH, fruit.getY() * SNAKE_HEIGHT, SNAKE_WIDTH,
            SNAKE_HEIGHT, canvas).setColor(Color.red);
    }

    /*
     * This method randomly generates a location for the fruit
     */
    public void fruitGenerator()
    {
        // Get a random x and y for a random location
        int x = r.nextInt(BOX_SIZE);
        int y = r.nextInt(BOX_SIZE);
        Location point = new Location(x, y);

        // If snake contains the fruit locate another point for the fruit
        while (snake.contains(point)){

            point = new Location(x, y);

        }

        // Initialize fruit to the point
        fruit = point;
    }

    /*
     * This method is responsible for the growth of the snake
     */
    public void grow()
    {
        Location back = headPoint;

        // Add the new part to the snake based on in which direction it is moving
        if (direction == UP)
        {
            headPoint = new Location(head.getX(), head.getY() - 1);
        }
        else if (direction == DOWN)
        {
            headPoint = new Location(head.getX(), head.getY() + 1);
        }
        else if (direction == LEFT)
        {
            headPoint = new Location(head.getX() - 1, head.getY());
        }
        else if (direction == RIGHT)
        {
            headPoint = new Location(head.getX() + 1, head.getY());
        }

        // Add the point to the linked list
        snake.push(back);

        // Generate an another fruit
        fruitGenerator();
    }

    /*
     * This mehtod plays the key role in this game. It moves the snake continously until the
     * game is over, and it also responsible for the growth of the snake.
     */
    public void move()
    {
        // Take a reference of the head of the snake
        head = snake.peekFirst();
        headPoint = head;
        // Based on the direction move the snake by one unit in that direction
        if (direction == UP)
        {
            headPoint = new Location(head.getX(), head.getY() - 1);
        }
        else if (direction == DOWN)
        {
            headPoint = new Location(head.getX(), head.getY() + 1);
        }
        else if (direction == LEFT)
        {
            headPoint = new Location(head.getX() - 1, head.getY());
        }
        else if (direction == RIGHT)
        {
            headPoint = new Location(head.getX() + 1, head.getY());
        }

        // Remove the last tail of the snake
        snake.remove(snake.peekLast());

        // If the snake overlaps the fruit make sure you grow the snake
        if (headPoint.equals(fruit))
        {
            grow();
            ++score;
        }
        // Stop the game if it exceeds any boundary of the canvas
        else if (headPoint.getX() < CANVAS_X || headPoint.getX() > (BOX_SIZE - 1))
        {
            inGame = false;
        }

        else if (headPoint.getY() < CANVAS_Y || headPoint.getY() > (BOX_SIZE - 1))
        {
            inGame = false;
        }

        //Add the point at the starting of the snake
        snake.push(headPoint);
    }

    /*
     * This method is responsible for the running of the game which performs 
     *  the move(), draw() method until the game is not over 
     */
    public void run(){

        while(inGame)
        {
            move();
            draw();
            pause(DELAY_TIME);

        }

        // Clear the snake after the game is over and display a game over message
        snake.clear();
        gameOver();

    }

    /*
     * This methods displays a message after the game is over
     */
    public void gameOver()
    {
        new FilledRect(CANVAS_X, CANVAS_Y, RECT_WIDTH, 
            RECT_HEIGHT, canvas).setColor(Color.red);

        new Text("GAME_OVER", TEXT_X, TEXT_Y, 
            canvas).setFontSize(2 * FONT_SIZE);

        new Text("YOUR SCORE IS: " + score, TEXT_X, TEXT_Y + 2 *TEXT_DIFF,
            canvas).setFontSize(FONT_SIZE);

    }

    /*
     * This method returns the value of the direction
     */
    public int getDirection()
    {
        return direction;
    }

    /*
     * This method sets the value to the direction based on what value it gets
     */
    public void setDirection(int dir)
    {
        direction = dir;
    }
}