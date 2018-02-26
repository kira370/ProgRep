package services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.*;

import utilisateur.TypeUtilisateur;
import utilisateur.Utilisateur;



public class Home extends HttpServlet{
	private static final long serialVersionUID = 1L;

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
		
		if(request.getParameter("error") != null) {
			switch(request.getParameter("error")) {
			case "0":
				out.println("<h4>Ce Livre est déjà emprunté.</h4>");
				break;
			case "1":
				out.println("<h4>un probleme est survenue, veuillez reessayer.</h4>");
				break;
			}
		}
		
		out.println("Bonjour " + user.getNom() +" !");
		out.println("<div>");
		out.println("<form action='action' method='GET'></br>");
		if(user.getType().equals(TypeUtilisateur.ABONNE)) {
			out.println("<input type='submit' name='action' value='emprunter'>");
			out.println("<input type='submit' name='action' value='retourner'>");
		}else {
			out.println("<input type='submit' name='action' value='ajouter'>");
		}
		out.println("</form></br>");
		out.println("<form action='deconnexion' method='GET'>");
		out.println("<input type='submit' value='Deconnexion'>");
		out.println("</form>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
}
