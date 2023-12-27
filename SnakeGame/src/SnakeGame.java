
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
	
	private class Tile {
		int x;
		int y;
		
		Tile(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	
	int boardWidth;
	int boardHeight;
	int tileSize= 25;
	
	//Snake
	Tile snakeHead;
	ArrayList<Tile> snakebody;
	
	
	//Food
	Tile food;
	Random random;
	
	//game logic
	Timer gameLoop;
	int velocityX;
	int velocityY;
	boolean gameOver = false;
	
	SnakeGame(int boardWidth, int boardHeight) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		setPreferredSize(new Dimension(this.boardWidth ,this.boardHeight));
		setBackground(Color.black);
		addKeyListener(this);
		setFocusable(true);
		
		snakeHead = new Tile(5,5);
		snakebody = new ArrayList<Tile>();
		
		food = new Tile(10,10);
		random = new Random();
		placeFood();
		
		velocityX = -1;
		velocityY = 0;
		
		
		gameLoop = new Timer(100,this);
		gameLoop.start();
		
		
	}
	
	  public void restartGame() {
	        snakeHead = new Tile(5, 5);
	        snakebody.clear();
	        placeFood();
	        velocityX = -1;
	        velocityY = 0;
	        gameOver = false;
	        gameLoop.start();
	    }
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);;
		draw(g);
	}

	public void draw(Graphics g) {
		//Grid
		/*for(int i = 0; i<boardWidth/tileSize; i++) {
			g.drawLine(i *tileSize, 0, i * tileSize, boardHeight);
			g.drawLine(0, i * tileSize, boardWidth, i*tileSize);
		}*/
		
		//Food
		g.setColor(Color.red);
		g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
		g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);
		
		
		//Snake Head
		g.setColor(Color.green);
		g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
		g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);
		
		
		
		//Snake Body
		for(int i = 0; i < snakebody.size(); i++) {
			Tile snakePart = snakebody.get(i);
			g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
			
		}
	
		//Score
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		if(gameOver) {
			g.drawString("Game Over!    To restart press F5     Your score: " + String.valueOf(snakebody.size()), tileSize - 16, tileSize);
		}
		else {
			g.drawString("Score: " + String.valueOf(snakebody.size()), tileSize - 16, tileSize);
		}
		
	
	}
	
	public void placeFood() {
		food.x = random.nextInt(boardWidth/tileSize); //600/25 = 24 
		food.y = random.nextInt(boardHeight/tileSize);
		
	}

	public boolean collision (Tile tile1, Tile tile2) {
		return tile1.x == tile2.x && tile1.y == tile2.y;
	}
	
	public void move() {
		
		//eat food
		if(collision(snakeHead, food)) {
			snakebody.add(new Tile(food.x, food.y));
			placeFood();
		}
		
		//Snake Body 
		try {
			for( int i = snakebody.size() -1; i >= 0; i-- ) {
				Tile snakePart = snakebody.get(i);
				if(i == 0) {
					snakePart.x = snakeHead.x;
					snakePart.y = snakeHead.y;
				}else {
					Tile prevSnakePart = snakebody.get(i - 1);
					snakePart.x = prevSnakePart.x;
					snakePart.y = prevSnakePart.y;
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(getComponentPopupMenu(), "Erro in function MOVE");
		}
		
		
		
		//SnakeHead
		snakeHead.x += velocityX;
		snakeHead.y += velocityY;
		
		//game over condition 
		for( int i = 0; i <snakebody.size(); i++) {
			Tile snakePart = snakebody.get(i);
			//collide with the snake head
			if (collision(snakeHead, snakePart)) {
				gameOver = true;
			}
		}
		if(snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth ||
				snakeHead.y * tileSize < 0 || snakeHead.y* tileSize > boardHeight) {
			gameOver = true;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		repaint();
		if(gameOver) {
			gameLoop.stop();
		}
		
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
	if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
		velocityX = 0;
		velocityY = -1;
		
	}else if( e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
		velocityX = 0;
		velocityY = 1;
		
		
	}else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
		velocityX = -1;
		velocityY = 0;
		
	} else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
		velocityX = 1;
		velocityY = 0;
	} else if (e.getKeyCode() == KeyEvent.VK_F5) {
        // Reinicia o jogo quando a tecla F5 é pressionada
        restartGame();
    }
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	
}

