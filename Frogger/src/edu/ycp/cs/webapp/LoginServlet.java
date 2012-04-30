package edu.ycp.cs.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.frogger.controller.LoginController;
import edu.ycp.cs496.model.Game;
import edu.ycp.cs496.model.persist.PersistenceException;

public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Game model = new Game();
		
		LoginController controller = new LoginController();
		controller.setModel(model);
		
		String username = req.getParameter("usernameBox");
		String password = req.getParameter("passwordBox");
		
		String errorMessage = null;
		
		boolean result = false;
		if (req.getParameter("loginButton") != null){
			try {
				result = controller.login(username, password);
				if(!result)
				{
					errorMessage = "Login failed";
				}
				else
				{
					errorMessage = "Login successful";
					req.getSession().setAttribute("game", model);
				}
			} catch (PersistenceException e) {
				System.out.print("Login database fail");
			}
		}
		
		else if (req.getParameter("createButton") != null){
			try {
				result = controller.createAccount(username, password);
				if(!result)
				{
					errorMessage = "Account already exists";
				}
				else
				{
					errorMessage = "Account successfully created";
				}
			} catch (PersistenceException e) {
				System.out.print("Create database fail");
			}
		}
		
		req.setAttribute("errorMessage", errorMessage);
		
		if (result && !errorMessage.equals("Account successfully created")){
			try 
			{
				req.setAttribute("maxLevel", model.getUser().getMaxLevel());
			} 
			catch (PersistenceException e) 
			{
				e.printStackTrace();
			}
			req.getRequestDispatcher("/view/levelSelect.jsp").forward(req, resp);
		} else {
			req.getRequestDispatcher("/view/login.jsp").forward(req, resp);
		}
		
	}
	
}
