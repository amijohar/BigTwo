package assignment5;

import java.util.Arrays;

/**
 * A subclass of Hand class. Used to model a hand of StraightFlush
 * It overrides the getType() and isValid() methods of Hand class
 * @author amanjohar
 *
 */
public class StraightFlush extends Hand {

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
	public StraightFlush(CardGamePlayer player, CardList cards) {
		super(player, cards);
		// TODO Auto-generated constructor stub
	}

	/** 
     * The overriding method for isValid() in Hand class
     * It checks whether a hand is valid or not
	 */
	@Override
	public boolean isValid() {
		boolean flag1=true,flag2=true;
		this.sort();
		  
    	int [] newRanks = new int[5];
		
		for(int i=0;i<5;i++)
		{
			if(this.getCard(i).getRank()<=1)
				newRanks[i]=this.getCard(i).getRank()+13;
			else
				newRanks[i]=this.getCard(i).getRank();
		}
		Arrays.sort(newRanks);
		for(int i=0;i<4;i++)
		{
			if(newRanks[i]+1 != newRanks[i+1])
				{ 
				  flag1=false;
			      break;
				}
			
		}
		
		for(int i=0; i<this.size()-1;i++)
		  {
			  if(this.getCard(i).getSuit()!=this.getCard(i+1).getSuit())
			  { flag2=false;
			      break;
			  }
		  }
		  if(flag1==true && flag2==true)
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
		return "StraightFlush";
	}

}
