package jeu;

import java.io.Serializable;

/**
 * Gère le temps imparti pour la partie en se basant sur l'horloge système.
 */
public class TimeManagement implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Le moment (en millisecondes) où la partie a commencé. */
	private long startTime;

	/** Le temps total alloué pour la partie (en millisecondes). */
	private long intervalleAutorise;

	/**
	 * Initialise le chronomètre.
	 * 
	 * @param minutesInitiales Le temps de base accordé au joueur (en minutes).
	 */
	public TimeManagement(int minutesInitiales) {
		this.startTime = System.currentTimeMillis();
		// Conversion des minutes en millisecondes
		this.intervalleAutorise = (long) minutesInitiales * 60 * 1000;
	}

	/**
	 * Réduit le temps total alloué (Pénalité).
	 * 
	 * @param minutesPenalite Le nombre de minutes à retirer.
	 */
	public void appliquerPenalite(int minutesPenalite) {
		long penaliteMs = (long) minutesPenalite * 60 * 1000;
		this.intervalleAutorise -= penaliteMs;
	}

	/**
	 * Vérifie si le temps est écoulé. (Temps actuel - Temps de départ) > Intervalle
	 * autorisé * @return true si le joueur a perdu au temps.
	 */
	public boolean estTempsEcoule() {
		long tempsEcoule = System.currentTimeMillis() - this.startTime;
		return tempsEcoule >= this.intervalleAutorise;
	}

	/**
	 * Retourne le temps restant sous forme de texte (ex: "12:34"). Très utile pour
	 * le GUI de Rayane.
	 */
	public String getTempsRestantFormate() {
		long tempsEcoule = System.currentTimeMillis() - this.startTime;
		long tempsRestantMs = this.intervalleAutorise - tempsEcoule;

		if (tempsRestantMs <= 0) {
			return "00:00";
		}

		long secondesTotales = tempsRestantMs / 1000;
		long minutes = secondesTotales / 60;
		long secondes = secondesTotales % 60;

		return String.format("%02d:%02d", minutes, secondes);
	}

	/**
	 * Restaure le chronomètre à partir d'un temps restant sauvegardé.
	 *
	 * @param tempsRestantMs temps restant en millisecondes
	 */
	public void restaurer(long tempsRestantMs) {
		this.startTime = System.currentTimeMillis() - (this.intervalleAutorise - tempsRestantMs);
	}

	/** Retourne le temps restant en millisecondes. */
	public long getTempsRestantMs() {
		long tempsEcoule = System.currentTimeMillis() - this.startTime;
		return Math.max(0, this.intervalleAutorise - tempsEcoule);
	}
}