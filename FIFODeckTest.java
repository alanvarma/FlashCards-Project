package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Card;
import model.FIFODeck;

public class FIFODeckTest {

	private FIFODeck deck;
	
	@Before
	public void setUp() throws Exception {
		
		List<Card> cards = new ArrayList<>();
		cards.add(new Card("description 01", "word 1"));
		cards.add(new Card("description 02", "word 2"));
		cards.add(new Card("description 03", "word 3"));
		cards.add(new Card("description 04", "word 4"));
		cards.add(new Card("description 05", "word 5"));
		
		deck = new FIFODeck(cards);
		
	}

	@Test
	public void test() {

		assertTrue(!deck.isEmpty());
		
		assertEquals("word 1", deck.dequeue().getWord());
		assertEquals("word 2", deck.dequeue().getWord());
		assertEquals("word 3", deck.dequeue().getWord());
		assertEquals("word 4", deck.dequeue().getWord());
		assertEquals("description 05", deck.dequeue().getDescription());
		
		assertTrue(deck.isEmpty());
		
	}

}
