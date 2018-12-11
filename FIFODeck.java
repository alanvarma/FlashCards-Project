package model;

import java.util.List;


public class FIFODeck {
	
	
    private final int INITIAL_SIZE = 20;
    
    
    private Card[] elements = new Card[INITIAL_SIZE];
    

    private int first;
    
    
    private int rear;
    

    private int size = 0;
    

   	public FIFODeck(List<Card> cards){
  
   		for (Card card: cards){
   			enqueue(card);
   		}
   	}


    private void resize(int capacity) {
    	
        Card[] tmp = new Card[capacity];

        for (int i = 0; i < size; i++) {
            tmp[i] = elements[(first + i) % elements.length];
        }

        elements = tmp;
        first = 0;
        rear = size;
    }

  
    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(Card card) {
        
        if (size == elements.length){ 
            resize(elements.length * 2);
        }
        
        elements[rear] = card;
        
        rear = (rear + 1) % elements.length;
        
        size = size + 1;
    }

    
    public Card dequeue() {  
        Card  card = elements[first];
        
        first = (first + 1) % elements.length; 
        
        size = size - 1;
        
        return card;
    }
}
