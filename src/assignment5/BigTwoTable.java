package assignment5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 * The BigTwoTable class implements the CardGameTable interface. It is used to
 * build a GUI for the Big Two card game and handle all user actions. Below is a
 * detailed description for the BigTwoTable class.
 * 
 * @author amanjohar
 *
 */
public class BigTwoTable implements CardGameTable {
	/**
	 * a constructor for creating a BigTwoTable. The parameter game is a
	 * reference to a card game associates with this table.
	 * 
	 * @param game
	 *            a card game associated with this table
	 */
	public BigTwoTable(CardGame game) {
		this.game = game;
		menuBar = new JMenuBar();
		menu = new JMenu("GAME");
		selected = new boolean[13];
		for (int i = 0; i < 13; i++) {
			selected[i] = false;
		}
		JPanel lFrame = new JPanel();
		JPanel rFrame = new JPanel();
		JPanel chatframe = new JPanel();
		JSplitPane pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		lFrame.setBackground(new Color(0, 128, 0));
		JPanel bFrame = new JPanel();
		JPanel b1frame = new JPanel();
		frame = new JFrame("BIG TWO");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menuBar);
		connect = new JMenuItem("Connect");
		quit = new JMenuItem("Quit");
		menu.add(connect);
		menu.add(quit);
		menuBar.add(menu);
		bigTwoPanel = new BigTwoPanel();
		playButton = new JButton("PLAY");
		playButton.addActionListener(new PlayButtonListener());
		passButton = new JButton("PASS");
		passButton.addActionListener(new PassButtonListener());
		connect.addActionListener(new ConnectMenuItemListener());
		quit.addActionListener(new QuitMenuItemListener());

		char[] s = { 'd', 'c', 'h', 's' };
		char[] r = { 'a', '2', '3', '4', '5', '6', '7', '8', '9', 't', 'j',
				'q', 'k' };
		cardImages = new Image[4][13];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				cardImages[i][j] = new ImageIcon("Images/" + r[j] + s[i]
						+ ".gif").getImage();
			}
		}
		cardBackImage = new ImageIcon("Images/b.gif").getImage();
		avatars = new Image[4];
		avatars[0] = new ImageIcon("Images/batman_64.png").getImage();
		avatars[1] = new ImageIcon("Images/flash_64.png").getImage();
		avatars[2] = new ImageIcon("Images/superman_64.png").getImage();
		avatars[3] = new ImageIcon("Images/green_lantern_64.png").getImage();

		msgArea = new JTextArea();
		chatArea = new JTextArea();
		chatText = new JTextField("Press Enter to Send",30);
		JLabel message = new JLabel("Message:");
		chatText.addKeyListener(new EnterKeyListener());
		frame.setSize(1280, 720);
		frame.setLayout(new GridLayout(1, 2));
		lFrame.setLayout(new BorderLayout());
		rFrame.setLayout(new BorderLayout());
		chatframe.setLayout(new BorderLayout());
		b1frame.setLayout(new FlowLayout());
		b1frame.add(message);
		b1frame.add(chatText);
		chatframe.add(b1frame, BorderLayout.SOUTH);
		bigTwoPanel.setLayout(new GridBagLayout());
		bFrame.add(playButton);
		bFrame.add(passButton);
		lFrame.add(bFrame, BorderLayout.SOUTH);
		lFrame.add(bigTwoPanel);

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets(2, 8, 0, 0);
		c.ipadx = 0;
		c.ipady = 0;

		JLabel player0 = new JLabel(game.getPlayerList().get(0).getName());
		c.gridy = 1;
		c.weighty = 1.0;
		bigTwoPanel.add(player0, c);

		JLabel player1 = new JLabel(game.getPlayerList().get(1).getName());
		c.gridy = 2;
		c.weighty = 1.0;
		bigTwoPanel.add(player1, c);

		JLabel player2 = new JLabel(game.getPlayerList().get(2).getName());
		c.gridy = 4;
		c.weighty = 1.0;
		bigTwoPanel.add(player2, c);

		JLabel player3 = new JLabel(game.getPlayerList().get(3).getName());
		c.gridy = 6;
		c.weighty = 2.4;
		bigTwoPanel.add(player3, c);

		JTextField nField = new JTextField(5);
		JTextField ipField = new JTextField(5);

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Name:"));
		myPanel.add(nField);
		myPanel.add(Box.createHorizontalStrut(15));
		myPanel.add(new JLabel("IP:"));
		myPanel.add(ipField);

		int result = JOptionPane.showConfirmDialog(null, myPanel,
				"Welcome to BigTwo", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) 
		{
			ip = ipField.getText();
			name = nField.getText();
			System.out.println(name);
		} 
		else
			System.exit(0);

		JScrollPane scroll = new JScrollPane(msgArea);
		JScrollPane scroll1 = new JScrollPane(chatArea);
		rFrame.add(scroll);
		chatframe.add(scroll1);
		pane.setResizeWeight(0.5);
		pane.setEnabled(false);
		pane.setDividerSize(0);
		pane.add(rFrame);
		pane.add(chatframe);
		frame.add(lFrame);
		frame.add(pane);
		frame.setVisible(true);
	}

	private JTextArea chatArea;
	private String ip;
	private String name;
	private JTextField chatText;
	private CardGame game; // a card game associates with this table
	private boolean[] selected; // a card game associates with this table
	private int activePlayer = -1; // an integer specifying the index of the
									// active player
	private JFrame frame; // the main window of the application.
	private JPanel bigTwoPanel; // a panel for showing the cards of each player
								// and the cards played on the table
	private JButton playButton; // a “Play” button for the active player to play
								// the selected cards
	private JButton passButton; // a “Pass” button for the active player to pass
								// his/her turn to the next player
	private JTextArea msgArea; // a text area for showing the current game
								// status as well as end of game messages.
	private Image[][] cardImages; // a 2D array storing the images for the faces
									// of the cards
	private Image cardBackImage; // an image for the backs of the cards
	private Image[] avatars; // an array storing the images for the avatars
	private JMenu menu; // a menu for BigTwoGame
	private JMenuBar menuBar; // a menu bar for big two game
	private JMenuItem connect; // a menu item used to connect to the game
	private JMenuItem quit; // a menu item used to quit the game

	/**
	 * a method for setting the index of the active player
	 * 
	 * @param activePlayer
	 *            stores the index of the active player
	 */
	@Override
	public void setActivePlayer(int activePlayer) {
		// TODO Auto-generated method stub
		if (activePlayer >= game.getPlayerList().size())
			this.activePlayer = -1;
		else
			this.activePlayer = activePlayer;
	}

	/**
	 * a method for getting an array of indices of the cards selected.
	 * 
	 * @return selection an array of indices of the selected cards
	 */
	@Override
	public int[] getSelected() {
		// TODO Auto-generated method stub
		int[] selection = null;
		int count = 0;
		for (int j = 0; j < selected.length; j++) {
			if (selected[j]) {
				count++;
			}
		}

		if (count != 0) {
			selection = new int[count];
			count = 0;
			for (int j = 0; j < selected.length; j++) {
				if (selected[j]) {
					selection[count] = j;
					count++;
				}
			}
		}

		return selection;
	}

	/**
	 * a method for resetting the list of selected cards.
	 */
	@Override
	public void resetSelected() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 13; i++) {
			selected[i] = false;
		}
	}

	/**
	 * a method for repainting the GUI
	 */
	@Override
	public void repaint() {
		// TODO Auto-generated method stub
		frame.repaint();
	}

	/**
	 * a method for printing the specified string to the message area of the GUI
	 * 
	 * @param msg
	 *            contains the string to be printed on the message area
	 */
	@Override
	public void printMsg(String msg) {
		// TODO Auto-generated method stub
		msgArea.append(msg);
		frame.repaint();
	}

	public void printChat(String chat) {
		chatArea.append(chat);
		frame.repaint();
	}

	public String getName() {
		if (name == null)
			name = "Default Player";
		return name;
	}

	public String getIP() {
		return ip;
	}

	/**
	 * a method for clearing the message area of the GUI
	 */
	@Override
	public void clearMsgArea() {
		// TODO Auto-generated method stub
		msgArea.setText(null);
	}

	/**
	 * a method for enabling user interaction with the GUI
	 */
	@Override
	public void enable() {
		// TODO Auto-generated method stub
		playButton.setEnabled(true);
		passButton.setEnabled(true);

	}

	/**
	 * a method for disabling user interaction with the GUI
	 */
	@Override
	public void disable() {
		// TODO Auto-generated method stub
		playButton.setEnabled(false);
		passButton.setEnabled(false);
	}

	public void showWin(String s) {
		JOptionPane.showMessageDialog(frame, s);
	}

	/**
	 * an inner class that extends the JPanel class and implements the
	 * MouseListener interface. Overrides the paintComponent() method inherited
	 * from the JPanel class to draw the card game table. Implements the
	 * mouseClicked() method from the MouseListener interface to handle mouse
	 * click events
	 * 
	 * @author amanjohar
	 *
	 */
	class BigTwoPanel extends JPanel implements MouseListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * A constructor for BigTwoPanel
		 */
		public BigTwoPanel() {
			this.addMouseListener(this);
		}

		/**
		 * A method where the drawing of the GUI takes place
		 * 
		 * @param g
		 *            an object of class Graphics
		 */
		public void paintComponent(Graphics g) {
			g.drawLine(0, 0, 700, 0);
			g.drawLine(0, 115, 700, 115);
			g.drawLine(0, 242, 700, 242);
			g.drawLine(0, 365, 700, 365);
			g.drawLine(0, 491, 700, 491);
			g.setColor(Color.BLACK);
			g.drawImage(avatars[0], 0, 30, this);
			g.drawImage(avatars[1], 0, 155, this);
			g.drawImage(avatars[2], 0, 280, this);
			g.drawImage(avatars[3], 0, 405, this);
			if (activePlayer != -1) {
				for (int i = 0; i < game.getPlayerList().get(activePlayer)
						.getNumOfCards(); i++) {
					int rank = game.getPlayerList().get(activePlayer)
							.getCardsInHand().getCard(i).getRank();
					int suit = game.getPlayerList().get(activePlayer)
							.getCardsInHand().getCard(i).getSuit();

					if (selected[i] == true) {

						g.drawImage(cardImages[suit][rank], (70 + i * 20),
								(15 + 125 * activePlayer - 15), this);
					} else {
						g.drawImage(cardImages[suit][rank], (70 + i * 20),
								(15 + 125 * activePlayer), this);
					}
				}
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < game.getPlayerList().get(i)
							.getNumOfCards(); j++) {
						if (i != activePlayer) {
							g.drawImage(cardBackImage, (70 + j * 20),
									(15 + 125 * i), this);
						}
					}
				}
				if (game.getHandsOnTable().size() != 0) {
					String play = ("Played by "
							+ game.getHandsOnTable()
									.get(game.getHandsOnTable().size() - 1)
									.getPlayer().getName() + ":");
					g.drawString(play, 10, 510);

					for (int i = 0; i < game.getHandsOnTable()
							.get(game.getHandsOnTable().size() - 1).size(); i++) {
						int r = game.getHandsOnTable()
								.get(game.getHandsOnTable().size() - 1)
								.getCard(i).getRank();
						int s = game.getHandsOnTable()
								.get(game.getHandsOnTable().size() - 1)
								.getCard(i).getSuit();
						g.drawImage(cardImages[s][r], (5 + i * 20),
								(15 + 125 * 4), this);
					}
				}
			}
		}

		/**
		 * A method which implements the steps to be taken when the mouse button
		 * is clicked
		 * 
		 * @param e
		 *            stores info from mouse click
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			int x, y;
			x = e.getX();
			y = e.getY();
			for (int i = game.getPlayerList().get(activePlayer).getNumOfCards() - 1; i >= 0; i--) {
				if ((x >= 70 + i * 20 && x <= 70 + i * 20 + 20)
						&& (y >= 15 + 125 * activePlayer && y <= 15 + 125 * activePlayer + 95)
						&& selected[i] == false
						|| (y >= 15 + 125 * activePlayer && y <= 15 + 125 * activePlayer + 95)
						&& (x >= 70 + (game.getPlayerList().get(activePlayer)
								.getNumOfCards() - 1) * 20 && x <= 70 + (game
								.getPlayerList().get(activePlayer)
								.getNumOfCards() - 1) * 20 + 80)
						&& selected[i] == false) {
					selected[i] = true;
					break;
				} else if ((x >= 70 + i * 20 && x <= 70 + i * 20 + 80)
						&& (y >= 15 + 125 * activePlayer - 15 && y <= 15 + 125
								* activePlayer + 95 - 15)
						&& selected[i] == true) {
					selected[i] = false;
					break;
				}
			}
			frame.repaint();

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * an inner class that implements the ActionListener interface. Implements
	 * the actionPerformed() method from the ActionListener interface to handle
	 * button-click events for the “Play” button
	 * 
	 * @author amanjohar
	 *
	 */
	class PlayButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int selection[] = getSelected();
			if (selection == null)
				return;
			game.makeMove(activePlayer, selection);
			resetSelected();
			frame.repaint();

		}

	}

	/**
	 * an inner class that implements the ActionListener interface. Implements
	 * the actionPerformed() method from the ActionListener interface to handle
	 * button-click events for the “Pass” button
	 * 
	 * @author amanjohar
	 *
	 */
	class PassButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			game.checkMove(activePlayer, null);
			resetSelected();
			frame.repaint();

		}

	}

	/**
	 * an inner class that implements the ActionListener interface. Implements
	 * the actionPerformed() method from the ActionListener interface to handle
	 * menu-item-click events for the “Restart” menu item
	 * 
	 * @author amanjohar
	 *
	 */
	class ConnectMenuItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			((BigTwoClient) game).makeConnection();

		}

	}

	/**
	 * an inner class that implements the ActionListener interface. Implements
	 * the actionPerformed() method from the ActionListener interface to handle
	 * menu-item-click events for the “Quit” menu item
	 * 
	 * @author amanjohar
	 *
	 */
	class QuitMenuItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);

		}

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	class EnterKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				CardGameMessage m = new CardGameMessage(CardGameMessage.MSG,
						-1, chatText.getText());
				((BigTwoClient) game).sendMessage(m);
				chatText.setText(null);
			}
		}

	}

}