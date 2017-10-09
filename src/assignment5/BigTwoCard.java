package assignment5;

/**
 * The BigTwoCard class is a subclass of the Card class, and is used to model a card used in a Big
 * Two card game. It overrides the compareTo() method it inherited from the Card class to
 * reflect the ordering of cards used in a Big Two card game
 * @author amanjohar
 *
 */
public class BigTwoCard extends Card {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * a constructor for building a card with the specified
     * suit and rank. suit is an integer between 0 and 3, and rank is an integer between 0 and 12
	 * @param suit
	 * 		index of the suit of the card
	 * @param rank
	 * 		index of the rank of the card
	 */
	public BigTwoCard(int suit, int rank)
	{
		super(suit,rank);
	}
	
	/**
	 * a method for comparing this card with the specified card for
     * order. Returns a negative integer, zero, or a positive integer as this card is less than, equal
     * to, or greater than the specified card
	 *  @param card
	 *  		represents a card from a deck
	 */
	public int compareTo(Card card)
	{
		
		if (this.rank == card.rank ) 
		{
		   if (this.suit > card.suit) 
		      return 1;
		   else 
			   return -1; 
		
	    }
		else
		{   
			if(this.rank==1)
				return 1;
			else if(this.rank==0 && card.rank==1)
				return -1;
			else if(this.rank==0)
				return 1;
			else if(this.rank>card.rank)
				return 1;
			else
				return -1;
		}

    }
}
