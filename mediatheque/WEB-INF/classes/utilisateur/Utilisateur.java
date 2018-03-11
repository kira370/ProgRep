package utilisateur;

public class Utilisateur {
	private int id;
	private String user;
	private String password;
	private String nomPrenom;
	private TypeUtilisateur type;
	
	public Utilisateur(int index, String user, String password,String nom, TypeUtilisateur type) {
		this.id = index;
		this.user = user;
		this.nomPrenom = nom;
		this.password = password;
		this.type = type;
	}
	
	
	public String getUser() {
		return user;
	}
	
	public int getId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}

	public String getNom() {
		return nomPrenom;
	}

	public TypeUtilisateur getType() {
		return type;
	}
}
