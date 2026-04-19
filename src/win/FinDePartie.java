package win;

import play.Inventaire;

public class FinDePartie {

	private final FabriqueAntidote fabrique;

	public FinDePartie() {
		this.fabrique = new FabriqueAntidote();
	}

	/**
	 * Analyse les conditions et retourne l'état actuel de la partie.
	 */
	public EtatJeu verifierEtat(Inventaire inv, int nbRencontres) {
		if (fabrique.fabriquer(inv)) {
			return EtatJeu.VICTOIRE;
		}

		if (nbRencontres >= 3) {
			return EtatJeu.DEFAITE;
		}

		return EtatJeu.EN_COURS;
	}
}