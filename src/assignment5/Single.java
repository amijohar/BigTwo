package assignment5;

/**
 * A subclass of Hand class. Used to model a hand of Single
 * It overrides the getType() and isValid() methods of Hand class
 * @author amanjohar
 *
 */
public class Single extends Hand 
{
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
	public Single(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}

   /** 
    * The overriding method for getType() of Hand Class
    * A method for returning specifying the type of this hand
    */
public String getType()
   {
	  return "Single"; 
   }
   

/** 
 * The overriding method for isValid() in Hand class
 * It checks whether a hand is valid or not
 */
   public boolean isValid()
   {
	   
		return true;
   }
}
