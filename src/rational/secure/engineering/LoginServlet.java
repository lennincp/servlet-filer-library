package rational.secure.engineering;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@SuppressWarnings("serial")
@WebServlet(description = "Login Servlet", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("running doPost()...");
		
		try {
			PasswordManagerHash.init(getServletContext(), getClass(), "pwdFileHash.txt");

			String username = request.getParameter("username");
			String password = request.getParameter("password");

			if (PasswordManagerHash.checkPassword(username, password)) {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/login_successful.jsp");
				rd.include(request, response);
			} else {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/login_failure.jsp");
				rd.include(request, response);
			}

		} catch (Exception e) {
			PasswordManagerHash.print(e.getMessage());
		}
	}
}