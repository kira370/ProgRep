package services;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;

import mediatheque.Document;
import mediatheque.Mediatheque;
import utilisateur.TypeUtilisateur;
import utilisateur.Utilisateur;

public class Action extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(true);
		Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
		if(user == null) {
			response.sendRedirect("./loginuser?error=3");
			return;
		}
		out.println("<html>");
		out.println("<head>");
		out.println("<title>");
		out.println("</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("Bonjour" + user.getNom() +" !");
		out.println("<div>");
		String action = request.getParameter("action");
		
		if(action == null) {
			response.sendRedirect("./home?error=1");
			return;
		}
		String htmlStr = "";
		switch(action) {
		
		case "emprunter":
			htmlStr += "<select name='idDoc'>";
			for(Document doc : Mediatheque.getInstance().tousLesDocuments()) {
				Object[] afficheDoc = doc.affiche();
				htmlStr += "<option value='" + afficheDoc[0] + "'>"+ afficheDoc[1] +" par " + afficheDoc[2] + "</option>";
			}
			htmlStr += "</select>";
			break;
		case "retourner":
			
			
			htmlStr += "<select name='idDoc'>";
			for(Document doc : Mediatheque.getInstance().tousLesDocuments(user.getId())) {
				Object[] afficheDoc = doc.affiche();
				htmlStr += "<option value='" + afficheDoc[0] + "'>"+ afficheDoc[1] +" par " + afficheDoc[2] + "</option>";
			}
			htmlStr += "</select>";
			break;
			
			
			
			
			
		case "ajouter":
			if(user.getType().equals(TypeUtilisateur.ABONNE)){
				response.sendRedirect("./home?error=1");
				return;
			}
			if(request.getParameter("error") != null) {
				switch(request.getParameter("error")) {
				case "1":
					out.println("<h4>Vous devez remplir les deux champs.</h4></br>");
					break;
				case "0":
					out.println("<h4>un problème est survenue, veuillez reessayer.</h4></br>");
					break;
				}
			}
			String typeDoc = request.getParameter("doc");
			htmlStr +=  "<select name='type' onChange='window.location.href = \"/mediatheque/action?action=ajouter&doc=\" + this.value'>";
			htmlStr += "<option value='0'>Type</option>";
			htmlStr += "<option value='1'" + ((typeDoc != null && typeDoc.equals("1"))?"selected":"") +">Livre</option>";
			htmlStr += "<option value='2'" + ((typeDoc != null && typeDoc.equals("2"))?"selected":"") +">CD</option>";
			htmlStr += "<option value='3'" + ((typeDoc != null && typeDoc.equals("3"))?"selected":"") +">DVD</option>";
			htmlStr += "</select>";
			htmlStr += "<br>";

			if(typeDoc != null){
				switch(typeDoc){
				case "1" :
					htmlStr += "Nom du document:<br>";
					htmlStr += "<input type='text' name='nom'><br>";
					htmlStr += "Nom de l'auteur:<br>";
					htmlStr += "<input type='text' name='auteur'><br>";
					htmlStr += "Année (XXXX):<br>";
					htmlStr += "<input type='number' name='annee'><br>";
					break;
				case "2" :
					htmlStr += "Nom du document:<br>";
					htmlStr += "<input type='text' name='nom'><br>";
					htmlStr += "Nom de l'auteur:<br>";
					htmlStr += "<input type='text' name='auteur'><br>";
					htmlStr += "Nom du compositeur:<br>";
					htmlStr += "<input type='text' name='compositeur'><br>";
					htmlStr += "Année (XXXX):<br>";
					htmlStr += "<input type='number' name='annee'><br>";
					break;
				case "3" :
					htmlStr += "Nom du document:<br>";
					htmlStr += "<input type='text' name='nom'><br>";
					htmlStr += "Nom du realisateur:<br>";
					htmlStr += "<input type='text' name='realisateur'><br>";
					htmlStr += "Nom du producteur:<br>";
					htmlStr += "<input type='text' name='producteur'><br>";
					htmlStr += "Année de sortie (XXXX):<br>";
					htmlStr += "<input type='number' name='sortie'><br>";
				}
			}
			
			break;
		}
		
		
		out.println("<form action='" + action + "' method='POST'>");
		out.println(htmlStr);
		out.println("<input type='submit' value='Envoyer'/>");
		out.println("</form>");
		out.println("");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
}
