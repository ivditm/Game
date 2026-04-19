package win;

import java.io.Serializable;

import play.Inventaire;

public class FabriqueAntidote implements Serializable {

	/**
	 * Tente de fabriquer l'antidote.
	 * 
	 * @return true si la synthèse est réussie, false sinon.
	 */
	public boolean fabriquer(Inventaire inv) {
		return inv != null && inv.estComplet();
	}
}
