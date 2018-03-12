package persistantdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
		ResourceBundle bundle = ResourceBundle.getBundle("config.config");
		String tag = bundle.getString("tag");
		try {
			Class.forName(bundle.getString(tag+".driver"));
			co = DriverManager.getConnection(bundle.getString(tag+".adresse"), bundle.getString(tag+".login"), bundle.getString(tag+".password"));
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private MediathequeData() {}

	// renvoie la liste de tous les documents de la bibliothèque
	@Override
	public List<Document> tousLesDocuments() {
		String req = "SELECT * FROM document;";
		List<Document> documents = new ArrayList<Document>();
		
		try {
			
			st = co.createStatement();
			rs = st.executeQuery(req);
			while (rs.next()) {
				String type = rs.getString("typeDoc");
				int idDoc = rs.getInt("id");
				int idUser = rs.getInt("idUser");
				PreparedStatement requete = co.prepareStatement("SELECT * FROM " + type + " WHERE idDoc = ?;");
				requete.setObject(1,rs.getInt("idDoc"));
				ResultSet result = requete.executeQuery();
				
				
				// si un utilisateur a déjà emprunté le livre.
				if(idUser != 0) {
					PreparedStatement reqUser = co.prepareStatement("SELECT * FROM utilisateur WHERE id = ?;");
					reqUser.setObject(1,idUser);
					ResultSet resultUser = reqUser.executeQuery();
					resultUser.next();
					
					TypeUtilisateur typeUser = null;
					if(resultUser.getBoolean("isSecretaire")) {
						typeUser = TypeUtilisateur.BIBLIOTHECAIRE;
					}else {
						typeUser = TypeUtilisateur.ABONNE;
					}				
					
					if(result.next()) {
						documents.add(DocumentFactory.creerDocument(
								result,
								type,
								idDoc,
								new Utilisateur(
										resultUser.getInt("id"),
										resultUser.getString("login"),
										resultUser.getString("password"),
										resultUser.getString("nomPrenom"),
										typeUser)
								));
					}
				}else {
					if(result.next()) {
						documents.add(DocumentFactory.creerDocument(result,type,idDoc,null));
					}
				}
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
			
			while(rs.next()) {
				TypeUtilisateur type = null;
				if(rs.getBoolean("isSecretaire")) {
					type = TypeUtilisateur.BIBLIOTHECAIRE;
				}else {
					type = TypeUtilisateur.ABONNE;
				}
				user = new Utilisateur(rs.getInt("id"),login,password,rs.getString("nomPrenom"),type);
			}
		} catch (SQLException | NullPointerException e) {}
		return user;
	}

	// va récupérer le document de numéro numDocument dans la BD
	// et le renvoie
	// si pas trouvé, renvoie null
	@Override
	public Document getDocument(int numDocument) {
		Document doc = null;
		try {
			PreparedStatement requete = co.prepareStatement("SELECT * FROM document WHERE id = ?;");
			requete.setObject(1,numDocument);
			rs = requete.executeQuery();
			while(rs.next()) {
				String type = rs.getString("typeDoc");
				int idDoc = rs.getInt("id");
				int idUser = rs.getInt("idUser");
				requete = co.prepareStatement("SELECT * FROM " + type + " WHERE idDoc = ?;");
				requete.setObject(1,rs.getInt("idDoc"));			
				

				rs = requete.executeQuery();
				if(idUser != 0) {
					PreparedStatement reqUser = co.prepareStatement("SELECT * FROM utilisateur WHERE id = ?;");
					reqUser.setObject(1,idUser);
					ResultSet resultUser = reqUser.executeQuery();
					resultUser.next();
					
					TypeUtilisateur typeUser = null;
					if(resultUser.getBoolean("isSecretaire")) {
						typeUser = TypeUtilisateur.BIBLIOTHECAIRE;
					}else {
						typeUser = TypeUtilisateur.ABONNE;
					}				
					
					if(rs.next()) {
						doc = DocumentFactory.creerDocument(
								rs,
								type,
								idDoc,
								new Utilisateur(
										resultUser.getInt("id"),
										resultUser.getString("login"),
										resultUser.getString("password"),
										resultUser.getString("nomPrenom"),
										typeUser)
								);
					}
				}else {
					if(rs.next()) {
						doc = DocumentFactory.creerDocument(rs,type,idDoc,null);
					}
				}
			}
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
		PreparedStatement requete = null;
		int idDoc = 0;
		String typeDoc = null;
		try {
			switch(type) {
			case 1 : 
				requete = co.prepareStatement("INSERT INTO livre(nom,auteur,annee) VALUES (?,?,?);", Statement.RETURN_GENERATED_KEYS);
				requete.setObject(1,(String) args[0]);
				requete.setObject(2,(String) args[1]);
				requete.setObject(3,(Integer) args[2]);
				requete.executeUpdate();
				rs = requete.getGeneratedKeys();
				if(rs.next()) {
					idDoc = rs.getInt(1);
					typeDoc = "livre";
				}
				break;
			case 2 : 
				requete = co.prepareStatement("INSERT INTO cd(nom,auteur,compositeur,annee) VALUES (?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
				requete.setObject(1,(String) args[0]);
				requete.setObject(2,(String) args[1]);
				requete.setObject(3,(String) args[2]);
				requete.setObject(4,(Integer) args[3]);
				requete.executeUpdate();
				rs = requete.getGeneratedKeys();
				if(rs.next()) {
					idDoc = rs.getInt(1);
					typeDoc = "cd";
				}
				break;
			case 3 :
				requete = co.prepareStatement("INSERT INTO dvd(nom,realisateur,producteur,sortie) VALUES (?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
				requete.setObject(1,(String) args[0]);
				requete.setObject(2,(String) args[1]);
				requete.setObject(3,(String) args[2]);
				requete.setObject(4,(Integer) args[3]);
				requete.executeUpdate();
				rs = requete.getGeneratedKeys();
				if(rs.next()) {
					idDoc = rs.getInt(1);
					typeDoc = "dvd";
				}
			}
			
			
			requete = co.prepareStatement("INSERT INTO document(idDoc,idUser,typeDoc) VALUES (?,NULL,?);");
			requete.setObject(1,idDoc);
			requete.setObject(2,typeDoc);
			requete.executeUpdate();
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Document> tousLesDocuments(int idUsr) {
		String req = "SELECT * FROM document WHERE idUser = ?;";
		List<Document> documents = new ArrayList<Document>();
		
		try {
		
			PreparedStatement st = co.prepareStatement(req);
			st.setObject(1,idUsr);
			rs = st.executeQuery();
			while (rs.next()) {
				String type = rs.getString("typeDoc");
				int idDoc = rs.getInt("id");
				PreparedStatement requete = co.prepareStatement("SELECT * FROM " + type + " WHERE idDoc = ?;");
				requete.setObject(1,rs.getInt("idDoc"));
				ResultSet result = requete.executeQuery();
				result.next();
				documents.add(DocumentFactory.creerDocument(result,type,idDoc,null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return documents;
		
		
	}

	@Override
	public void emprunterDoc(int idDoc, int idUser) {
		String req = "UPDATE document SET idUser = ? WHERE id = ?";
		try {
			PreparedStatement st = co.prepareStatement(req);
			st.setObject(1,(Integer) idUser);
			st.setObject(2,(Integer) idDoc);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void retourDoc(int idDoc) {
		String req = "UPDATE document SET idUser = NULL WHERE id = ?";
		try {
			PreparedStatement st = co.prepareStatement(req);
			st.setObject(1,(Integer) idDoc);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
