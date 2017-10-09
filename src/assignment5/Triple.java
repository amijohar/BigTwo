package assignment5;

/**
 * A subclass of Hand class. Used to model a hand of Triple
 * It overrides the getType() and isValid() methods of Hand class
 * @author amanjohar
 *
 */
public class Triple extends Hand {

	
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
	public Triple(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}

	/** 
     * The overriding method for isValid() in Hand class
     * It checks whether a hand is valid or not
	 */
	@Override
	public boolean isValid() {
		if((this.getCard(0).getRank() == this.getCard(1).getRank()) && this.getCard(1).getRank()==this.getCard(2).getRank())
				return true;
		else return false;
	}
	
	/** 
	 * The overriding method for getType() of Hand Class
	 * A method for returning specifying the type of this hand
	 */
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Triple";
	}

}
