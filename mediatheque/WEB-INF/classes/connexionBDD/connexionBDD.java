package connexionBDD;

public class connexionBDD {
	private static boolean connexion = false;

	public static boolean isConnexion() {
		return connexion;
	}

	public static void setConnexion(boolean c) {
		connexion = c;
	}
	
}
