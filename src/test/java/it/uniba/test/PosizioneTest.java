package it.uniba.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.uniba.main.Posizione;


class PosizioneTest {


	@Test
	void testGetRiga() {
		Posizione pos= new Posizione(3,4);
		assertEquals(3,pos.getRiga());
	}
	
	@Test
	void testGetColonna() {
		Posizione pos= new Posizione(3,4);
		assertEquals(4,pos.getColonna());
	}
	
	@Test
	void testSetRiga() {
		Posizione pos= new Posizione(3,4);
		pos.setRiga(7);
		assertEquals(7,pos.getRiga());
	}
	
	@Test
	void testSetColonna() {
		Posizione pos= new Posizione(3,4);
		pos.setColonna(1);
		assertEquals(1,pos.getColonna());
	}

}
