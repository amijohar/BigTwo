package assignment5;

/**
 * A subclass of Hand class. Used to model a hand of Pair
 * It overrides the getType() and isValid() methods of Hand class
 * @author amanjohar
 *
 */
public class Pair extends Hand {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * a constructor for specifying the player and cards played by the player
	 * @param player
	 * 		player who plays this hand
	 * @param cards
	 * 		cards played by the player
	 */
	public Pair(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}
    
	
	/** 
     * The overriding method for isValid() in Hand class
     * It checks whether a hand is valid or not
	 */
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub

		  
			  int r1,r2;
			  r1=this.getCard(0).getRank();
			  r2=this.getCard(1).getRank(); 
			  if(r1==r2)
				  return true;
			  else 
				  return false;
		   
		   
	}

	/** 
	 * The overriding method for getType() of Hand Class
	 * A method for returning specifying the type of this hand
	 */
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Pair";
	}

}
