package typesdocuments;

import mediatheque.Document;
import mediatheque.EmpruntException;
import utilisateur.Utilisateur;

public class DVD implements Document {
	private int id;
	private String producteur;
	private String realisateur;
	private String nom;
	private String sortie;
	private Utilisateur user;

	public DVD(int index, String nom, String real, String prod,String date, Utilisateur user) {
		this.id = index;
		this.realisateur = real;
		this.producteur = prod;
		this.nom = nom;
		this.sortie = date;
		this.user = user;
	}
	
	public DVD(int index, String nom, String real, String prod,String date) {
		this(index, nom, real, prod, date, null);
	}

	@Override
	public void emprunter(Utilisateur a) throws EmpruntException {
		if(this.user != null) throw new EmpruntException();
		this.user = a;
		
		/*
		 * 
		 * Ajouter methode
		 * 
		 */
	}

	@Override
	public void retour() {
		this.user = null;
	}

	@Override
	public Object[] affiche() {
		Object[] ret = {id , nom, producteur, realisateur, sortie};
		return ret;
	}


}
