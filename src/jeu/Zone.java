package jeu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import danger.Monstre;
import interactions.Interactable;

/**
 * Représente une zone du jeu (une salle, un couloir, etc.).
 * <p>
 * Chaque zone possède un nom, une description, une image et des sorties vers
 * d'autres zones. Elle peut également contenir un monstre, des éléments
 * interactifs et des obstacles secrets bloquant certains passages.
 * </p>
 */
public class Zone implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Nom court de la zone (ex: "Cafétéria", "Laboratoire"). */
	private String nom;

	/** Description textuelle de la zone. */
	private String description;

	/** Nom du fichier image associé à la zone. */
	private String nomImage;

	/** Map des sorties libres : direction -> zone voisine. */
	private final Map<Direction, Zone> sorties;

	/** Map des obstacles (énigmes, portes) bloquant une direction. */
	private final Map<Direction, Interactable> obstacles;

	/** Le monstre présent dans la zone (null s'il n'y en a pas). */
	private Monstre monstre;

	/**
	 * Liste des objets interactifs (Coffres, Ordinateurs) présents dans la pièce.
	 */
	private List<Interactable> interactables;

	/**
	 * Construit une zone avec un nom, une description et un nom d'image.
	 *
	 * @param nom         le nom court de la zone
	 * @param description description détaillée de la zone
	 * @param image       nom du fichier image associé
	 */
	public Zone(String nom, String description, String image) {
		this.nom = nom;
		this.description = description;
		this.nomImage = image;
		this.sorties = new EnumMap<>(Direction.class);
		this.obstacles = new EnumMap<>(Direction.class);
		this.interactables = new ArrayList<>();
		this.monstre = null;
	}

	// --- MÉTHODES D'INFORMATIONS GÉNÉRALES ---

	public String getNom() {
		return nom;
	}

	public String nomImage() {
		return nomImage;
	}

	// --- MÉTHODES POUR GÉRER LES SORTIES ET OBSTACLES ---

	public void ajouteSortie(Direction sortie, Zone zoneVoisine) {
		sorties.put(sortie, zoneVoisine);
	}

	public Zone obtientSortie(Direction direction) {
		return sorties.get(direction);
	}

	public void ajouteObstacle(Direction direction, Interactable obstacle) {
		this.obstacles.put(direction, obstacle);
	}

	public Interactable getObstacle(Direction direction) {
		return obstacles.get(direction);
	}

	/**
	 * Supprime l'obstacle dans une direction donnée (appelé quand une énigme est
	 * résolue).
	 */
	public void retirerObstacle(Direction direction) {
		this.obstacles.remove(direction);
	}

	// --- MÉTHODES POUR LE MONSTRE ---

	public void setMonstre(Monstre monstre) {
		this.monstre = monstre;
	}

	public Monstre getMonstre() {
		return monstre;
	}

	public boolean possedeMonstre() {
		return monstre != null;
	}

	// --- MÉTHODES POUR LES INTERACTABLES ---

	public void ajouterInteractable(Interactable interactable) {
		this.interactables.add(interactable);
	}

	public List<Interactable> getInteractables() {
		return interactables;
	}

	// --- MÉTHODES D'AFFICHAGE ---

	@Override
	public String toString() {
		return nom + " : " + description;
	}

	/**
	 * Retourne une description complète de la zone.
	 *
	 * @return description complète de la zone
	 */
	public String descriptionLongue() {
		StringBuilder sb = new StringBuilder();
		sb.append("Vous êtes dans : ").append(nom).append(".\n");
		sb.append(description).append("\n");

		if (!interactables.isEmpty()) {
			sb.append("Éléments interactifs ici : ");
			for (Interactable inter : interactables) {
				sb.append(inter.toString()).append(" ");
			}
			sb.append("\n");
		}

		sb.append("Sorties possibles : ").append(sorties());

		return sb.toString();
	}

	/**
	 * Fusionne visuellement les sorties libres et les obstacles pour que le joueur
	 * ne puisse pas deviner quelles portes sont bloquées avant d'essayer.
	 */
	private String sorties() {
		Set<Direction> toutesLesDirections = new HashSet<>();
		toutesLesDirections.addAll(sorties.keySet());
		toutesLesDirections.addAll(obstacles.keySet());

		return toutesLesDirections.toString();
	}
}
