package interactions;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Représente une énigme à choix multiple.
 */
public class Enigme implements Interactable {

	private static final long serialVersionUID = 1L;

	private final String question;
	private final LinkedHashMap<String, String> propositions;
	private final String bonneReponse;
	private final String explication;
	private boolean resolue;

	public Enigme(String question, Map<String, String> propositions, String bonneReponse, String explication) {
		this.question = Objects.requireNonNull(question, "La question ne peut pas être null.");
		this.propositions = new LinkedHashMap<>(Objects.requireNonNull(propositions,
				"Les propositions de réponse ne peuvent pas être null."));
		if (this.propositions.isEmpty()) {
			throw new IllegalArgumentException("Une énigme doit contenir au moins une proposition.");
		}
		this.bonneReponse = normaliserReponse(bonneReponse);
		if (!this.propositions.containsKey(this.bonneReponse)) {
			throw new IllegalArgumentException("La bonne réponse doit correspondre à une proposition.");
		}
		this.explication = explication == null ? "" : explication;
		this.resolue = false;
	}

	public String getQuestion() {
		return question;
	}

	public Map<String, String> getPropositions() {
		return new LinkedHashMap<>(propositions);
	}

	public boolean estResolue() {
		return resolue;
	}

	public String getBonneReponse() {
		return bonneReponse;
	}

	public boolean validerReponse(String reponse) {
		return bonneReponse.equals(normaliserReponse(reponse));
	}

	public boolean resoudre(String reponse) {
		if (validerReponse(reponse)) {
			resolue = true;
			return true;
		}
		return false;
	}

	@Override
	public String examiner() {
		StringJoiner joiner = new StringJoiner("\n");
		joiner.add(question);
		for (Map.Entry<String, String> entree : propositions.entrySet()) {
			joiner.add(entree.getKey() + ". " + entree.getValue());
		}
		if (resolue) {
			joiner.add("Énigme résolue.");
		}
		return joiner.toString();
	}

	@Override
	public String interagir(play.Inventaire inventaire) {
		return examiner();
	}

	public String donnerExplication() {
		return explication;
	}

	@Override
	public String toString() {
		return "Enigme";
	}

	private String normaliserReponse(String reponse) {
		if (reponse == null) {
			return "";
		}
		return reponse.trim().toUpperCase();
	}

}
