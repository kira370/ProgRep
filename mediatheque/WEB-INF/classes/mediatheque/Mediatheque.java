package mediatheque;

import java.util.List;

import utilisateur.Utilisateur;

/**
 cette classe représente la mediatheque du point de vue du domaine
 cette classe est un singleton
 elle a un attribut qui fait le lien avec les données persistentes
 
 LES SERVLETS DOIVENT S'ADRESSER A CETTE CLASSE EXCLUSIVEMENT
 POUR INTERROGER LES DONNEES

 beaucoup des méthodes de Mediatheque sont déléguées à l'attribut de persistance
 qui devra répercuter ces opérations sur les données persistantes

*/
public class Mediatheque {
// Jean-François Brette 01/01/2018

// singleton standard ======================== 
	static {
		instance = new Mediatheque();
	}
	private static Mediatheque instance;
	public static Mediatheque getInstance() {
		return instance;
	}
	private Mediatheque () {}
// fin - singleton standard ==================

// lien avec les données persistantes +++++++++++++++
	private PersistentMediatheque data;

	public void setData(PersistentMediatheque data) {
		if (this.data == null) this.data = data;
	}
// fin - lien avec les données persistantes +++++++++

// ********** action sur le document ***********************

	// enregistre l'emprunt par l'abonné a du document d)

	public void emprunt(Document d, Utilisateur a) throws EmpruntException {
		synchronized(d){
			d.emprunter(a);
		}
	}

	//enregistre le retour du document d)

	public void retour (Document d) {
		synchronized(d){
			d.retour();
		}
	}

// *********************** délégation **********************

	// renvoie la liste de tous les documents de la bibliothèque

	public List<Document> tousLesDocuments() {
		return data.tousLesDocuments();
	}

	public List<Document> tousLesDocuments(int id) {
		return data.tousLesDocuments(id);
	}
	
	// renvoie le user de login et passwd 
	// si pas trouvé, renvoie null
	
	public Utilisateur getUser (String login, String password) {
		return data.getUser(login, password);
	}

	// renvoie le document de numéro numDocument
	// si pas trouvé, renvoie null

	public Document getDocument(int numDocument) {
		return data.getDocument(numDocument);
	}
	
	// ajoute un nouveau document

	public void nouveauDocument(int type, Object... args ) {
		data.nouveauDocument(type, args);
	};

	
	
	/*
	 * 
	 * a faire
	 * methode 
	 * public void emprunterDoc(int idDoc, int idUser)
	 * public void retourDoc(int idDoc)
	 * 
	 */
	
	
	
}
