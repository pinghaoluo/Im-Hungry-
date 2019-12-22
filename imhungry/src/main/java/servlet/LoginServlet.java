package servlet;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database_manager.HistoryDataManager;
import database_manager.UserDataManager;
import info.History;



/**
 * Servlet implementation class myFirstServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @throws FileNotFoundException
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() throws FileNotFoundException {
		super();

	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		if(session.isNew() || session.getAttribute("username") == null) {
			session.setAttribute("username", "");
		}
		
		String logoutformSIGNAL = "";
		logoutformSIGNAL = request.getParameter("logoutformSIGNAL");
		if (logoutformSIGNAL.equals("logoutformSIGNAL"))
		{
			session.setAttribute("username", "");
			String next = "/searchPage.jsp";
	        session.setAttribute("log","logout");
	        session.setAttribute("Favorites", null);
			session.setAttribute("To Explore", null);
			session.setAttribute("Do Not Show", null);
			session.setAttribute("Grocery", null);
			session.setAttribute("Quick Access", null);
			RequestDispatcher dispatch = getServletContext().getRequestDispatcher(next);
			dispatch.forward(request, response);
			return;
		}
		String next = "";
		String username = request.getParameter("username");
		String pw = request.getParameter("pw");
		username = username.trim();
		pw = pw.trim();
		if (username.equals("") || pw.equals("")) {
			if (username.equals("")) {
				session.setAttribute("username", "");
				next = "/login.jsp";
				request.setAttribute("uerror", "No Username Entered");
			}
			if (pw.equals("")) {
				session.setAttribute("username", "");
				next = "/login.jsp";
				request.setAttribute("perror", "No Password Entered");
			}
		} else {
			UserDataManager userDB = new UserDataManager(username);
			
			int check = userDB.checkPassword(username,pw);
			if (check != 1) {
				session.setAttribute("username", "");
				next = "/login.jsp";
				request.setAttribute("uerror", "Username or Password wrong");
				request.setAttribute("perror", "Username or Password wrong");
			} else {
				next = "/searchPage.jsp"; //successfully login
				response.setContentType("text/html"); // what's this for?
		        session.setAttribute("log","login");
		        session.setAttribute("username", username);
	        	
		        HistoryDataManager historyDB = new HistoryDataManager(username);
	        	ArrayList<History> quickAccessList;
        		//load quickAccessList from database
    			quickAccessList = historyDB.loadHistory();
    			session.setAttribute("Quick Access", quickAccessList);
        	}
		}
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(next);
		dispatch.forward(request, response);
	}
}