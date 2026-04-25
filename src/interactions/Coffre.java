package interactions;

import java.util.Objects;

import play.Ingredient;
import play.Inventaire;
import play.Objet;

/**
 * Représente un coffre pouvant contenir un objet récupérable une seule fois.
 */
public class Coffre implements Interactable {

	private static final long serialVersionUID = 1L;

	private final String nom;
	private final String description;
	private final Objet contenu;
	private boolean ouvert;

	public Coffre(String nom, String description, Objet contenu) {
		this.nom = Objects.requireNonNull(nom, "Le nom du coffre ne peut pas être null.");
		this.description = Objects.requireNonNull(description, "La description du coffre ne peut pas être null.");
		this.contenu = contenu;
		this.ouvert = false;
	}

	@Override
	public String getNom() {
		return nom;
	}

	public Objet getContenu() {
		return contenu;
	}

	public boolean estOuvert() {
		return ouvert;
	}

	@Override
	public String examiner() {
		if (ouvert) {
			return nom + " est déjà ouvert.";
		}
		if (contenu == null) {
			return description + " Il semble vide.";
		}
		return description + " Il contient peut-être quelque chose d'utile.";
	}

	@Override
	public String interagir(Inventaire inventaire) {
		if (ouvert) {
			return nom + " est déjà ouvert.";
		}
		if (inventaire == null) {
			return "Impossible d'ouvrir " + nom + " sans inventaire.";
		}
		if (contenu == null) {
			ouvert = true;
			return "Vous ouvrez " + nom + ", mais il est vide.";
		}
		if (contenu instanceof Ingredient ingredient) {
			if (!inventaire.ajouterIngredient(ingredient)) {
				return "Vous avez trouvé " + ingredient.getNom()
						+ ", mais vous devez respecter l'ordre de collecte.";
			}
		} else {
			inventaire.ajouterObjetDivers(contenu);
		}

		ouvert = true;
		return "Vous ouvrez " + nom + " et récupérez : " + contenu.getNom() + ".";
	}

	@Override
	public String toString() {
		return nom;
	}
}
