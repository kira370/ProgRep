package services;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;


import mediatheque.Mediatheque;
import utilisateur.Utilisateur;

public class Ajouter extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(true);
		Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
		if(user == null) {
			response.sendRedirect("./loginuser?error=3");
			return;
		}
		
		String nom = request.getParameter("nom");
		String auteur = request.getParameter("auteur");
		String type = request.getParameter("type");
		if(nom.equals("") || auteur.equals("")) {
			response.sendRedirect("./action?action=ajouter&error=1");
			return;
		}
		
		try {
			Mediatheque media = Mediatheque.getInstance();
			media.nouveauDocument(Integer.parseInt(type), nom, auteur);
		}catch(NumberFormatException e) {
			response.sendRedirect("./action?action=ajouter&error=0");
			return;
		}
		out.println("<html>");
		out.println("<head>");
		out.println("<title>");
		out.println("</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div>");
		out.println("Vous venez d'ajouter " + nom + " dans la base de donnée de la mediathèque.");
		out.println("</br><form action='home' method='get'>");
		out.println("<input type='submit' value='Accueil'/>");
		out.println("</form>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
}
