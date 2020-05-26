package it.uniba.test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

import it.uniba.main.Alfiere;
import it.uniba.main.Pezzo;
import it.uniba.main.Posizione;
import it.uniba.main.Tabella;
import it.uniba.main.Torre;

class PezzoTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
	//**Colore
	@Test
	void testgetColore() {
		Posizione pos= new Posizione(0,5);
		Pezzo pz = new Alfiere(0,pos);
		assertEquals(0,pz.getColore());
	}

	@Test
	void testsetColore() {
		Posizione pos= new Posizione(0,5);
		Pezzo pz = new Alfiere(0,pos);
		pz.setColore(1);
		assertEquals(1,pz.getColore());
	}
	
	
	//**Simbolo
	@Test
	void testgetSimbolo() {
		Posizione pos= new Posizione(0,5);
		Pezzo pz = new Alfiere(0,pos);
		assertEquals('\u265D',pz.getSimbolo());
	}
	
	@Test
	void testsetSimbolo() {
		Posizione pos= new Posizione(0,5);
		Pezzo pz = new Alfiere(0,pos);
		pz.setSimbolo('\u2657');
		assertEquals('\u2657',pz.getSimbolo());
	}
	
	//**Nome
	@Test
	void testgetNome() {
		Posizione pos= new Posizione(0,5);
		Pezzo pz = new Alfiere(0,pos);
		assertEquals('B',pz.getNome());
	}
	
	@Test
	void testgetNomeDuePezzi() {
		Posizione pos= new Posizione(0,5);
		Pezzo pz1 = new Alfiere(0,pos);
		Pezzo pz2 = new Torre(0,pos);
		assertNotEquals(pz1.getNome(),pz2.getNome());
		
	}
	
	//**Posizione
	@Test
	void testgetPosizione() {
		Posizione pos1= new Posizione(0,5);
		Pezzo pz1 = new Alfiere(0,pos1);
		assertEquals(pos1,pz1.getPosizione());
	}
	
	@Test
	void testsetPosizione() {
		Posizione pos1= new Posizione(0,5);
		Pezzo pz1 = new Alfiere(0,pos1);
		Posizione pos2= new Posizione(2,4);
		pz1.setPosizione(pos2);
		assertTrue(pz1.getPosizione().equals(pos2));
	}
	
	//**Gittata
	@Test
	void testgetGittata() {
		Posizione pos1= new Posizione(0,5);
		Pezzo pz1 = new Alfiere(0,pos1);
		assertFalse(pz1.getGittata()==7);
	}
	
	@Test
	void testsetGittata() {
		Posizione pos1= new Posizione(0,5);
		Pezzo pz1 = new Alfiere(0,pos1);
		pz1.setGittata(9);
		assertTrue(pz1.getGittata()==9);
	}
	
	//**CatturabileE
	@Test
	void testgetCatturabileE() {
		Posizione pos1= new Posizione(0,5);
		Pezzo pz1 = new Alfiere(0,pos1);
		assertFalse(pz1.getCatturabileE());
	}
	
	@Test
	void testsetCatturabileE() {
		Posizione pos1= new Posizione(0,5);
		Pezzo pz1 = new Alfiere(0,pos1);
		pz1.setCatturabileE();
		assertTrue(pz1.getCatturabileE());
	}
	
	//**Enpassant
	@Test
	void testgetEnpassant() {
		Posizione pos1= new Posizione(0,5);
		Pezzo pz1 = new Alfiere(0,pos1);
		assertFalse(pz1.getEnpassant());
	}
	
	@Test
	void testsetEnpassant() {
		Posizione pos1= new Posizione(0,5);
		Pezzo pz1 = new Alfiere(0,pos1);
		pz1.setEmpoissonTrue();
		assertTrue(pz1.getEnpassant());
	}
	
	
	//**GiaMosso
	@Test
	void testGiaMossoEnp() {
		Posizione pos1= new Posizione(0,5);
		Pezzo pz1 = new Alfiere(0,pos1);
		pz1.setEmpoissonTrue();
		pz1.giaMosso();
		assertTrue(!pz1.getEnpassant());
	}
	
	
	
	@Test
  void testGiaMossoNotEnp() throws UnsupportedEncodingException {
    InputStream sysInBackup = System.in; // backup System.in to restore it later
    ByteArrayInputStream in = new ByteArrayInputStream("a4".getBytes());
    System.setIn(in);
    Tabella tabella = new Tabella(8, 8);
    System.setOut(new PrintStream(outContent));
    tabella.muovicondomanda();
    System.setIn(sysInBackup);
    tabella.getTabella(new Posizione(3,0)).giaMosso();
    assertTrue(!tabella.getTabella(new Posizione(3,0)).getCatturabileE());
    System.setOut(originalOut);
  }
	
	
	
}
