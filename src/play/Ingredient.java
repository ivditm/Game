package play;

import java.util.Objects;

/**
 * Représente un ingrédient spécifique nécessaire à la synthèse de l'antidote.
 * <p>
 * Cette classe hérite de {@link Objet} et ajoute la notion de "rang". Le rang
 * définit l'ordre strict dans lequel les ingrédients doivent être collectés par
 * le joueur (de 1 à 5). Par exemple, le système empêchera le joueur de ramasser
 * l'ingrédient de rang 2 si l'ingrédient de rang 1 n'est pas déjà présent dans
 * son inventaire.
 * </p>
 */
public class Ingredient extends Objet {

	private static final long serialVersionUID = -6816230121893197092L;
	/**
	 * * Le rang de l'ingrédient déterminant sa position dans la séquence de
	 * collecte (ex : 1 pour la Poudre stabilisatrice, 2 pour l'Adrénaline pure).
	 */
	private int rang;

	/**
	 * Construit un nouvel ingrédient avec un nom, une description et un rang
	 * spécifique. * @param nom Le nom de l'ingrédient (ex : "Adrénaline pure").
	 * 
	 * @param description La description textuelle affichée lorsque le joueur
	 *                    examine l'ingrédient.
	 * @param rang        La position de cet ingrédient dans l'ordre strict de
	 *                    collecte (entier positif).
	 */
	public Ingredient(String nom, String description, int rang) {
		// Appel du constructeur de la classe mère (Objet) pour initialiser le nom et la
		// description
		super(nom, description, TypeObjet.INGREDIENT);
		this.rang = rang;
	}

	/**
	 * Récupère le rang de l'ingrédient.
	 * <p>
	 * Ce rang est utilisé principalement par la classe {@code Inventaire} pour
	 * vérifier si le joueur respecte la progression imposée par le scénario.
	 * </p>
	 * * @return Le rang de l'ingrédient sous forme d'entier.
	 */
	public int getRang() {
		return rang;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(rang);
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
		if (!(obj instanceof Ingredient)) {
			return false;
		}
		Ingredient other = (Ingredient) obj;
		return rang == other.rang;
	}

}
