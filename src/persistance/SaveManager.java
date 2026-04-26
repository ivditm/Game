package persistance;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Gère la sauvegarde et le chargement de l'état d'une partie en cours (fichier JSON). */
public class SaveManager {

    private static final String DOSSIER_RACINE = "sauvegardes";
    private static final String FICHIER_PARTIE = "partie.json";

    /** Sauvegarde l'état de la partie dans un fichier JSON. @return true si réussi. */
    public boolean sauvegarderPartie(String pseudo, EtatPartie etat) {
        getDossierJoueur(pseudo).mkdirs();
        File fichier = new File(getDossierJoueur(pseudo), FICHIER_PARTIE);
        String json = "{\n"
                + "  \"nomZone\": \"" + etat.nomZone + "\",\n"
                + "  \"prochainRangAttendu\": " + etat.prochainRangAttendu + ",\n"
                + "  \"recetteObtenue\": " + etat.recetteObtenue + ",\n"
                + "  \"nbRencontres\": " + etat.nbRencontres + ",\n"
                + "  \"tempsRestantMs\": " + etat.tempsRestantMs + "\n"
                + "}";
        try {
            Files.writeString(fichier.toPath(), json, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Charge l'état sauvegardé depuis le JSON. @return l'état ou null si aucune sauvegarde. */
    public EtatPartie chargerPartie(String pseudo) {
        File fichier = new File(getDossierJoueur(pseudo), FICHIER_PARTIE);
        if (!fichier.exists()) return null;
        try {
            String json = Files.readString(fichier.toPath(), StandardCharsets.UTF_8);
            return new EtatPartie(
                    getString(json, "nomZone"),
                    getInt(json, "prochainRangAttendu"),
                    getBoolean(json, "recetteObtenue"),
                    getInt(json, "nbRencontres"),
                    getLong(json, "tempsRestantMs"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Supprime la sauvegarde du joueur (ex : après une fin de partie). */
    public void supprimerSauvegarde(String pseudo) {
        File fichier = new File(getDossierJoueur(pseudo), FICHIER_PARTIE);
        if (fichier.exists()) fichier.delete();
    }

    /** Retourne true si une sauvegarde de partie existe pour ce pseudo. */
    public boolean partieSauvegardeeExiste(String pseudo) {
        return new File(getDossierJoueur(pseudo), FICHIER_PARTIE).exists();
    }

    private File getDossierJoueur(String pseudo) {
        return new File(DOSSIER_RACINE + File.separator + pseudo);
    }

    private static String getString(String json, String key) {
        Matcher m = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]+)\"").matcher(json);
        return m.find() ? m.group(1) : "";
    }

    private static int getInt(String json, String key) {
        Matcher m = Pattern.compile("\"" + key + "\"\\s*:\\s*(-?\\d+)").matcher(json);
        return m.find() ? Integer.parseInt(m.group(1)) : 0;
    }

    private static long getLong(String json, String key) {
        Matcher m = Pattern.compile("\"" + key + "\"\\s*:\\s*(-?\\d+)").matcher(json);
        return m.find() ? Long.parseLong(m.group(1)) : 0L;
    }

    private static boolean getBoolean(String json, String key) {
        Matcher m = Pattern.compile("\"" + key + "\"\\s*:\\s*(true|false)").matcher(json);
        return m.find() && Boolean.parseBoolean(m.group(1));
    }
}
