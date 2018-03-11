package services;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class startAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession(true);
		Object isAdmin = session.getAttribute("admin");
		if(isAdmin == null) {
			response.sendRedirect("./loginadmin?error=1");
			return;
		}
		try {
			Class.forName("mediatheque.Mediatheque");
			Class.forName("persistantdata.MediathequeData");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("./loginuser");
	}
	


}
