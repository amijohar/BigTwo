package assignment5;

/**
 * A subclass of Hand class. Used to model a hand of FullHouse
 * It overrides the getType() and isValid() methods of Hand class
 * @author amanjohar
 *
 */
public class FullHouse extends Hand {

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
	public FullHouse(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}
    
	/** 
     * The overriding method for isValid() in Hand class
     * It checks whether a hand is valid or not
	 */
	@Override
	public boolean isValid() {
		boolean flag = false;
		this.sort();
		if (this.getCard(0).getRank() == this.getCard(1).getRank() && this.getCard(1).getRank() == this.getCard(2).getRank()){
			if (this.getCard(3).getRank() == this.getCard(4).getRank()){
				flag = true;
			}
		}
		else if (this.getCard(0).getRank() == this.getCard(1).getRank()){
			if (this.getCard(2).getRank() == this.getCard(3).getRank() && this.getCard(3).getRank() == this.getCard(4).getRank()){
				flag = true;
			}
		}
		return flag;
	}
	

	/** 
	 * The overriding method for getType() of Hand Class
	 * A method for returning specifying the type of this hand
	 */
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "FullHouse";
	}

}
