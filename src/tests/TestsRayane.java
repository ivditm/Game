package tests;

import commande.Commande;
import commande.CommandeNonDirectionnelle;
import gui.GUI;
import sauvegarde.GestionnaireSauvegarde;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour les classes de Rayane :
 * Commande, CommandeNonDirectionnelle, GestionnaireSauvegarde
 */
public class TestsRayane {

    private GestionnaireSauvegarde gestionnaire;

    @BeforeEach
    public void setUp() {
        gestionnaire = new GestionnaireSauvegarde();
    }

    // -------------------------------------------------------
    // Tests Commande
    // -------------------------------------------------------

    @Test
    public void testCommandeNord() {
        assertEquals(Commande.NORD, Commande.fromString("NORD"));
    }

    @Test
    public void testCommandeNordRaccourci() {
        assertEquals(Commande.NORD, Commande.fromString("N"));
    }

    @Test
    public void testCommandeSud() {
        assertEquals(Commande.SUD, Commande.fromString("SUD"));
    }

    @Test
    public void testCommandeSudRaccourci() {
        assertEquals(Commande.SUD, Commande.fromString("S"));
    }

    @Test
    public void testCommandeEst() {
        assertEquals(Commande.EST, Commande.fromString("EST"));
    }

    @Test
    public void testCommandeOuest() {
        assertEquals(Commande.OUEST, Commande.fromString("OUEST"));
    }

    @Test
    public void testCommandeOuestRaccourci() {
        assertEquals(Commande.OUEST, Commande.fromString("O"));
    }

    @Test
    public void testCommandeInconnue() {
        assertNull(Commande.fromString("BONJOUR"));
    }

    @Test
    public void testCommandeNull() {
        assertNull(Commande.fromString(null));
    }

    @Test
    public void testCommandeMinuscule() {
        assertEquals(Commande.NORD, Commande.fromString("nord"));
    }

    // -------------------------------------------------------
    // Tests CommandeNonDirectionnelle
    // -------------------------------------------------------

    @Test
    public void testCNDInventaire() {
        assertEquals(CommandeNonDirectionnelle.INVENTAIRE,
                CommandeNonDirectionnelle.fromString("INVENTAIRE"));
    }

    @Test
    public void testCNDInventaireRaccourci() {
        assertEquals(CommandeNonDirectionnelle.INVENTAIRE,
                CommandeNonDirectionnelle.fromString("I"));
    }

    @Test
    public void testCNDSauvegarder() {
        assertEquals(CommandeNonDirectionnelle.SAUVEGARDER,
                CommandeNonDirectionnelle.fromString("SAUVEGARDER"));
    }

    @Test
    public void testCNDQuitter() {
        assertEquals(CommandeNonDirectionnelle.QUITTER,
                CommandeNonDirectionnelle.fromString("QUITTER"));
    }

    @Test
    public void testCNDQuitterRaccourci() {
        assertEquals(CommandeNonDirectionnelle.QUITTER,
                CommandeNonDirectionnelle.fromString("Q"));
    }

    @Test
    public void testCNDFabriquer() {
        assertEquals(CommandeNonDirectionnelle.FABRIQUER,
                CommandeNonDirectionnelle.fromString("F"));
    }

    @Test
    public void testCNDTest() {
        assertEquals(CommandeNonDirectionnelle.TEST,
                CommandeNonDirectionnelle.fromString("TEST"));
    }

    @Test
    public void testCNDInconnue() {
        assertNull(CommandeNonDirectionnelle.fromString("NIMPORTEQUOI"));
    }

    @Test
    public void testCNDNull() {
        assertNull(CommandeNonDirectionnelle.fromString(null));
    }

    @Test
    public void testCNDMinuscule() {
        assertEquals(CommandeNonDirectionnelle.QUITTER,
                CommandeNonDirectionnelle.fromString("quitter"));
    }

    // -------------------------------------------------------
    // Tests GestionnaireSauvegarde
    // -------------------------------------------------------

    @Test
    public void testCreerCompteJoueur() {
        gestionnaire.creerCompte("TestJoueur");
        assertTrue(gestionnaire.joueurExiste("TestJoueur"));
        // Nettoyage
        supprimerDossier(new File("sauvegardes/TestJoueur"));
    }

    @Test
    public void testJoueurNExistePas() {
        assertFalse(gestionnaire.joueurExiste("JoueurInexistant999"));
    }

    @Test
    public void testAucunePartieSauvegardee() {
        gestionnaire.creerCompte("TestJoueur2");
        assertFalse(gestionnaire.partieSauvegardeExiste("TestJoueur2"));
        supprimerDossier(new File("sauvegardes/TestJoueur2"));
    }

    @Test
    public void testSauvegardeEtChargement() {
        gestionnaire.creerCompte("TestJoueur3");

        // On sauvegarde un objet simple (String simulant un Personnage)
        String fakePersonnage = "ZoneCourante:BatimentA;Inventaire:ingredient1";
        gestionnaire.sauvegarderPartie("TestJoueur3", fakePersonnage);

        assertTrue(gestionnaire.partieSauvegardeExiste("TestJoueur3"));

        Object charge = gestionnaire.chargerPartie("TestJoueur3");
        assertNotNull(charge);
        assertEquals(fakePersonnage, charge);

        supprimerDossier(new File("sauvegardes/TestJoueur3"));
    }

    @Test
    public void testSupprimerSauvegarde() {
        gestionnaire.creerCompte("TestJoueur4");
        gestionnaire.sauvegarderPartie("TestJoueur4", "donnee");
        gestionnaire.supprimerSauvegarde("TestJoueur4");
        assertFalse(gestionnaire.partieSauvegardeExiste("TestJoueur4"));
        supprimerDossier(new File("sauvegardes/TestJoueur4"));
    }

    @Test
    public void testListerJoueurs() {
        gestionnaire.creerCompte("TestJoueur5");
        List<String> joueurs = gestionnaire.listerJoueurs();
        assertTrue(joueurs.contains("TestJoueur5"));
        supprimerDossier(new File("sauvegardes/TestJoueur5"));
    }

    @Test
    public void testChargerPartieSansExistant() {
        Object result = gestionnaire.chargerPartie("JoueurFantome999");
        assertNull(result);
    }

    // -------------------------------------------------------
    // Utilitaire nettoyage
    // -------------------------------------------------------

    /**
     * Supprime un dossier et son contenu (pour nettoyer après les tests).
     */
    private void supprimerDossier(File dossier) {
        if (dossier.exists()) {
            for (File f : dossier.listFiles()) {
                f.delete();
            }
            dossier.delete();
        }
    }
}
