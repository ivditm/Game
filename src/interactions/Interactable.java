package interactions;

import java.io.Serializable;

import play.Inventaire;

/**
 * Définit le comportement commun aux éléments avec lesquels le joueur peut
 * interagir dans une zone.
 */
public interface Interactable extends Serializable {

	/**
	 * Retourne le nom affiché de l'élément.
	 *
	 * @return le nom de l'élément interactif
	 */
	default String getNom() {
		return toString();
	}

	/**
	 * Retourne un texte d'inspection de l'élément.
	 *
	 * @return la description détaillée de l'élément
	 */
	default String examiner() {
		return toString();
	}

	/**
	 * Exécute l'action principale liée à l'élément.
	 *
	 * @param inventaire l'inventaire du joueur, si l'interaction en a besoin
	 * @return le résultat textuel de l'interaction
	 */
	default String interagir(Inventaire inventaire) {
		return examiner();
	}
}
