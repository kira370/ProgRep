package services;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;

import mediatheque.Document;
import mediatheque.EmpruntException;
import mediatheque.Mediatheque;
import utilisateur.Utilisateur;

public class Emprunter extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(true);
		Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
		if(user == null) {
			response.sendRedirect("./loginuser?error=3");
			return;
		}
		int numeroDoc;
		Document doc;
		try {
			numeroDoc = Integer.parseInt(request.getParameter("idDoc"));
			Mediatheque media = Mediatheque.getInstance();
			doc = media.getDocument(numeroDoc);
			media.emprunt(doc, user);
		}catch(NumberFormatException | NullPointerException e) {
			response.sendRedirect("./home?error=1");
			return;
		} catch (EmpruntException e) {
			response.sendRedirect("./home?error=0");
			return;
		}

		out.println("<html>");
		out.println("<head>");
		out.println("<title>");
		out.println("</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div>");
		out.println("Vous venez d'emprunter " + doc.affiche()[1]);
		out.println("</br><form action='home' method='get'>");
		out.println("<input type='submit' value='Accueil'/>");
		out.println("</form>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
}
