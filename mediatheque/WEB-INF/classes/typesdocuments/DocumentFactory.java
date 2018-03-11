package typesdocuments;

import java.sql.ResultSet;
import java.sql.SQLException;

import mediatheque.Document;
import mediatheque.EmpruntException;
import utilisateur.Utilisateur;

public class DocumentFactory {
	public static Document creerDocument(ResultSet rs,String type,int idDoc,Utilisateur user) throws SQLException {
		Document doc = null;
		switch (type) {
		case "livre": // Livre
			if(user == null) {
				doc = new Livre(idDoc,rs.getString("nom"), rs.getString("auteur"), rs.getString("annee"));
			}else {
				doc = new Livre(idDoc,rs.getString("nom"), rs.getString("auteur"), rs.getString("annee"), user);
			}
			return doc;
		case "cd": // CD
			if(user == null) {
				doc = new CD(idDoc,rs.getString("nom"), rs.getString("auteur"), rs.getString("compositeur"), rs.getString("annee"));
			}else {
				doc = new CD(idDoc,rs.getString("nom"), rs.getString("auteur"), rs.getString("compositeur"), rs.getString("annee"),user);
			}
			return doc;
		case "dvd": // DVD
			if(user == null) {
				doc = new DVD(idDoc,rs.getString("nom"), rs.getString("realisateur"), rs.getString("producteur"), rs.getString("sortie"));
			}else {
				doc = new DVD(idDoc,rs.getString("nom"), rs.getString("realisateur"), rs.getString("producteur"), rs.getString("sortie"), user);
			}
			return doc;
		default:
			throw new SQLException("Problème avec les paramètres demandés.");
		}
	}
}
