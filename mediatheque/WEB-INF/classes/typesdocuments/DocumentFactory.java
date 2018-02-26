package typesdocuments;

import java.sql.ResultSet;
import java.sql.SQLException;

import mediatheque.Document;

public class DocumentFactory {
	public static Document creerDocument(ResultSet rs) throws SQLException {

		switch (rs.getInt("typeDoc")) {
		case 1: // Livre
			return new Livre(rs.getInt("idDoc"),rs.getString("nom"), rs.getString("auteur"));
		case 2: //CD
			return new CD(rs.getInt("idDoc"),rs.getString("nom"), rs.getString("auteur"));
		case 3: //DVD
			return new DVD(rs.getInt("idDoc"),rs.getString("nom"), rs.getString("auteur"));
		default:
			throw new SQLException("Problème avec les paramètres demandés.");
		}
	}
}
