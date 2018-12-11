package model;

import java.io.Serializable;



public class Card implements Serializable{


	private String description;
	

	private String word;

	
	public Card(String description, String word) {
		this.description = description;
		this.word = word;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getWord() {
		return word;
	}


	public void setWord(String word) {
		this.word = word;
	}
	
	
}
