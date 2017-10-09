package assignment5;


/**
 * The Hand class is a subclass of the CardList class, and is used to model a hand of cards. It has a
 * private instance variable for storing the player who plays this hand. It also has methods for
 * getting the player of this hand, checking if it is a valid hand, getting the type of this hand, getting
 * the top card of this hand, and checking if it beats a specified hand
 * @author amanjohar
 *
 */
public abstract class Hand extends CardList 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * a constructor for building a hand with
     * the specified player and list of cards
	 * @param player
	 * 		the player playing this hand
	 * @param cards
	 * 		the cards played by the player
	 */
	public Hand(CardGamePlayer player, CardList cards)
	{
		this.player=player;
		cards=player.getCardsInHand();
		
	}
	
	private CardGamePlayer player; //the player who plays this hand
	
	
	/**
	 * a method for retrieving the player of this hand
	 * @return player
	 * 		the player who plays this hand
	 */
	public CardGamePlayer getPlayer()
	{
		return player;
	}
	
	/**
	 * a method for retrieving the top card of this hand
	 * @return card
	 * 			the top card
	 * 		 
	 * 		
	 */
	public Card getTopCard()
	{
		
			this.sort();
			
			if(this.getType() == "Single"){
				
				return this.getCard(0);
			}
			else if(this.getType() == "Pair"){
				return this.getCard(1);
			}
			else if (this.getType() == "Triple"){
				return this.getCard(2);
			}
			else if(this.getType() == "Quad"){
				{
					int r1,r2,r3,r4,r5;
					 r1=this.getCard(0).getRank();
					  r2=this.getCard(1).getRank(); 
					  r3=this.getCard(2).getRank();
					  r4=this.getCard(3).getRank(); 
					  r5=this.getCard(4).getRank();
					
					  if((r1==r2 && r2==r3 && r3==r4))
						  return this.getCard(3);
					  else
						  return this.getCard(4);
				}
			}
			else if(this.getType()=="FullHouse")
			{
				int r1,r2,r3,r4,r5;
				 r1=this.getCard(0).getRank();
				  r2=this.getCard(1).getRank(); 
				  r3=this.getCard(2).getRank();
				  r4=this.getCard(3).getRank(); 
				  r5=this.getCard(4).getRank();
				
					return this.getCard(2);
				
			}
				
			
			else if (this.getType() == "Straight" || this.getType() == "Flush" || this.getType() == "StraightFlush")
			{
				int r1,r2,r3,r4,r5;
				 r1=this.getCard(0).getRank();
				  r2=this.getCard(1).getRank(); 
				  r3=this.getCard(2).getRank();
				  r4=this.getCard(3).getRank(); 
				  r5=this.getCard(4).getRank();
				if(r1==1)
					return this.getCard(0);
				else if(r2==1)
					return this.getCard(1);
				else if(r1==0)
					return this.getCard(0);
				else
					return this.getCard(4);
					
			}
			else
				return null;
		
		
	}
	
	/**
	 * a method for checking if this hand beats a specified hand
	 * @param hand
	 * 		cards played by the player
	 * @return true/false
	 * 		
	 */
	public boolean beats(Hand hand)
	{
		String type = hand.getType();
		String a = this.getType();
		
		int x = 0, y = 0;
		String order[] = {"Single","Pair","Triple","Straight", "Flush", "FullHouse","Quad", "StraightFlush"};
		for (int i = 0; i < 8; i++){
			if (type.equals(order[i])){
				x = i;
			}
			if (a.equals(order[i])){
				y = i;
			}
		}
		if (y > x){
			return true;
		}
		else if(x>y)
		{
			return false;
		}
		else{
			if (this.getTopCard().compareTo(hand.getTopCard()) == 1)
				return true;
			else
				return false;
		}
	}
	
	
	public abstract boolean isValid();
	
	public abstract String getType();
	

}
