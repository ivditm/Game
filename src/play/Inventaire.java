package play;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Gère la collection d'objets possédés par le personnage.
 * <p>
 * Cette classe est le "cœur" de la logique de progression. Elle garantit que
 * les ingrédients sont ramassés dans l'ordre strict défini par le scénario
 * (rang 1, puis 2, etc.). Elle permet également de stocker des objets divers
 * comme la recette ou les documents informatiques.
 * </p>
 */
public class Inventaire implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Liste exhaustive des objets (ingrédients et documents) dans l'inventaire. */
	private List<Objet> objets;

	/**
	 * * Suit le rang du prochain ingrédient attendu. Initialisé à 1 au début de la
	 * partie.
	 */
	private int prochainRangAttendu;

	/**
	 * Initialise un inventaire vide et définit le premier rang attendu à 1.
	 */
	public Inventaire() {
		this.objets = new ArrayList<>();
		this.prochainRangAttendu = 1;
	}

	/**
	 * Tente d'ajouter un ingrédient à l'inventaire en vérifiant son rang.
	 * <p>
	 * L'ajout n'est accepté que si le rang de l'ingrédient correspond exactement au
	 * prochain rang attendu dans la séquence.
	 * </p>
	 * * @param ingredient L'ingrédient que le joueur tente de ramasser.
	 * 
	 * @return {@code true} si l'ingrédient a été ajouté avec succès, {@code false}
	 *         si l'ordre n'est pas respecté.
	 */
	public boolean ajouterIngredient(Ingredient ingredient) {
		if (ingredient.getRang() == prochainRangAttendu) {
			objets.add(ingredient);
			prochainRangAttendu++;
			return true;
		}
		return false;
	}

	/**
	 * Ajoute un objet qui n'est pas un ingrédient (ex: Recette, Document).
	 * <p>
	 * Ces objets ne sont pas soumis à la vérification de rang et peuvent être
	 * ajoutés à tout moment.
	 * </p>
	 * * @param objet L'objet divers à ajouter.
	 */
	public void ajouterObjetDivers(Objet objet) {
		if (objet.getType() != TypeObjet.INGREDIENT) {
			objets.add(objet);
		}
	}

	/**
	 * Vérifie si un ingrédient spécifique a déjà été collecté.
	 * <p>
	 * Cette méthode est particulièrement utile pour l'interface graphique (GUI)
	 * afin d'afficher l'état d'avancement (cocher des cases).
	 * </p>
	 * * @param rang Le rang de l'ingrédient à vérifier (1 à 5).
	 * 
	 * @return {@code true} si l'ingrédient est possédé.
	 */
	public boolean possedeIngredient(int rang) {
		return rang < prochainRangAttendu;
	}

	/**
	 * Indique si tous les ingrédients nécessaires à l'antidote sont réunis.
	 * * @return {@code true} si les 5 ingrédients ont été collectés.
	 */
	public boolean estComplet() {
		return prochainRangAttendu > 5;
	}

	/**
	 * Récupère la liste brute des objets de l'inventaire. * @return Une
	 * {@link List} contenant tous les objets possédés.
	 */
	public List<Objet> getObjets() {
		return objets;
	}

	/**
	 * Génère une description textuelle du contenu de l'inventaire. * @return Une
	 * chaîne de caractères listant les noms des objets possédés.
	 */
	@Override
	public String toString() {
		if (objets.isEmpty()) {
			return "L'inventaire est vide.";
		}
		StringBuilder sb = new StringBuilder("Contenu de l'inventaire :\n");
		for (Objet obj : objets) {
			sb.append("- ").append(obj.getNom()).append("\n");
		}
		return sb.toString();
	}
}