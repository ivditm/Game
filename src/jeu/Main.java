package jeu;

import persistance.CompteManager;
import persistance.EtatPartie;
import persistance.SaveManager;
import play.Joueur;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;

/**
 * Point d'entrée du jeu.
 * <p>
 * Cette classe initialise le jeu et son interface graphique Swing.
 * Elle crée une instance de {@link Jeu} et une instance de {@link GUI},
 * puis associe la GUI au jeu.
 * <p>
 * L'initialisation est effectuée dans le thread de dispatching Swing
 * grâce à {@link javax.swing.SwingUtilities#invokeLater(Runnable)},
 * pour respecter les bonnes pratiques de Swing.
 *
 * <pre>
 * Exemple d'exécution :
 *   java Main
 * </pre>
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Joueur joueur = choisirOuCreerCompte();
            if (joueur == null) return; // l'utilisateur a fermé la fenêtre

            SaveManager saveManager = new SaveManager();
            EtatPartie etatSauvegarde = saveManager.chargerPartie(joueur.getPseudo());

            Jeu jeu;
            if (etatSauvegarde != null) {
                int choix = JOptionPane.showConfirmDialog(null,
                        "Partie sauvegardée trouvée. Reprendre ?", "Reprise de partie",
                        JOptionPane.YES_NO_OPTION);
                if (choix == JOptionPane.YES_OPTION) {
                    jeu = new Jeu(joueur, etatSauvegarde);
                } else {
                    saveManager.supprimerSauvegarde(joueur.getPseudo());
                    jeu = new Jeu(joueur);
                }
            } else {
                jeu = new Jeu(joueur);
            }

            GUI gui = new GUI(jeu);
            jeu.setGUI(gui);
        });
    }

    /** Affiche le menu de connexion et retourne le joueur sélectionné, ou null si annulé. */
    private static Joueur choisirOuCreerCompte() {
        CompteManager cm = new CompteManager();
        List<String> comptes = cm.listerComptes();

        List<String> options = new ArrayList<>(comptes);
        options.add("[ Nouveau compte ]");

        String choix = (String) JOptionPane.showInputDialog(null,
                "Sélectionnez votre compte :", "Connexion",
                JOptionPane.QUESTION_MESSAGE, null,
                options.toArray(),
                options.isEmpty() ? "[ Nouveau compte ]" : options.get(0));

        if (choix == null) return null;

        if (choix.equals("[ Nouveau compte ]")) {
            String pseudo = JOptionPane.showInputDialog(null,
                    "Entrez votre pseudo :", "Nouveau compte", JOptionPane.QUESTION_MESSAGE);
            if (pseudo == null || pseudo.isBlank()) return null;
            pseudo = pseudo.trim();
            cm.creerCompte(pseudo);
            return cm.chargerCompte(pseudo);
        }

        return cm.chargerCompte(choix);
    }
}
