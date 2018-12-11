package model;

import java.util.List;


public class LIFODeck {
	

    private final int INITIAL_SIZE = 20;
    

    private Card[] elements = new Card[INITIAL_SIZE];
    
   
    private int front = -1;
    
    
	public LIFODeck(List<Card> cards){
	
		for (Card card: cards){
			push(card);
		}
	}
    

    public Card pop(){
    	Card data = elements[front];
        front = (front - 1) % elements.length;
        return data;
    }
    

    public void push(Card card){
        
    	front = front + 1;

        if (front == elements.length){
            

        	Card[] temp = new Card[elements.length * 2];
            for (int i = 0; i < front; i++){
            	temp[i] = elements[i];
            }
            elements = temp;
        }
        
        elements[front] = card;
        
    }
    
    
    public Card getFront(){
        return elements[front];
    }
    

    public boolean isEmpty(){
        return front == -1;
    }
}
