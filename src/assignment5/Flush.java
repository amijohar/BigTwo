package assignment5;

/**
 * A subclass of Hand class. Used to model a hand of Flush
 * It overrides the getType() and isValid() methods of Hand class
 * @author amanjohar
 *
 */
public class Flush extends Hand {

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
	public Flush(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}
    
	
	/** 
     * The overriding method for isValid() in Hand class
     * It checks whether a hand is valid or not
	 */
	@Override
	public boolean isValid() {
		
			  this.sort();
		int flag=1;
		for(int i=0; i<this.size()-1;i++)
		  {
			  if(this.getCard(i).getSuit()!=this.getCard(i+1).getSuit())
			  { flag=0;
			      break;
			  }
		  }
		  if(flag==1)
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
		return "Flush";
	}

}
