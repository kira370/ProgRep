package typesdocuments;

import mediatheque.Document;
import mediatheque.EmpruntException;
import utilisateur.Utilisateur;

public class CD implements Document {
	private int id;
	private String auteur;
	private String nom;
	private String compositeur;
	private String annee;
	private Utilisateur user;
	
	public CD(int index, String nom, String auteur,String comp, String annee, Utilisateur user) {
		this.id = index;
		this.auteur = auteur;
		this.nom = nom;
		this.compositeur = comp;
		this.annee = annee;
		this.user = user;
	}
	
	public CD(int index, String nom, String auteur,String comp, String annee) {
		this(index, nom, auteur, comp, annee, null);
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
		Object[] ret = {id , nom, auteur, compositeur, annee};
		return ret;
	}

}
