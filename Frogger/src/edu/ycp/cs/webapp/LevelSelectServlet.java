package edu.ycp.cs.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.frogger.controller.LevelSelectController;
import edu.ycp.cs496.model.Game;
import edu.ycp.cs496.model.Level;
import edu.ycp.cs496.model.persist.PersistenceException;

public class LevelSelectServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.getRequestDispatcher("/view/levelSelect.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Game model = (Game) req.getSession().getAttribute("game");
		if (model == null) 
		{
			throw new ServletException("User is not logged in");
		}
		
		LevelSelectController controller = new LevelSelectController();
		controller.setModel(model);
		
		if(req.getParameter("Level1Button") != null)
		{
			model.setLevel(new Level(1));
			req.getSession().setAttribute("game", model);
			req.getRequestDispatcher("/view/game.jsp").forward(req, resp);
		}
		else if(req.getParameter("Level2Button") != null)
		{
			model.setLevel(new Level(2));
			req.getSession().setAttribute("game", model);
			req.getRequestDispatcher("/view/game.jsp").forward(req, resp);
		}
		else if(req.getParameter("Level3Button") != null)
		{
			model.setLevel(new Level(3));
			req.getSession().setAttribute("game", model);
			req.getRequestDispatcher("/view/game.jsp").forward(req, resp);
		}
		else if(req.getParameter("Level4Button") != null)
		{
			model.setLevel(new Level(4));
			req.getSession().setAttribute("game", model);
			req.getRequestDispatcher("/view/game.jsp").forward(req, resp);
		}
		else if(req.getParameter("Level5Button") != null)
		{
			model.setLevel(new Level(5));
			req.getSession().setAttribute("game", model);
			req.getRequestDispatcher("/view/game.jsp").forward(req, resp);
		}
		else if(req.getParameter("Level6Button") != null)
		{
			model.setLevel(new Level(6));
			req.getSession().setAttribute("game", model);
			req.getRequestDispatcher("/view/game.jsp").forward(req, resp);
		}
		else if(req.getParameter("Level7Button") != null)
		{
			model.setLevel(new Level(7));
			req.getSession().setAttribute("game", model);
			req.getRequestDispatcher("/view/game.jsp").forward(req, resp);
		}
		else if(req.getParameter("Level8Button") != null)
		{
			model.setLevel(new Level(8));
			req.getSession().setAttribute("game", model);
			req.getRequestDispatcher("/view/game.jsp").forward(req, resp);
		}
		else if(req.getParameter("Level9Button") != null)
		{
			model.setLevel(new Level(9));
			req.getSession().setAttribute("game", model);
			req.getRequestDispatcher("/view/game.jsp").forward(req, resp);
		}
		else if(req.getParameter("Level10Button") != null)
		{
			model.setLevel(new Level(10));
			req.getSession().setAttribute("game", model);
			req.getRequestDispatcher("/view/game.jsp").forward(req, resp);
		}
		else
		{
			req.getRequestDispatcher("/view/levelSelect.jsp").forward(req, resp);
		}
	}
}
