package lightbike;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")

// Main class
public class LightBike extends Canvas {
	public static final String WINDOW_TITLE = "Java LightBike";
	public static final int WINDOW_WIDTH = 1280;
	public static final int WINDOW_HEIGHT = 720;
	public static final Color LIME_GREEN = new Color(0, 255, 0);
	public static final Color RED = new Color(255, 0, 0);
	public static final Color BLACK = new Color(0, 0, 0);
	public static int mouseX = 0;
	public static int mouseY = 0;
	public static ArrayList<Button> buttonList = new ArrayList<Button>();
	private static BufferStrategy bufferStrategy;

	public LightBike() {
		JFrame container = new JFrame(WINDOW_TITLE);

		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		panel.setLayout(null);

		setBounds(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
		panel.add(this);

		setIgnoreRepaint(true);

		container.pack();
		container.setResizable(false);
		container.setVisible(true);

		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		addKeyListener(new KeyboardInput());
		addMouseListener(new MouseInputMainMenu());
		addMouseMotionListener(new MouseInputMainMenu());

		requestFocus();

		createBufferStrategy(2);
		bufferStrategy = getBufferStrategy();

		mainMenu();
	}

	public void mainMenu() {
		final int TITLE_RATIO = 4; //how far down the screen the title is (height/TITLE_RATIO)
		final int BUTTON_WIDTH = 300;
		final int BUTTON_HEIGHT = 50;

		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

		Font titleFont = new Font("Helvetica",Font.BOLD,128);
		FontMetrics titleMetrics = g.getFontMetrics(titleFont);
		g.setFont(titleFont);
		g.setColor(LIME_GREEN);

		g.drawString("LightBike", (WINDOW_WIDTH - titleMetrics.stringWidth("LightBike"))/2, WINDOW_HEIGHT/TITLE_RATIO);

		Button play = new Button((WINDOW_WIDTH - BUTTON_WIDTH)/2, 300, (WINDOW_WIDTH - BUTTON_WIDTH)/2 + BUTTON_WIDTH, 300 + BUTTON_HEIGHT, LIME_GREEN, "Play", g);
		Button quit = new Button((WINDOW_WIDTH - BUTTON_WIDTH)/2, 500, (WINDOW_WIDTH - BUTTON_WIDTH)/2 + BUTTON_WIDTH, 500 + BUTTON_HEIGHT, RED, "Quit", g);

		buttonList.add(play); //add all the buttons to an arraylist for checking mouse
		buttonList.add(quit);

		bufferStrategy.show();
	}

	public static void updateButtons(boolean moved, boolean clicked) {
		for (int i = 0; i < buttonList.size(); i++) { //go through all the buttons
			Button currentButton = buttonList.get(i);

			if (moved) {
				if (currentButton.checkMouseInBounds() == true) { //check if the mouse is within each button, if it is then update the button
					System.out.println("LEL");
					currentButton.change(1);

					bufferStrategy.show();
					break; //exit the loop once a button is updated, only one button can be updated at once
				}
				else if (currentButton.checkMouseInBounds() == false) {
					currentButton.change(0);

					bufferStrategy.show();
					break; //exit the loop once a button is updated, only one button can be updated at once
				}
			}
			else if (clicked) {
				currentButton.change(2);

				bufferStrategy.show();
				break;
			}
		}
	}

	public static void main(String[] args) {
		new LightBike();
	}
}