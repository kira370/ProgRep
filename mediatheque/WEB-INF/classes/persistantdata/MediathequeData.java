package persistantdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mediatheque.Document;
import mediatheque.Mediatheque;
import mediatheque.PersistentMediatheque;
import typesdocuments.DocumentFactory;
import utilisateur.TypeUtilisateur;
import utilisateur.Utilisateur;

// classe mono-instance  dont l'unique instance n'est connue que de la bibliotheque
// via une auto-déclaration dans son bloc static

public class MediathequeData implements PersistentMediatheque {
// Jean-François Brette 01/01/2018
	private static Connection co = null;
	private Statement st = null;
	private static ResultSet rs = null;
	
	static {
		Mediatheque.getInstance().setData(new MediathequeData());
		// Chargement dynamique du Driver qui s'auto-instancie
		// Et s'initialise
		try {
			//Class.forName("oracle.jdbc.OracleDriver");
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}

		try {
			co = DriverManager.getConnection("jdbc:mysql://localhost/biblio", "jimmy", "123");
			//co = DriverManager.getConnection("jdbc:oracle:thin:@vs-oracle2:1521:ORCL", "GRP201US9", "GRP201US9");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	private MediathequeData() {}

	// renvoie la liste de tous les documents de la bibliothèque
	@Override
	public List<Document> tousLesDocuments() {
		String req = "SELECT * FROM document";
		List<Document> documents = new ArrayList<Document>();
		
		try {
			st = co.createStatement();
			rs = st.executeQuery(req);
			while (rs.next()) {
				documents.add(DocumentFactory.creerDocument(rs));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return documents;
		
		
	}

	// va récupérer le User dans la BD et le renvoie
	// si pas trouvé, renvoie null
	@Override
	public Utilisateur getUser(String login, String password) {
		Utilisateur user = null;

		try {
			//on prepare la requete
			PreparedStatement requete = co.prepareStatement("SELECT * FROM utilisateur WHERE login = ? AND password = ?");
			requete.setObject(1,login);
			requete.setObject(2,password);
			
			// resultat de la requete
			rs = requete.executeQuery();
			
			//si pas trouvé, renvoi null
			if(!rs.next()) {return null;}
			
			
			TypeUtilisateur type = null;
			if(rs.getBoolean("isSecretaire")) {
				type = TypeUtilisateur.BIBLIOTHECAIRE;
			}else {
				type = TypeUtilisateur.ABONNE;
			}
			user = new Utilisateur(login,password,rs.getString("nom"),type);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("test");
		}
		return user;
	}

	// va récupérer le document de numéro numDocument dans la BD
	// et le renvoie
	// si pas trouvé, renvoie null
	@Override
	public Document getDocument(int numDocument) {
		Document doc = null;
		try {
			PreparedStatement requete = co.prepareStatement("SELECT * FROM document WHERE idDoc = ?;");
			requete.setObject(1,numDocument);
			rs = requete.executeQuery();
			//si pas trouvé, renvoi null
			if(!rs.next()) {return null;}
			
			doc = DocumentFactory.creerDocument(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return doc;
	}

	@Override
	public void nouveauDocument(int type, Object... args) {
		// args [0] --> le nom
		// args [1] --> l'auteur
		// etc...
		try {
			PreparedStatement requete = co.prepareStatement("INSERT INTO document(typeDoc,nom,auteur) VALUES (?,?,?);");
			requete.setObject(1,type);
			requete.setObject(2,(String) args[0]);
			requete.setObject(3,(String) args[1]);
			requete.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
