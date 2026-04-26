package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistance.EtatPartie;
import persistance.SaveManager;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class SaveManagerTest {

    private static final String PSEUDO = "test_save_junit";
    private SaveManager sm;

    @BeforeEach
    void setUp() {
        sm = new SaveManager();
        sm.supprimerSauvegarde(PSEUDO);
    }

    @AfterEach
    void tearDown() {
        sm.supprimerSauvegarde(PSEUDO);
        // Supprime le dossier vide si plus de partie.ser
        new File("sauvegardes" + File.separator + PSEUDO).delete();
    }

    @Test
    void testSauvegarderEtCharger() {
        EtatPartie etat = new EtatPartie("Couloir Central (Bat A)", 3, true, 1, 420000L);
        assertTrue(sm.sauvegarderPartie(PSEUDO, etat));

        EtatPartie chargé = sm.chargerPartie(PSEUDO);
        assertNotNull(chargé);
        assertEquals("Couloir Central (Bat A)", chargé.nomZone);
        assertEquals(3, chargé.prochainRangAttendu);
        assertTrue(chargé.recetteObtenue);
        assertEquals(1, chargé.nbRencontres);
        assertEquals(420000L, chargé.tempsRestantMs);
    }

    @Test
    void testChargerSansSauvegarde() {
        assertNull(sm.chargerPartie(PSEUDO));
    }

    @Test
    void testSupprimerSauvegarde() {
        sm.sauvegarderPartie(PSEUDO, new EtatPartie("Zone", 1, false, 0, 900000L));
        sm.supprimerSauvegarde(PSEUDO);
        assertNull(sm.chargerPartie(PSEUDO));
    }

    @Test
    void testPartieSauvegardeeExiste() {
        assertFalse(sm.partieSauvegardeeExiste(PSEUDO));
        sm.sauvegarderPartie(PSEUDO, new EtatPartie("Zone", 1, false, 0, 900000L));
        assertTrue(sm.partieSauvegardeeExiste(PSEUDO));
    }

    @Test
    void testEcraseSauvegardePrecedente() {
        sm.sauvegarderPartie(PSEUDO, new EtatPartie("Zone A", 1, false, 0, 900000L));
        sm.sauvegarderPartie(PSEUDO, new EtatPartie("Zone B", 2, false, 0, 800000L));
        assertEquals("Zone B", sm.chargerPartie(PSEUDO).nomZone);
    }
}
