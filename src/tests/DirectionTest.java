package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import jeu.Direction;

/** Tests unitaires de l'énumération Direction. */
class DirectionTest {

	@Test
	final void testValeurs() {
		Direction[] valeurs = Direction.values();
		assertEquals(4, valeurs.length, "Il doit y avoir exactement 4 directions");
	}

	@Test
	final void testNordAbreviation() {
		assertEquals("N", Direction.NORD.getAbreviation());
	}

	@Test
	final void testSudAbreviation() {
		assertEquals("S", Direction.SUD.getAbreviation());
	}

	@Test
	final void testEstAbreviation() {
		assertEquals("E", Direction.EST.getAbreviation());
	}

	@Test
	final void testOuestAbreviation() {
		assertEquals("O", Direction.OUEST.getAbreviation());
	}

	@Test
	final void testNordDescription() {
		assertEquals("N (aller au nord)", Direction.NORD.getDescription());
	}

	@Test
	final void testSudDescription() {
		assertEquals("S (aller au sud)", Direction.SUD.getDescription());
	}

	@Test
	final void testEstDescription() {
		assertEquals("E (aller à l'est)", Direction.EST.getDescription());
	}

	@Test
	final void testOuestDescription() {
		assertEquals("O (aller à l'ouest)", Direction.OUEST.getDescription());
	}

	@Test
	final void testValueOf() {
		assertEquals(Direction.NORD, Direction.valueOf("NORD"));
		assertEquals(Direction.SUD, Direction.valueOf("SUD"));
		assertEquals(Direction.EST, Direction.valueOf("EST"));
		assertEquals(Direction.OUEST, Direction.valueOf("OUEST"));
	}

	@Test
	final void testNonNull() {
		for (Direction d : Direction.values()) {
			assertNotNull(d.getAbreviation());
			assertNotNull(d.getDescription());
		}
	}
}
