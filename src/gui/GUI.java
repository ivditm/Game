package gui;

import commande.Commande;
import commande.CommandeNonDirectionnelle;

import java.io.Serializable;
import java.util.Scanner;

/**
 * Classe GUI - Gère l'interface graphique en mode texte :
 * affichage des textes, des menus et saisie des options du joueur.
 */
public class GUI implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient Scanner scanner;

    public GUI() {
        this.scanner = new Scanner(System.in);
    }

    // -------------------------------------------------------
    // Affichage général
    // -------------------------------------------------------

    /**
     * Affiche un message simple dans la console.
     * @param message le message à afficher
     */
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    /**
     * Affiche un séparateur visuel.
     */
    public void afficherSeparateur() {
        System.out.println("========================================");
    }

    /**
     * Affiche le titre du jeu au lancement.
     */
    public void afficherTitre() {
        afficherSeparateur();
        System.out.println("   BIENVENUE AU JEU DE LA MIAGE   ");
        System.out.println("   Trouvez l'antidote avant qu'il soit trop tard !   ");
        afficherSeparateur();
    }

    /**
     * Affiche le menu principal (nouvelle partie / charger / quitter).
     */
    public void afficherMenuPrincipal() {
        afficherSeparateur();
        System.out.println("[1] Nouvelle partie");
        System.out.println("[2] Charger une partie");
        System.out.println("[3] Quitter");
        afficherSeparateur();
    }

    /**
     * Affiche les commandes disponibles en jeu.
     */
    public void afficherAide() {
        afficherSeparateur();
        System.out.println("COMMANDES DISPONIBLES :");
        System.out.println("  NORD / N       -> Se déplacer vers le nord");
        System.out.println("  SUD  / S       -> Se déplacer vers le sud");
        System.out.println("  EST  / E       -> Se déplacer vers l'est");
        System.out.println("  OUEST / O      -> Se déplacer vers l'ouest");
        System.out.println("  EXAMINER / EX  -> Examiner la salle");
        System.out.println("  OUVRIR / OU    -> Ouvrir un élément");
        System.out.println("  RAMASSER / R   -> Ramasser un objet");
        System.out.println("  INVENTAIRE / I -> Consulter l'inventaire");
        System.out.println("  FABRIQUER / F  -> Fabriquer l'antidote");
        System.out.println("  SAUVEGARDER    -> Sauvegarder la partie");
        System.out.println("  TEST / T       -> Lancer les tests");
        System.out.println("  QUITTER / Q    -> Quitter le jeu");
        afficherSeparateur();
    }

    /**
     * Affiche l'état de la zone actuelle du personnage.
     * @param descriptionZone description textuelle de la zone
     */
    public void afficherZone(String descriptionZone) {
        afficherSeparateur();
        System.out.println("📍 " + descriptionZone);
        afficherSeparateur();
    }

    /**
     * Affiche le contenu de l'inventaire.
     * @param contenuInventaire description textuelle de l'inventaire
     */
    public void afficherInventaire(String contenuInventaire) {
        afficherSeparateur();
        System.out.println("🎒 INVENTAIRE : " + contenuInventaire);
        afficherSeparateur();
    }

    /**
     * Affiche les vies restantes du personnage.
     * @param vies nombre de vies restantes
     */
    public void afficherVies(int vies) {
        System.out.println("❤️  Vies restantes : " + vies);
    }

    /**
     * Affiche un message de victoire.
     */
    public void afficherVictoire() {
        afficherSeparateur();
        System.out.println("🎉 VICTOIRE ! Vous avez fabriqué l'antidote !");
        System.out.println("Vous avez sauvé votre collègue. Bravo !");
        afficherSeparateur();
    }

    /**
     * Affiche un message de défaite.
     */
    public void afficherDefaite() {
        afficherSeparateur();
        System.out.println("💀 DÉFAITE ! Vous avez été rattrapé par le monstre...");
        System.out.println("Meilleure chance la prochaine fois !");
        afficherSeparateur();
    }

    /**
     * Affiche un message d'avertissement (monstre proche).
     */
    public void afficherAvertissement(String message) {
        System.out.println("⚠️  " + message);
    }

    /**
     * Affiche une énigme et ses choix de réponse.
     * @param question  l'intitulé de la question
     * @param choixA    réponse A
     * @param choixB    réponse B
     * @param choixC    réponse C
     * @param choixD    réponse D
     */
    public void afficherEnigme(String question, String choixA, String choixB,
                                String choixC, String choixD) {
        afficherSeparateur();
        System.out.println("💻 ÉNIGME : " + question);
        System.out.println("  A) " + choixA);
        System.out.println("  B) " + choixB);
        System.out.println("  C) " + choixC);
        System.out.println("  D) " + choixD);
        afficherSeparateur();
    }

    // -------------------------------------------------------
    // Saisie utilisateur
    // -------------------------------------------------------

    /**
     * Demande au joueur de saisir son nom.
     * @return le nom saisi
     */
    public String demanderNomJoueur() {
        System.out.print("Entrez votre nom de joueur : ");
        return scanner.nextLine().trim();
    }

    /**
     * Lit et retourne la saisie brute du joueur.
     * @return la saisie en majuscules
     */
    public String lireSaisie() {
        System.out.print("> ");
        return scanner.nextLine().trim().toUpperCase();
    }

    /**
     * Lit la saisie et tente de la convertir en Commande directionnelle.
     * @return la Commande correspondante, ou null si non reconnue
     */
    public Commande lireCommande() {
        String saisie = lireSaisie();
        return Commande.fromString(saisie);
    }

    /**
     * Lit la saisie et tente de la convertir en CommandeNonDirectionnelle.
     * @return la CommandeNonDirectionnelle correspondante, ou null si non reconnue
     */
    public CommandeNonDirectionnelle lireCommandeNonDirectionnelle() {
        String saisie = lireSaisie();
        return CommandeNonDirectionnelle.fromString(saisie);
    }

    /**
     * Lit la réponse à une énigme (A, B, C ou D).
     * @return la lettre saisie en majuscule
     */
    public String lireReponseEnigme() {
        System.out.print("Votre réponse (A/B/C/D) : ");
        return scanner.nextLine().trim().toUpperCase();
    }

    /**
     * Lit le choix dans le menu principal (1, 2 ou 3).
     * @return le choix sous forme d'entier, -1 si invalide
     */
    public int lireChoixMenu() {
        System.out.print("Votre choix : ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Ferme le scanner proprement.
     */
    public void fermer() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
