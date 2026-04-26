package persistance;

import play.Joueur;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Gère la persistance des comptes joueurs en JSON (création, chargement, mise à jour). */
public class CompteManager {

    private static final String DOSSIER_RACINE = "sauvegardes";
    private static final String FICHIER_COMPTE = "compte.json";

    /** Crée un compte et l'enregistre sur disque. Ne fait rien si le pseudo existe déjà. */
    public void creerCompte(String pseudo) {
        if (compteExiste(pseudo)) return;
        getDossierJoueur(pseudo).mkdirs();
        sauvegarderCompte(new Joueur(pseudo));
    }

    /** Charge et retourne le compte du joueur depuis le JSON, ou null s'il est introuvable. */
    public Joueur chargerCompte(String pseudo) {
        File fichier = new File(getDossierJoueur(pseudo), FICHIER_COMPTE);
        if (!fichier.exists()) return null;
        try {
            String json = Files.readString(fichier.toPath(), StandardCharsets.UTF_8);
            Joueur j = new Joueur(getString(json, "pseudo"));
            int victoires = getInt(json, "nbVictoires");
            int defaites  = getInt(json, "nbDefaites");
            for (int i = 0; i < victoires; i++) j.ajouterVictoire();
            for (int i = 0; i < defaites;  i++) j.ajouterDefaite();
            return j;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Sauvegarde (écrase) les données d'un compte dans le fichier JSON. */
    public void sauvegarderCompte(Joueur joueur) {
        getDossierJoueur(joueur.getPseudo()).mkdirs();
        File fichier = new File(getDossierJoueur(joueur.getPseudo()), FICHIER_COMPTE);
        String json = "{\n"
                + "  \"pseudo\": \"" + joueur.getPseudo() + "\",\n"
                + "  \"nbVictoires\": " + joueur.getNbVictoires() + ",\n"
                + "  \"nbDefaites\": " + joueur.getNbDefaites() + "\n"
                + "}";
        try {
            Files.writeString(fichier.toPath(), json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Retourne la liste des pseudos ayant un compte sur disque. */
    public List<String> listerComptes() {
        List<String> comptes = new ArrayList<>();
        File racine = new File(DOSSIER_RACINE);
        if (!racine.exists() || !racine.isDirectory()) return comptes;
        File[] dossiers = racine.listFiles();
        if (dossiers == null) return comptes;
        for (File f : dossiers) {
            if (f.isDirectory() && new File(f, FICHIER_COMPTE).exists()) {
                comptes.add(f.getName());
            }
        }
        return comptes;
    }

    /** Retourne true si un compte existe sur disque pour ce pseudo. */
    public boolean compteExiste(String pseudo) {
        return new File(getDossierJoueur(pseudo), FICHIER_COMPTE).exists();
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
}
