package assignment5;

import java.util.ArrayList;
import java.io.*;
import java.net.*;


public class BigTwoClient implements CardGame, NetworkGame
{
	public BigTwoClient()
	{
		playerList = new ArrayList<>();
		handsOnTable = new ArrayList<>();
		for (int i = 0; i < 4; i++)
		{
			playerList.add(new CardGamePlayer());
		}
		table= new BigTwoTable(this);
	    playerName = table.getName();
		makeConnection();
	
	}
	
	int	numOfPlayers;
	private Deck deck;  //a deck of cards
	private ArrayList<CardGamePlayer> playerList; //a list of players
	private ArrayList<Hand> handsOnTable; //a list of hands played on the table
	int	playerID; // an integer specifying the playerID (i.e., index) of the local player.
	String	playerName; // a string specifying the name of the local player.
	String	serverIP; // a string specifying the IP address of the game server.
	int	serverPort; // an integer specifying the TCP port of the game server.
	Socket	sock; // a socket connection to the game server.
	ObjectOutputStream	oos; // an ObjectOutputStream for sending messages to the server.
	private int currentIdx; //an integer specifying the index of the current player
	private BigTwoTable table;; //a BigTwoTable object for providing the user interface
	Card start= new Card(0,2); //stores the 3 of diamonds card
	int idx; //index of player
	int startidx; //index of starting player
	int passes=0; //no of passes
	boolean startGame = true; //to check if it's the first move of the game
	
	

	@Override
	public int getPlayerID() {
		// TODO Auto-generated method stub
		return playerID;
	}

	@Override
	public void setPlayerID(int playerID) {
		// TODO Auto-generated method stub
		this.playerID=playerID;
	}

	@Override
	public String getPlayerName() {
		// TODO Auto-generated method stub
		return playerName;
	}

	@Override
	public void setPlayerName(String playerName) {
		// TODO Auto-generated method stub
		this.playerName=playerName;
	}

	@Override
	public String getServerIP() {
		// TODO Auto-generated method stub
		return serverIP;
	}

	@Override
	public void setServerIP(String serverIP) {
		// TODO Auto-generated method stub
		this.serverIP=serverIP;
	}

	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return serverPort;
	}

	@Override
	public void setServerPort(int serverPort) {
		// TODO Auto-generated method stub
		this.serverPort=serverPort;
	}

	@Override
	public void makeConnection() {
		// TODO Auto-generated method stub
		String ip=table.getIP();
		if(ip==null)
		{
			ip="127.0.0.1";
		}
		try
		{
			sock = new Socket(ip, 2396);
			oos = new ObjectOutputStream(sock.getOutputStream());
			Thread readerThread = new Thread(new ServerHandler());
			readerThread.start();
			CardGameMessage message = new CardGameMessage(CardGameMessage.JOIN,-1,playerName);
			sendMessage(message);
			message = new CardGameMessage(CardGameMessage.READY,-1,null);
			sendMessage(message);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public synchronized void parseMessage(GameMessage message) {
		// TODO Auto-generated method stub
		switch(message.getType())
		{
		   case 0: 
			   playerID= message.getPlayerID();
			   table.setActivePlayer(playerID);
			   String[] names=(String[])message.getData();
			   for(int i=0;i<4;i++)
			   {
				  playerList.get(i).setName(names[i]); 
			   }
			   table.repaint();
			   break;
		   case 1: 
			   String name = (String)message.getData();
			   
			   playerList.get(message.getPlayerID()).setName(name);
			   break;
		   case 2:
			   table.printMsg("Server Full. Unable To Join\n");
			   break;
		   case 3:
			   playerList.get(message.getPlayerID()).setName("");
			   for(int j=0;j<4;j++)
			   {
				  playerList.get(j).removeAllCards();
			   }
			   handsOnTable.removeAll(getHandsOnTable());
			   CardGameMessage m= new CardGameMessage(CardGameMessage.READY,-1,null);
			   table.repaint();
			   table.disable();
		       sendMessage(m); 
		       break;
		   case 4:
			   name=playerList.get(message.getPlayerID()).getName();
			   table.printMsg("Player "+name+" is Ready!\n");
			   break;
		   case 5:
			   passes=0;
			   startGame=true;
			   handsOnTable.removeAll(getHandsOnTable());
			   table.enable();
			   BigTwoDeck deck=(BigTwoDeck)message.getData();
			   start(deck);
			   break;
		   case 6:
			   int[] cardidx=(int[])message.getData();
			   checkMove(message.getPlayerID(),cardidx);
			   table.repaint();
			   break;
		   case 7:
			   table.printChat((String)message.getData());
			   break;
		}
	}

	@Override
	public void sendMessage(GameMessage message) {
		// TODO Auto-generated method stub
		try
		{
			oos.writeObject(message);
			oos.flush();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public int getNumOfPlayers() {
		// TODO Auto-generated method stub
		return numOfPlayers;
	}

	public Deck getDeck()
	{
		return deck;
	}
	
	/**
	 * – a method for retrieving the list of players
	 * @return playerList
	 * 		a list of players
	 */
	public ArrayList<CardGamePlayer> getPlayerList()
	{
		return playerList;
	}
	
	/**
	 * a method for retrieving the list of hands played on
     * the table
	 * @return handsOnTable
	 * 		a list of hands played on the table
	 */
	public ArrayList<Hand> getHandsOnTable()
	{
		return handsOnTable;
	}
	
	/**
	 * a method for retrieving the index of the current player
	 * @return currentIdx
	 * 		an integer specifying the index of the current player		
	 */
	public int getCurrentIdx()
	{
		return currentIdx;
	}
	
	/**
	 * a method for starting the game with a (shuffled) deck of
     * cards supplied as the argument. 
	 * @param deck
	 * 		a deck of cards
	 */
	public void start(BigTwoDeck deck)
	{
		for(int i=0;i<52;)
		{
			for(int j=0;j<4;j++)
			{
				playerList.get(j).addCard(deck.getCard(i));
				i++;
			}
		}
		
		Card start= new Card(0,2);
		for(int i=0;i<4;i++)
		{
			if(playerList.get(i).getCardsInHand().contains(start))
			{
				startidx=i;
			}
		}
		
	    //table.setActivePlayer(startidx);
		table.repaint();
		idx=startidx;
	}

	@Override
	public void makeMove(int playerID, int[] cardIdx) {
		// TODO Auto-generated method stub
		CardGameMessage m = new CardGameMessage(CardGameMessage.MOVE, playerID, cardIdx);
		sendMessage(m);
	}

	@Override
	public void checkMove(int playerID, int[] cardIdx) {
		// TODO Auto-generated method stub
		CardList hand = new CardList();
		CardList cards= new CardList();
		CardGamePlayer player = new CardGamePlayer();
		Hand validHand = null;
		player=playerList.get(idx);
		table.printMsg("Player "+idx+"'s turn:\n");
		table.repaint();
		cards=player.getCardsInHand();
		hand= player.play(cardIdx);
		if(hand==null && passes!=3)
		{
			if(startGame != true)
			{
				table.printMsg("{Pass}\n");
				idx=(idx==3)?0:idx+1;
				//table.setActivePlayer(idx);
				passes++;
			}
			else
				table.printMsg("{Pass} <==  Not a legal move!!!\n");
				
			  
			
		}
		else if(hand==null && passes==3)
		{
			table.printMsg("{Pass} <==  Not a legal move!!!\n");
		}
		else if(idx==startidx-- && !(hand.contains(start)))
		{
			table.printMsg(hand.toString()+" <==  Not a legal move!!!\n");
			startidx++;
			
		}
		
		else if(handsOnTable.size()!=0 && hand.size()!=handsOnTable.get(handsOnTable.size()-1).size() && passes!=3 )
		{
			
			table.printMsg(hand.toString()+" <==  Not a legal move!!!\n");
			
		}
		else
			
		{
			startidx=-1;
			startGame=false;
			if(passes==3)
				{
				  passes=0;
				  handsOnTable.clear();
				}
		    Hand validhand;
		 
		   
		     validhand= composeHand(player,hand);
		   
		   if(validhand!=null)
		   {
			  if(handsOnTable.size()!=0)
			  { 
				  if(validhand.beats(handsOnTable.get(handsOnTable.size()-1)))
				  {
					  passes=0;
					  handsOnTable.add(validhand);
					  table.printMsg("{"+validhand.getType()+"}" + validhand.toString()+"\n");
					  idx=(idx==3)?0:idx+1;
					  //table.setActivePlayer(idx);
					  for(int i=0;i<cardIdx.length;i++)
					  {
						  cards.removeCard(cardIdx[i]-i);
					  }
					  
				  }
				  else
					  table.printMsg(hand.toString()+" <==  Not a legal move!!!\n");
					  
			  }
			  else
			  {
				  passes=0;
				  handsOnTable.add(validhand);
				  table.printMsg("{"+validhand.getType()+"}" + validhand.toString()+ "\n");
			      idx=(idx==3)?0:idx+1;
			      //table.setActivePlayer(idx);
			      for(int i=0;i<cardIdx.length;i++)
				  {
					  cards.removeCard(cardIdx[i]-i);
				  }
			      
			  }
		  }
		 
		  else
		  {
			  table.printMsg(hand.toString()+" <==  Not a legal move!!!\n"); 
		  }
		}
		
		if(endOfGame() == true)
		{
			String winMsg=new String();
			winMsg="Game Ends\n";
			table.printMsg("Game ends\n");
			for(CardGamePlayer x : playerList)
			{
					if (x.getNumOfCards() != 0)
						winMsg+=x.getName() + " has " + x.getNumOfCards() + " cards in hand.\n";
					else
						winMsg+=x.getName() + " wins the game.\n"; 
				
			}
			table.showWin(winMsg);
			table.disable();
		}
		table.resetSelected();
		table.repaint();
	}

	@Override
	public boolean endOfGame() {
		// TODO Auto-generated method stub
		int i=0;
		if(idx==0)
			i=3;
		else
			i=idx-1;
		if(playerList.get(i).getCardsInHand().isEmpty())
			return true;
		else
		    return false;
	}

	@Override
	public void start(Deck deck) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * a method for returning a
     * valid hand from the specified list of cards of the player. Returns null is no valid hand can
     * be composed from the specified list of cards
	 * @param player
	 * 		the current player
	 * @param hand
	 * 		cards played by the current player
	 * @return
	 * 		cards played by the current player
	 */
	public static Hand composeHand(CardGamePlayer player, CardList cards)
	{
		if (cards.size() == 1)
		{
			Single single = new Single(player,cards);
			if (single.isValid()){
				for (int i = 0; i < cards.size(); i++){
					single.addCard(cards.getCard(i));
				}
				
				return single;
			}
			else
				return null;
			
		}
		else if (cards.size() == 2)
		{
			
			Pair pair = new Pair(player,cards);
			for (int i = 0; i < cards.size(); i++){
				pair.addCard(cards.getCard(i));
			}
			if (pair.isValid()){
				
				return pair;
			}
			else
				return null;
			
		}
		else if (cards.size() == 3)
		{
			Triple triple = new Triple(player,cards);
			for (int i = 0; i < cards.size(); i++){
				triple.addCard(cards.getCard(i));
			}
			if (triple.isValid()){
				
				return triple;
			}
			else
				return null;
			
		}
		else if (cards.size() == 5)
		{
			Quad quad = new Quad(player,cards);
			for (int i = 0; i < cards.size(); i++){
				quad.addCard(cards.getCard(i));
			}
			Flush flush = new Flush(player,cards);
			for (int i = 0; i < cards.size(); i++){
				flush.addCard(cards.getCard(i));
			}
			Straight straight = new Straight(player,cards);
			for (int i = 0; i < cards.size(); i++){
				straight.addCard(cards.getCard(i));
			}
			StraightFlush straightFlush = new StraightFlush(player,cards);
			for (int i = 0; i < cards.size(); i++){
				straightFlush.addCard(cards.getCard(i));
			}
			FullHouse fullHouse = new FullHouse(player,cards);
			for (int i = 0; i < cards.size(); i++){
				fullHouse.addCard(cards.getCard(i));
			}
			if (quad.isValid())
			{
				
				return quad;
			}
			
			else if (straightFlush.isValid())
			{
				
				return straightFlush;
			}
			
			else if (straight.isValid()){
				
				return straight;
			}
			
			else if (flush.isValid()){
				
				return flush;
			}
			
			else if (fullHouse.isValid())
			{
				
				return fullHouse;
			}
			else
			{ 
				return null;
			}
			
		}
		else
			return null;
	
		
	}
	
	public static void main (String[] args)
	{
		BigTwoClient bigTwoClient= new BigTwoClient();
	}
	
	class ServerHandler implements Runnable
	{
        public void run() 
		{
			try
			{
				ObjectInputStream inputStream = new ObjectInputStream(sock.getInputStream());
				while(true)
				{
					parseMessage((CardGameMessage)inputStream.readObject());
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
			
	 
	
	
	

}
