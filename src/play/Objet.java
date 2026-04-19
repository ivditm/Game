/**
 * 
 */
package play;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe abstraite représentant un objet générique dans le jeu.
 * <p>
 * Cette classe sert de base (classe mère) pour tous les éléments qui peuvent
 * être manipulés, examinés ou ajoutés à l'inventaire du personnage (par
 * exemple, les ingrédients ou les documents). Elle implémente
 * {@code Serializable} pour permettre la sauvegarde de l'état du jeu.
 * </p>
 */
public abstract class Objet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4428485099355404540L;

	/** Le nom usuel de l'objet. */
	private String nom;

	/** Une description détaillée de l'objet pour donner du contexte au joueur. */
	private String description;

	private TypeObjet type;

	/**
	 * Construit un nouvel objet avec un nom et une description spécifiques.
	 * * @param nom Le nom de l'objet (ex: "Poudre stabilisatrice", "Recette").
	 * 
	 * @param description La description textuelle de l'objet, affichée lors de son
	 *                    examen.
	 */
	public Objet(String nom, String description, TypeObjet type) {
		this.nom = nom;
		this.description = description;
		this.type = type;
	}

	/**
	 * Récupère le nom de l'objet. * @return Le nom de l'objet sous forme de chaîne
	 * de caractères.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Récupère la description contextuelle de l'objet. * @return La description de
	 * l'objet sous forme de chaîne de caractères.
	 */
	public String getDescription() {
		return description;
	}

	public TypeObjet getType() {
		return type;
	}

	/**
	 * Retourne une représentation textuelle de l'objet.
	 * <p>
	 * Par défaut, cette méthode renvoie uniquement le nom de l'objet. Cela s'avère
	 * particulièrement utile pour l'affichage de listes textuelles dans l'interface
	 * utilisateur, comme lors de la consultation de l'inventaire.
	 * </p>
	 * * @return Le nom de l'objet.
	 */
	@Override
	public String toString() {
		return this.nom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, nom, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Objet)) {
			return false;
		}
		Objet other = (Objet) obj;
		return Objects.equals(description, other.description) && Objects.equals(nom, other.nom)
				&& this.type == other.type;
	}

}
