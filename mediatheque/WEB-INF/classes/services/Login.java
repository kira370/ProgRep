package services;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.*;

public class Login extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(true);
		if(session.getAttribute("utilisateur") != null) {
			response.sendRedirect("./home");
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
				out.println("<h4>Veuillez remplir les deux champs.</h4>");
				break;
			case "1":
				out.println("<h4>Le nom d'utilisateur ou mot de passe n'est pas bon.</h4>");
				break;
			case "3":
				out.println("<h4>Vous devez vous connectez pour acceder à cette partie su site.</h4>");
				break;
			}
		}
		
		
		out.println("<div>");
		out.println("<form action='verification' method='POST'>");
		out.println("Nom d'utilisateur:<br>");
		out.println("<input type='text' name='username' value='delannoy'>");
		out.println("<br>");
		out.println("Mot de passe:<br>");
		out.println("<input type='password' name='password' value='delannoy'>");
		out.println("<br>");
		out.println("<input type='submit' value='Submit'>");
		out.println("</form>"); 

		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}
}
