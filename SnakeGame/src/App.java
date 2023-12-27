
import java.awt.Graphics;

import javax.swing.*;

import javafx.scene.shape.DrawMode;

public class App {
public static void main(String[] args) {
	int boardWidth = 600;
	int boardHeight = boardWidth;
	
	JFrame frame = new JFrame("Snake");
	frame.setVisible(true);
	frame.setSize(boardHeight, boardWidth);
	frame.setLocationRelativeTo(null);
	frame.setResizable(false);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	
	SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
	frame.add(snakeGame);
	frame.pack();
	snakeGame.requestFocus();

	
}


}
