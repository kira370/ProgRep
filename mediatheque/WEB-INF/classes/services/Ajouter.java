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
		if(type.equals("0")){
			response.sendRedirect("./action?action=ajouter&error=0");
			return;
		}
		Object[] args = null;
		
		switch(type){
		case "1":
			args = new Object[] { request.getParameter("nom") , request.getParameter("auteur") , Integer.parseInt(request.getParameter("annee"))};
			break;
		case "2":
			args = new Object[] { request.getParameter("nom") , request.getParameter("auteur") , request.getParameter("compositeur"), Integer.parseInt(request.getParameter("annee"))};
			break;
		case "3":
			args = new Object[] { request.getParameter("nom") , request.getParameter("realisateur") , request.getParameter("producteur"), Integer.parseInt(request.getParameter("sortie"))};
		}
		
		if(nom.equals("") || auteur.equals("")) {
			response.sendRedirect("./action?action=ajouter&error=1");
			return;
		}
		
		try {
			Mediatheque media = Mediatheque.getInstance();
			media.nouveauDocument(Integer.parseInt(type),args);
		}catch(NumberFormatException e) {
			e.printStackTrace();
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
