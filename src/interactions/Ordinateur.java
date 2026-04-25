package interactions;

import play.Inventaire;
import play.Objet;

/**
 * Représente un ordinateur qui exige la résolution d'une énigme avant d'ouvrir
 * son contenu.
 */
public class Ordinateur extends Coffre {

	private static final long serialVersionUID = 1L;

	private final Enigme enigme;

	public Ordinateur(String nom, String description, Objet contenu, Enigme enigme) {
		super(nom, description, contenu);
		this.enigme = enigme;
	}

	public Enigme getEnigme() {
		return enigme;
	}

	@Override
	public String examiner() {
		StringBuilder sb = new StringBuilder(super.examiner());
		if (!estOuvert() && enigme != null) {
			sb.append(" Un écran vous demande de résoudre une énigme.");
		}
		return sb.toString();
	}

	@Override
	public String interagir(Inventaire inventaire) {
		if (enigme != null && !enigme.estResolue()) {
			return "L'ordinateur est verrouillé. " + enigme.examiner();
		}
		return super.interagir(inventaire);
	}

	public String repondreAEnigme(String reponse, Inventaire inventaire) {
		if (enigme == null) {
			return super.interagir(inventaire);
		}
		if (!enigme.resoudre(reponse)) {
			return "Mauvaise réponse. L'ordinateur reste verrouillé.";
		}
		return "Bonne réponse. " + super.interagir(inventaire);
	}
}
