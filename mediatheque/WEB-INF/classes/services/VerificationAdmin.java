package services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
;

public class VerificationAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if((request.getParameter("username") == null) || (request.getParameter("password") == null)) {
			response.sendRedirect("./login?error=0");
			return;
		}
		boolean admin = false;
		try {
			String username = request.getParameter("username");
			String pass = request.getParameter("password");
			//si la mediatheque renvoi null, alors redirection vers la page login
			if(!(username.equals("admin") && pass.equals("123"))) 
			{
				admin = true;
			}
		}catch(NullPointerException e) {}
		
		if(!admin) {
			response.sendRedirect("./loginadmin?error=1");
			return;
		}
		
		HttpSession session = request.getSession(true);
		session.setAttribute("admin", true);
		PrintWriter out = response.getWriter();
		out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>");
		out.println("</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div>");
		out.println("<form action='startadmin' method='POST'>");
		out.println("<input type='submit' value='Allumer'>");
		out.println("</form>");
		out.println("");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

}
