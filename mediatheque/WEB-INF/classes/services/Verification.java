package services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mediatheque.Mediatheque;
import utilisateur.Utilisateur;

public class Verification extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if((request.getParameter("username") == null) || (request.getParameter("password") == null)) {
			response.sendRedirect("./loginuser?error=0");
			return;
		}

		String username = request.getParameter("username");
		String pass = request.getParameter("password");
		Utilisateur user = null;
		try {
			user = Mediatheque.getInstance().getUser(username, pass);
		}catch(NullPointerException e) {}
		
		//si la mediatheque renvoi null, alors redirection vers la page login
		if(user == null) {
			response.sendRedirect("./loginuser?error=1");
			return;
		}
		
		// ajout d'une variable dans la session
		HttpSession session = request.getSession(true);
		session.setAttribute("utilisateur", user);
		response.sendRedirect("./home");
	}

}
