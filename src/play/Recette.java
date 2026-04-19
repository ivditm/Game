package play;

import java.util.List;
import java.util.Objects;

/**
 * Représente le document contenant le protocole de l'antidote.
 * <p>
 * Hérite de {@link Objet}. Ce document est indispensable pour connaître l'ordre
 * exact des ingrédients à collecter.
 * </p>
 */
public class Recette extends Objet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6821092003271433026L;
	// La liste des ingrédients dans l'ordre de leur rang (1 à 5)
	private List<Ingredient> listeIngredientsAttendus;

	/**
	 * Construit la recette avec la liste des ingrédients triés. * @param nom Le nom
	 * du document (ex: "Protocole Antidote").
	 * 
	 * @param description              La description de l'objet.
	 * @param listeIngredientsAttendus La liste des ingrédients que le joueur devra
	 *                                 trouver.
	 */
	public Recette(String nom, String description, List<Ingredient> listeIngredientsAttendus) {
		super(nom, description, TypeObjet.DOCUMENT);
		this.listeIngredientsAttendus = listeIngredientsAttendus;
	}

	/**
	 * Surcharge du toString pour afficher la recette comme une vraie liste à
	 * cocher. C'est ce texte qui s'affichera quand le joueur "lira" la recette.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("=== ").append(this.getNom()).append(" ===\n");
		sb.append(this.getDescription()).append("\n\n");
		sb.append("Ordre de collecte obligatoire :\n");
		for (Ingredient ing : listeIngredientsAttendus) {
			sb.append("Étape ").append(ing.getRang()).append(" : ").append(ing.getNom()).append("\n");
		}

		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(listeIngredientsAttendus);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Recette)) {
			return false;
		}
		Recette other = (Recette) obj;
		return Objects.equals(listeIngredientsAttendus, other.listeIngredientsAttendus);
	}

}