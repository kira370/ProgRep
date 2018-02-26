package utilisateur;

public class Utilisateur {
	private String user;
	private String password;
	private String nom;
	private TypeUtilisateur type;
	
	public Utilisateur(String user, String password,String nom, TypeUtilisateur type) {
		this.user = user;
		this.nom = nom;
		this.password = password;
		this.type = type;
	}
	
	
	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getNom() {
		return nom;
	}

	public TypeUtilisateur getType() {
		return type;
	}
}
