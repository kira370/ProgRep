package mediatheque;

import java.util.List;

import utilisateur.Utilisateur;

public interface PersistentMediatheque {
// Jean-François Brette 01/01/2018
	List<Document> tousLesDocuments();
	List<Document> tousLesDocuments(int id);


	Document getDocument(int numDocument);

	Utilisateur getUser(String login, String password);
	
	void nouveauDocument(int type, Object... args );

}
