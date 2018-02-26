package typesdocuments;

import mediatheque.Document;
import mediatheque.EmpruntException;
import utilisateur.Utilisateur;

public class Livre implements Document {
	private int id;
	private String auteur;
	private String nom;
	private Utilisateur user;
	
	public Livre(int index, String nom, String auteur) {
		this.id = index;
		this.auteur = auteur;
		this.nom = nom;
		this.user = null;
	}

	@Override
	public void emprunter(Utilisateur a) throws EmpruntException {
		if(this.user != null) throw new EmpruntException();
		this.user = a;
	}

	@Override
	public void retour() {
		this.user = null;
	}

	@Override
	public Object[] affiche() {
		Object[] ret = {id , nom, auteur};
		return ret;
	}

	@Override
	public Utilisateur getUser() {
		return this.user;
	}

}
