package services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Deconnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		
		if(session.getAttribute("utilisateur") == null) {
			response.sendRedirect("./loginuser");
			return;
		}
		// ajout d'une variable dans la session
		session.removeAttribute("utilisateur");
		response.sendRedirect("./loginuser");
	}

}
