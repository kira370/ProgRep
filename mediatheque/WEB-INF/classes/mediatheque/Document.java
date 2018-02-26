// Jean-François Brette 01/01/2018

package mediatheque;

import utilisateur.Utilisateur;

public interface Document {
// Jean-François Brette 01/01/2018
	
	void emprunter(Utilisateur a) throws EmpruntException;
	void retour();
	Object[] affiche();
	Utilisateur getUser();
}
