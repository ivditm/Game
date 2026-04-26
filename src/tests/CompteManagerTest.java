package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistance.CompteManager;
import play.Joueur;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CompteManagerTest {

    private static final String PSEUDO = "test_compte_junit";
    private CompteManager cm;

    @BeforeEach
    void setUp() {
        cm = new CompteManager();
        nettoyerDossierTest();
    }

    @AfterEach
    void tearDown() {
        nettoyerDossierTest();
    }

    @Test
    void testCreerEtChargerCompte() {
        cm.creerCompte(PSEUDO);
        Joueur j = cm.chargerCompte(PSEUDO);
        assertNotNull(j);
        assertEquals(PSEUDO, j.getPseudo());
        assertEquals(0, j.getNbVictoires());
        assertEquals(0, j.getNbDefaites());
    }

    @Test
    void testCompteExisteAvantEtApresCreation() {
        assertFalse(cm.compteExiste(PSEUDO));
        cm.creerCompte(PSEUDO);
        assertTrue(cm.compteExiste(PSEUDO));
    }

    @Test
    void testCreerComptePasDupliquer() {
        cm.creerCompte(PSEUDO);
        // On simule une victoire et on sauvegarde
        Joueur j = cm.chargerCompte(PSEUDO);
        j.ajouterVictoire();
        cm.sauvegarderCompte(j);
        // Un second appel ne doit pas écraser le compte existant
        cm.creerCompte(PSEUDO);
        assertEquals(1, cm.chargerCompte(PSEUDO).getNbVictoires());
    }

    @Test
    void testListerComptes() {
        cm.creerCompte(PSEUDO);
        assertTrue(cm.listerComptes().contains(PSEUDO));
    }

    @Test
    void testSauvegarderEtRecharglerStats() {
        cm.creerCompte(PSEUDO);
        Joueur j = cm.chargerCompte(PSEUDO);
        j.ajouterVictoire();
        j.ajouterVictoire();
        j.ajouterDefaite();
        cm.sauvegarderCompte(j);

        Joueur rechargé = cm.chargerCompte(PSEUDO);
        assertEquals(2, rechargé.getNbVictoires());
        assertEquals(1, rechargé.getNbDefaites());
    }

    @Test
    void testChargerComptePseudoInconnu() {
        assertNull(cm.chargerCompte("pseudo_qui_nexiste_vraiment_pas"));
    }

    /** Supprime le dossier de test sur disque. */
    private void nettoyerDossierTest() {
        File dossier = new File("sauvegardes" + File.separator + PSEUDO);
        if (dossier.exists() && dossier.isDirectory()) {
            for (File f : dossier.listFiles()) f.delete();
            dossier.delete();
        }
    }
}
