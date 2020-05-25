package it.uniba.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.uniba.main.Alfiere;
import it.uniba.main.Cavallo;
import it.uniba.main.Donna;
import it.uniba.main.Pedone;
import it.uniba.main.Pezzo;
import it.uniba.main.Posizione;
import it.uniba.main.Tabella;



public class TabellaTest {



@BeforeAll
	static void setUpAll() {
		System.out.println("Inizio dei test della classe Tabella");
	}

	@AfterAll
	static void tearDownAll() {
		System.out.println("Fine dei test della classe Tabella");
	}

	// **Getrighe
	@Test
	@DisplayName("Testing getrighe")
	public void testGetRighe() {
		Tabella tabella = new Tabella(8, 8);
		assertEquals(8, tabella.getRighe());
	}

	
	@Test
	public void testWrongValGetRighe() {
		Tabella tabella = new Tabella(8, 8);
		assertNotEquals(7, tabella.getRighe());
	}

	// **Getcolonne
	@Test
	public void testGetColonne() {
		Tabella tabella = new Tabella(8, 8);
		assertEquals(8, tabella.getColonne());
	}

	
	@Test
	public void testWrongValGetColonne() {
		Tabella tabella = new Tabella(8, 8);
		assertNotEquals(0, tabella.getColonne());
	}
	

	
	//**getTabella
	@Test
	void testgetTebella() { 
		Tabella tabella = new Tabella(8, 8);
		Posizione pos= new Posizione(0,5);
		Posizione toGo= new Posizione(1,4);
		Pezzo pz = new Alfiere(0,pos);
		tabella.setTabella(pz,toGo);
		pz.setPosizione(toGo);
		assertTrue(tabella.getTabella(toGo).equals(pz));
	}
	
	
	//**move
	@Test
	void testmove() { 
		Tabella tabella = new Tabella(8, 8);
		Posizione pos= new Posizione(0,5);
		Pezzo pz = new Alfiere(0,pos);
		Posizione toGo= new Posizione(1,4);
		tabella.move(toGo, pz);
		assertTrue(tabella.getTabella(toGo).equals(pz));
	}
	
	@Test
	void testmoveEMangiaPedina() { 
		Tabella tabella = new Tabella(8, 8);
		Posizione pos1= new Posizione(4,4);
		Posizione pos2= new Posizione(5,5);
		Pezzo pz1 = new Alfiere(0,pos1);
		Pezzo pz2 = new Alfiere(1,pos2);
		Posizione toGo= new Posizione(5,5);
		tabella.move(toGo, pz1);
		assertTrue(!pz1.getEnpassant());

	}
	
	@Test
	void testmoveEMangiaPedinaConEnpassant() { 
		Tabella tabella = new Tabella(8, 8);
		Posizione pos1= new Posizione(4,3);
		Posizione pos2= new Posizione(4,4);
		tabella.move(pos1, tabella.getTabella(new Posizione(1,1)));
		tabella.move(pos2, tabella.getTabella(new Posizione(6,1)));
		Posizione toGo= new Posizione(5,4);
		tabella.move(toGo, tabella.getTabella(pos1));
		assertTrue(tabella.getTabella(toGo).getColore() == 0);		
	}
	
	
	
	
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	

	
	@Test
	void teststampaturnobianco() {
		Tabella tabella = new Tabella(8, 8);
		//tabella.cambiaTurno();
		System.setOut(new PrintStream(outContent));
		tabella.stampaTurno();
		assertTrue("muove il bianco:\n".equals(outContent.toString()));
	    System.setOut(originalOut);
	}
	
	@Test
	void teststampaturnonero() {
		Tabella tabella = new Tabella(8, 8);
		tabella.cambiaTurno();
		System.setOut(new PrintStream(outContent));
		tabella.stampaTurno();
		assertTrue("muove il nero:\n".equals(outContent.toString()));
	    System.setOut(originalOut);
	}


	// **MuoviConDomanda
	@Test
	public void testmuovicondomandaPedoneBiancoNormale() throws UnsupportedEncodingException {
		InputStream sysInBackup = System.in; // backup System.in to restore it later
		ByteArrayInputStream in = new ByteArrayInputStream("a4".getBytes());
		System.setIn(in);
		System.setOut(new PrintStream(outContent));
		Tabella tabella = new Tabella(8, 8);
		tabella.muovicondomanda();
		assertTrue("Pezzo Mosso\n".equals(outContent.toString()));	
		System.setIn(sysInBackup);
		System.setOut(originalOut);
	}
	
	@Test
	public void testmuovicondomandaPedoneBiancoMangia() throws UnsupportedEncodingException {
		InputStream sysInBackup = System.in; // backup System.in to restore it later
		ByteArrayInputStream in = new ByteArrayInputStream("axb3".getBytes());
		System.setIn(in);
		System.setOut(new PrintStream(outContent));
		Tabella tabella = new Tabella(8, 8);
		Pezzo pd = new Pedone(1,new Posizione(1,2));
		tabella.setTabella(pd, pd.getPosizione());
		tabella.muovicondomanda();
		Posizione finalpos = new Posizione(1,2);
		assertEquals(tabella.getTabella(finalpos).getNome(),'p');	
		System.setIn(sysInBackup);
		System.setOut(originalOut);
	}
	
	@Test
	public void testmuovicondomandaPedoneNeroMangia() throws UnsupportedEncodingException {
		InputStream sysInBackup = System.in; // backup System.in to restore it later
		ByteArrayInputStream in = new ByteArrayInputStream("axb6".getBytes());
		System.setIn(in);
		System.setOut(new PrintStream(outContent));
		Tabella tabella = new Tabella(8, 8);
		Pezzo pd = new Pedone(0,new Posizione(5,1));
		tabella.setTabella(pd, pd.getPosizione());
		tabella.cambiaTurno();
		tabella.muovicondomanda();
		Posizione finalpos = new Posizione(5,1);
		assertEquals(tabella.getTabella(finalpos).getNome(),'p');	
		System.setIn(sysInBackup);
		System.setOut(originalOut);
	}
	
	
	@Test
	public void testmuovicondomandaPedoneNeroNormale() throws UnsupportedEncodingException {
		InputStream sysInBackup = System.in; // backup System.in to restore it later
		ByteArrayInputStream in = new ByteArrayInputStream("a6".getBytes());
		System.setIn(in);
		System.setOut(new PrintStream(outContent));
		Tabella tabella = new Tabella(8, 8);
		tabella.cambiaTurno();
		tabella.muovicondomanda();
		assertTrue("Pezzo Mosso\n".equals(outContent.toString()));	
		System.setIn(sysInBackup);
		System.setOut(originalOut);
	}
	
	@Test
	public void testmuovicondomandaArroccoBiancoLungo() throws UnsupportedEncodingException {
		InputStream sysInBackup = System.in; // backup System.in to restore it later
		ByteArrayInputStream in = new ByteArrayInputStream("O-O-O".getBytes());
		System.setIn(in);
		System.setOut(new PrintStream(outContent));
		Tabella tabella = new Tabella(8, 8);
		//vengono tolti i pezzi
		Posizione attuale= new Posizione(0,3);
		 tabella.setTabella(null, attuale);
		
		 attuale = new Posizione(0,1);
		 tabella.setTabella(null, attuale);

		 attuale = new Posizione(0,2);
		 tabella.setTabella(null, attuale);
				
		tabella.muovicondomanda();
		assertTrue("Pezzo Mosso\n".equals(outContent.toString()));	
		System.setIn(sysInBackup);
		System.setOut(originalOut);
	}
	
	
	@Test
	public void testmuovicondomandaArroccoBiancoCorto() throws UnsupportedEncodingException {
		InputStream sysInBackup = System.in; // backup System.in to restore it later
		ByteArrayInputStream in = new ByteArrayInputStream("O-O".getBytes());
		System.setIn(in);
		System.setOut(new PrintStream(outContent));
		Tabella tabella = new Tabella(8, 8);
		//vengono tolti i pezzi
		Posizione attuale = new Posizione(0,6);
		 tabella.setTabella(null, attuale);
		
		 attuale = new Posizione(0,5);
		 tabella.setTabella(null, attuale);
				
		tabella.muovicondomanda();
		assertTrue("Pezzo Mosso\n".equals(outContent.toString()));	
		System.setIn(sysInBackup);
		System.setOut(originalOut);
	}
	
	
	@Test
	public void testmuovicondomandaArroccoNeroLungo() throws UnsupportedEncodingException {
		InputStream sysInBackup = System.in; // backup System.in to restore it later
		ByteArrayInputStream in = new ByteArrayInputStream("O-O-O".getBytes());
		System.setIn(in);
		System.setOut(new PrintStream(outContent));
		Tabella tabella = new Tabella(8, 8);
		tabella.cambiaTurno();
		//vengono tolti i pezzi
		Posizione attuale= new Posizione(7,3);
		 tabella.setTabella(null, attuale);
		
		 attuale = new Posizione(7,1);
		 tabella.setTabella(null, attuale);

		 attuale = new Posizione(7,2);
		 tabella.setTabella(null, attuale);
				
		tabella.muovicondomanda();
		assertTrue("Pezzo Mosso\n".equals(outContent.toString()));	
		System.setIn(sysInBackup);
		System.setOut(originalOut);
	}
	
	
	@Test
	public void testmuovicondomandaArroccoNeroCorto() throws UnsupportedEncodingException {
		InputStream sysInBackup = System.in; // backup System.in to restore it later
		ByteArrayInputStream in = new ByteArrayInputStream("O-O".getBytes());
		System.setIn(in);
		System.setOut(new PrintStream(outContent));
		Tabella tabella = new Tabella(8, 8);
		tabella.cambiaTurno();
		//vengono tolti i pezzi
		Posizione attuale = new Posizione(7,6);
		 tabella.setTabella(null, attuale);
		
		 attuale = new Posizione(7,5);
		 tabella.setTabella(null, attuale);
				
		tabella.muovicondomanda();
		assertTrue("Pezzo Mosso\n".equals(outContent.toString()));	
		System.setIn(sysInBackup);
		System.setOut(originalOut);
	}
	
	@Test
	public void testmuovicondomandaComandoHelp() throws UnsupportedEncodingException {
		InputStream sysInBackup = System.in; // backup System.in to restore it later
		ByteArrayInputStream in = new ByteArrayInputStream("help".getBytes());
		System.setIn(in);
		System.setOut(new PrintStream(outContent));
		Tabella tabella = new Tabella(8, 8);
		tabella.muovicondomanda();
		assertTrue(("help      stampa elenco comandi\n"+"board     mostra la schacchiera\n"+"captures  mostra i pezzi mangiati dell'avversario\n"+"moves     mostra le mosse effettuate finora\n"+"quit      esci dal gioco\n"+"play      inizia una nuova partita\n").equals(outContent.toString()));	
		System.setIn(sysInBackup);
		System.setOut(originalOut);
	}
	
	
	@Test
	public void testmuovicondomandaComandoMoves() throws UnsupportedEncodingException {
		InputStream sysInBackup = System.in; // backup System.in to restore it later
		ByteArrayInputStream in = new ByteArrayInputStream("moves".getBytes());
		System.setIn(in);
		System.setOut(new PrintStream(outContent));
		Tabella tabella = new Tabella(8, 8);
		tabella.aggiungiComando("a4");
		tabella.muovicondomanda();
	    assertTrue(("a4"+ ";  ").equals(outContent.toString()));	
		System.setIn(sysInBackup);
		System.setOut(originalOut);
	}
	
	//**restart
	@Test
	public void testmuovicondomandaComandoPlayRestart() throws UnsupportedEncodingException {
		InputStream sysInBackup = System.in; // backup System.in to restore it later
		ByteArrayInputStream in = new ByteArrayInputStream("play".getBytes());
		System.setIn(in);
		System.setOut(new PrintStream(outContent));
		Tabella tabella = new Tabella(8, 8);
		tabella.muovicondomanda();
	    assertTrue(("nuova partita iniziata, "
	            + "inserire help per vededere elenco comandi disponibili\n").equals(outContent.toString()));	
		System.setIn(sysInBackup);
		System.setOut(originalOut);
	}
	

	//**test con move di pezzi specifici
		@Test
		public void testmuovicondomandaCavalloBiancoNormale() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Cc3".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(2,2);
			assertEquals(tabella.getTabella(finalpos).getNome(),'N');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
	
		@Test
		public void testmuovicondomandaTorreBiancoSu() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Ta2".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.setTabella(null, new Posizione(1,0));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(1,0);
			assertEquals(tabella.getTabella(finalpos).getNome(),'R');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaTorreBiancoDx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Tb1".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.setTabella(null, new Posizione(0,1));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(0,1);
			assertEquals(tabella.getTabella(finalpos).getNome(),'R');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaTorreBiancoSx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Tg1".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.setTabella(null, new Posizione(0,6));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(0,6);
			assertEquals(tabella.getTabella(finalpos).getNome(),'R');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaTorreBiancoGiu() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Tf4".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(4,5), tabella.getTabella(new Posizione(0,0)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,5);
			assertEquals(tabella.getTabella(finalpos).getNome(),'R');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaAlfiereBiancoSuSx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Ab2".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.setTabella(null, new Posizione(1,1));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(1,1);
			assertEquals(tabella.getTabella(finalpos).getNome(),'B');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaAlfiereBiancoSuDx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Ad2".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.setTabella(null, new Posizione(1,3));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(1,3);
			assertEquals(tabella.getTabella(finalpos).getNome(),'B');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaAlfiereBiancoGiuSx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Ae4".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(4,5), tabella.getTabella(new Posizione(0,2)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,4);
			assertEquals(tabella.getTabella(finalpos).getNome(),'B');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaAlfiereBiancoGiuDx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Ag4".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(4,5), tabella.getTabella(new Posizione(0,2)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,6);
			assertEquals(tabella.getTabella(finalpos).getNome(),'B');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaDonnaBiancoSu() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Dd2".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.setTabella(null, new Posizione(1,3));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(1,3);
			assertEquals(tabella.getTabella(finalpos).getNome(),'Q');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaDonnaBiancoDx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("De1".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.setTabella(null, new Posizione(0,4));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(0,4);
			assertEquals(tabella.getTabella(finalpos).getNome(),'Q');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaDonnaBiancoSx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Dc1".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.setTabella(null, new Posizione(0,2));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(0,2);
			assertEquals(tabella.getTabella(finalpos).getNome(),'Q');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaDonnaBiancoGiu() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Df4".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(4,5), tabella.getTabella(new Posizione(0,3)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,5);
			assertEquals(tabella.getTabella(finalpos).getNome(),'Q');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		
		@Test
		public void testmuovicondomandaDonnaBiancoSuSx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Dc2".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.setTabella(null, new Posizione(1,2));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(1,2);
			assertEquals(tabella.getTabella(finalpos).getNome(),'Q');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaDonnaBiancoSuDx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("De2".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.setTabella(null, new Posizione(1,4));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(1,4);
			assertEquals(tabella.getTabella(finalpos).getNome(),'Q');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaDonnaBiancoGiuSx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("De4".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(4,5), tabella.getTabella(new Posizione(0,3)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,4);
			assertEquals(tabella.getTabella(finalpos).getNome(),'Q');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaDonnaBiancoGiuDx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Dg4".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(4,5), tabella.getTabella(new Posizione(0,3)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,6);
			assertEquals(tabella.getTabella(finalpos).getNome(),'Q');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		
		
		
		@Test
		public void testmuovicondomandaReBiancoSu() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Re2".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.setTabella(null, new Posizione(1,4));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(1,4);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaReBiancoDx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rf1".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.setTabella(null, new Posizione(0,5));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(0,5);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaReBiancoSx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rd1".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.setTabella(null, new Posizione(0,3));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(0,3);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaReBiancoGiu() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rf4".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(4,5), tabella.getTabella(new Posizione(0,4)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,5);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testAmbiguitaTorreStessaRiga() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Tad6".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(5,0), tabella.getTabella(new Posizione(7,0)));
			tabella.move(new Posizione(5,7), tabella.getTabella(new Posizione(7,7)));
			tabella.cambiaTurno();
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(5,3);
			assertEquals(tabella.getTabella(finalpos).getNome(),'R');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}

		@Test
		public void testAmbiguitaTorreStessaRigaMangia() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Taxd6".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			Pezzo pz = new Pedone(0,new Posizione(5,3));
			tabella.setTabella(pz, pz.getPosizione());
			tabella.move(new Posizione(5,0), tabella.getTabella(new Posizione(7,0)));
			tabella.move(new Posizione(5,7), tabella.getTabella(new Posizione(7,7)));
			tabella.cambiaTurno();
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(5,3);
			assertEquals(tabella.getTabella(finalpos).getNome(),'R');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testAmbiguitaTorreStessaColonna() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("T6a5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(5,0), tabella.getTabella(new Posizione(7,0)));
			tabella.move(new Posizione(3,0), tabella.getTabella(new Posizione(7,7)));
			tabella.cambiaTurno();
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(4,0);
			assertEquals(tabella.getTabella(finalpos).getNome(),'R');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testAmbiguitaTorreStessaColonnaMangia() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("T6xa5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			Pezzo pz = new Pedone(0,new Posizione(4,0));
			tabella.setTabella(pz, pz.getPosizione());
			tabella.move(new Posizione(5,0), tabella.getTabella(new Posizione(7,0)));
			tabella.move(new Posizione(3,0), tabella.getTabella(new Posizione(7,7)));
			tabella.cambiaTurno();
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(4,0);
			assertEquals(tabella.getTabella(finalpos).getNome(),'R');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		
		@Test
		public void testAmbiguitaTorreStessaCasella() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Tah6".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(5,0), tabella.getTabella(new Posizione(7,0)));
			tabella.move(new Posizione(3,7), tabella.getTabella(new Posizione(7,7)));
			tabella.cambiaTurno();
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(5,7);
			assertEquals(tabella.getTabella(finalpos).getNome(),'R');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testAmbiguitaTorreStessaCasella2() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Thh6".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(5,0), tabella.getTabella(new Posizione(7,0)));
			tabella.move(new Posizione(3,7), tabella.getTabella(new Posizione(7,7)));
			tabella.cambiaTurno();
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(5,7);
			assertEquals(tabella.getTabella(finalpos).getNome(),'R');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testAmbiguitaTorreStessaCasellaMangia() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Taxh6".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			Pezzo pz = new Pedone(0,new Posizione(5,7));
			tabella.setTabella(pz, pz.getPosizione());
			tabella.move(new Posizione(5,0), tabella.getTabella(new Posizione(7,0)));
			tabella.move(new Posizione(3,7), tabella.getTabella(new Posizione(7,7)));
			tabella.cambiaTurno();
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(5,7);
			assertEquals(tabella.getTabella(finalpos).getNome(),'R');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testAmbiguitaTorreStessaCasellaMangia2() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Thxh6".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			Pezzo pz = new Pedone(0,new Posizione(5,7));
			tabella.setTabella(pz, pz.getPosizione());
			tabella.move(new Posizione(5,0), tabella.getTabella(new Posizione(7,0)));
			tabella.move(new Posizione(3,7), tabella.getTabella(new Posizione(7,7)));
			tabella.cambiaTurno();
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(5,7);
			assertEquals(tabella.getTabella(finalpos).getNome(),'R');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testAmbiguitaCavalliStessaRiga() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Cab5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(2,0), tabella.getTabella(new Posizione(0,1)));
			tabella.move(new Posizione(2,2), tabella.getTabella(new Posizione(0,6)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(4,1);
			assertEquals(tabella.getTabella(finalpos).getNome(),'N');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testAmbiguitaCavalliStessaRigaMangia() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Caxb5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			Pezzo pz = new Pedone(1,new Posizione(4,1));
			tabella.setTabella(pz, pz.getPosizione());
			tabella.move(new Posizione(2,0), tabella.getTabella(new Posizione(0,1)));
			tabella.move(new Posizione(2,2), tabella.getTabella(new Posizione(0,6)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(4,1);
			assertEquals(tabella.getTabella(finalpos).getNome(),'N');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testAmbiguitaCavalliStessaColonna() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("C3a4".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(2,2), tabella.getTabella(new Posizione(0,1)));
			tabella.move(new Posizione(4,2), tabella.getTabella(new Posizione(0,6)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,0);
			assertEquals(tabella.getTabella(finalpos).getNome(),'N');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testAmbiguitaCavalliStessaColonnaMangia() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("C3xa4".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			Pezzo pz = new Pedone(1,new Posizione(0,3));
			tabella.setTabella(pz, pz.getPosizione());
			tabella.move(new Posizione(2,2), tabella.getTabella(new Posizione(0,1)));
			tabella.move(new Posizione(4,2), tabella.getTabella(new Posizione(0,6)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,0);
			assertEquals(tabella.getTabella(finalpos).getNome(),'N');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testAmbiguitaCavalliStessaCasella() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Cbc5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(2,1), tabella.getTabella(new Posizione(0,1)));
			tabella.setTabella(null,new Posizione(6,3));
			tabella.move(new Posizione(6,3), tabella.getTabella(new Posizione(0,6)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(4,2);
			assertEquals(tabella.getTabella(finalpos).getNome(),'N');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testAmbiguitaCavalliStessaCasellaMangia() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Cbxc5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(2,1), tabella.getTabella(new Posizione(0,1)));
			tabella.setTabella(null,new Posizione(6,3));
			tabella.move(new Posizione(6,3), tabella.getTabella(new Posizione(0,6)));
			Pezzo pz = new Pedone(1,new Posizione(4,2));
			tabella.setTabella(pz, pz.getPosizione());
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(4,2);
			assertEquals(tabella.getTabella(finalpos).getNome(),'N');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
	//**antiscacco re
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoGiu() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Ra3".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(3,0), tabella.getTabella(new Posizione(0,4)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(2,0);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoDx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rb4".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(3,0), tabella.getTabella(new Posizione(0,4)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,1);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoSu() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Ra5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(3,0), tabella.getTabella(new Posizione(0,4)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(4,0);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoSx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rh4".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(3,7), tabella.getTabella(new Posizione(0,4)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,7);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoDxGiu() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rb3".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(3,0), tabella.getTabella(new Posizione(0,4)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(2,1);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoDxSu() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rb5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(3,0), tabella.getTabella(new Posizione(0,4)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(4,1);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoSxGiu() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rg3".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(3,7), tabella.getTabella(new Posizione(0,4)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(2,6);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoSxSu() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rg5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(3,7), tabella.getTabella(new Posizione(0,4)));
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(4,6);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoAlfiereEDonna() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rd4".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(3,7), tabella.getTabella(new Posizione(0,4)));
			tabella.move(new Posizione(2,3), tabella.getTabella(new Posizione(7,2)));

			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,7);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoAlfiereEDonnaInBasso() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Re5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(4,5), tabella.getTabella(new Posizione(0,4)));
			tabella.move(new Posizione(2,2), tabella.getTabella(new Posizione(7,3)));

			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(4,5);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoNotAlfiereEDonna() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rc4".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(3,3), tabella.getTabella(new Posizione(0,4)));
			tabella.move(new Posizione(5,0), tabella.getTabella(new Posizione(6,0)));

			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,2);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoNotAlfiereEDonnaInBasso() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rf4".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(3,4), tabella.getTabella(new Posizione(0,4)));
			tabella.move(new Posizione(5,7), tabella.getTabella(new Posizione(6,3)));

			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,5);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoReSu() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rf5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(5,5), tabella.getTabella(new Posizione(7,4)));
			tabella.move(new Posizione(3,5), tabella.getTabella(new Posizione(0,4)));

			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,5);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoReSuDx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Re5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(5,5), tabella.getTabella(new Posizione(7,4)));
			tabella.move(new Posizione(3,3), tabella.getTabella(new Posizione(0,4)));

			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,3);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoReSuSx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rg5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(5,5), tabella.getTabella(new Posizione(7,4)));
			tabella.move(new Posizione(5,7), tabella.getTabella(new Posizione(0,4)));

			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(5,7);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoReGiu() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rf5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(3,5), tabella.getTabella(new Posizione(7,4)));
			tabella.move(new Posizione(5,5), tabella.getTabella(new Posizione(0,4)));

			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(5,5);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoReGiuDx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Re5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(3,3), tabella.getTabella(new Posizione(7,4)));
			tabella.move(new Posizione(5,5), tabella.getTabella(new Posizione(0,4)));

			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(5,5);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoReGiuSx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rg5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(5,7), tabella.getTabella(new Posizione(7,4)));
			tabella.move(new Posizione(5,5), tabella.getTabella(new Posizione(0,4)));

			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(5,5);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoReBordoDx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rh5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(5,7), tabella.getTabella(new Posizione(7,4)));
			tabella.move(new Posizione(3,7), tabella.getTabella(new Posizione(0,4)));

			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,7);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoReBordoDxSuSx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rg5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(5,5), tabella.getTabella(new Posizione(7,4)));
			tabella.move(new Posizione(3,7), tabella.getTabella(new Posizione(0,4)));

			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(3,7);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoReGiuBordoDx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rh4".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(2,7), tabella.getTabella(new Posizione(7,4)));
			tabella.move(new Posizione(4,7), tabella.getTabella(new Posizione(0,4)));

			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(4,7);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoReGiuBordoDxGiu() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rg4".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(2,5), tabella.getTabella(new Posizione(7,4)));
			tabella.move(new Posizione(4,7), tabella.getTabella(new Posizione(0,4)));

			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(4,7);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoReSxBordoDx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rg5".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.move(new Posizione(4,5), tabella.getTabella(new Posizione(7,4)));
			tabella.move(new Posizione(4,7), tabella.getTabella(new Posizione(0,4)));

			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(4,7);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoReBordoSu() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Re7".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.setTabella(null, new Posizione(6,4));
			tabella.move(new Posizione(5,4), tabella.getTabella(new Posizione(0,4)));
            tabella.cambiaTurno();
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(5,4);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoReBordoSuDx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rf8".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.setTabella(null, new Posizione(7,5));
			tabella.setTabella(null, new Posizione(7,6));
			tabella.move(new Posizione(7,6), tabella.getTabella(new Posizione(0,4)));
            tabella.cambiaTurno();
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(7,4);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoReBordoSuSx() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rd8".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.setTabella(null, new Posizione(7,3));
			tabella.setTabella(null, new Posizione(7,2));
			tabella.move(new Posizione(7,2), tabella.getTabella(new Posizione(0,4)));
            tabella.cambiaTurno();
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(7,4);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
		@Test
		public void testmuovicondomandaReBiancoAntiscaccoReBordoSuSxGiu() throws UnsupportedEncodingException {
			InputStream sysInBackup = System.in; // backup System.in to restore it later
			ByteArrayInputStream in = new ByteArrayInputStream("Rd7".getBytes());
			System.setIn(in);
			System.setOut(new PrintStream(outContent));
			Tabella tabella = new Tabella(8, 8);
			tabella.setTabella(null, new Posizione(6,3));
			tabella.move(new Posizione(5,2), tabella.getTabella(new Posizione(0,4)));
            tabella.cambiaTurno();
			tabella.muovicondomanda();
			Posizione finalpos = new Posizione(7,4);
			assertEquals(tabella.getTabella(finalpos).getNome(),'K');	
			System.setIn(sysInBackup);
			System.setOut(originalOut);
		}
		
}