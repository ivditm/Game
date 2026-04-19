package play;

/**
 * Définit les différentes catégories d'objets pouvant exister dans le jeu.
 * <p>
 * Cette énumération permet de classifier rapidement un objet sans avoir à
 * vérifier sa classe exacte, ce qui est très utile pour l'affichage (GUI) ou
 * pour la logique de l'inventaire.
 * </p>
 */
public enum TypeObjet {

	/**
	 * Un composant chimique servant à fabriquer l'antidote final.
	 */
	INGREDIENT("Ingrédient chimique"),

	/**
	 * Un élément d'information lisible par le joueur.
	 */
	DOCUMENT("Document d'information"),

	/**
	 * * Le remède final créé à partir de tous les ingrédients.
	 */
	ANTIDOTE("Antidote expérimental");

	private final String nomAffichage;

	TypeObjet(String nomAffichage) {
		this.nomAffichage = nomAffichage;
	}

	/**
	 * Récupère le nom formaté du type d'objet.
	 * 
	 * @return Le nom d'affichage adapté pour l'interface graphique.
	 */
	public String getNomAffichage() {
		return nomAffichage;
	}

	/**
	 * Retourne une représentation textuelle propre du type d'objet.
	 * 
	 * @return Le nom d'affichage du type.
	 */
	@Override
	public String toString() {
		return this.nomAffichage;
	}
}