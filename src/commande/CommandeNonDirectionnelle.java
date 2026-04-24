package commande;

import java.io.Serializable;

/**
 * Énumération représentant les commandes non directionnelles du joueur.
 */
public enum CommandeNonDirectionnelle implements Serializable {
    INVENTAIRE,
    SAUVEGARDER,
    EXAMINER,
    OUVRIR,
    RAMASSER,
    FABRIQUER,
    TEST,
    QUITTER;

    /**
     * Convertit une chaîne de caractères en CommandeNonDirectionnelle.
     * @param input la saisie du joueur
     * @return la CommandeNonDirectionnelle correspondante, ou null si non reconnue
     */
    public static CommandeNonDirectionnelle fromString(String input) {
        if (input == null) return null;
        switch (input.trim().toUpperCase()) {
            case "INVENTAIRE": case "I":  return INVENTAIRE;
            case "SAUVEGARDER": case "S": return SAUVEGARDER;
            case "EXAMINER": case "EX":   return EXAMINER;
            case "OUVRIR": case "OU":     return OUVRIR;
            case "RAMASSER": case "R":    return RAMASSER;
            case "FABRIQUER": case "F":   return FABRIQUER;
            case "TEST": case "T":        return TEST;
            case "QUITTER": case "Q":     return QUITTER;
            default: return null;
        }
    }
}
