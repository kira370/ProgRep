package typesdocuments;

import mediatheque.Document;
import mediatheque.EmpruntException;
import utilisateur.Utilisateur;

public class Livre implements Document {
	private int id;
	private String auteur;
	private String nom;
	private Utilisateur user;
	private String annee;
	
	public Livre(int index, String nom, String auteur, String annee,Utilisateur user) {
		this.id = index;
		this.auteur = auteur;
		this.nom = nom;
		this.annee = annee;
		this.user = user;
	}
	
	public Livre(int index, String nom, String auteur, String annee) {
		this(index, nom, auteur, annee, null);
	}

	@Override
	public void emprunter(Utilisateur a) throws EmpruntException {
		if(this.user != null) throw new EmpruntException();
		System.out.println(a.getNom());
		this.user = a;
	}

	@Override
	public void retour() {
		this.user = null;
	}

	@Override
	public Object[] affiche() {
		Object[] ret = {id , nom, auteur, annee};
		return ret;
	}

}
