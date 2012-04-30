package edu.ycp.cs.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.frogger.controller.GameController;
import edu.ycp.cs496.model.Game;
import edu.ycp.cs496.model.Terrain;
import edu.ycp.cs496.model.persist.PersistenceException;

public class GameServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.getRequestDispatcher("/view/game.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{	
		Game model = (Game) req.getSession().getAttribute("game");
		if (model == null) 
		{
			throw new ServletException("User is not logged in");
		}
		req.getSession().setAttribute("game", model);
		
		GameController controller = new GameController();
		controller.setModel(model);
		
		try 
		{
			if(model.getLevel().isFinished())
			{
				controller.updateMaxLevel(model.getLevel().getLevelNumber());
			}
			req.setAttribute("maxLevel", model.getUser().getMaxLevel());
			req.getSession().setAttribute("game", model);
			req.getRequestDispatcher("/view/levelSelect.jsp").forward(req, resp);
		} 
		catch (PersistenceException e) 
		{
			e.printStackTrace();
		}
		
//		System.out.printf("Enters GameServlet\n");
//		
//		Game model = (Game) req.getSession().getAttribute("game");
//		if (model == null) 
//		{
//			throw new ServletException("User is not logged in");
//		}
//		
//		GameController controller = new GameController();
//		controller.setModel(model);
//		
//		while(!model.getLevel().getCurrentMap().isFinishingPoint(model.getLevel().getCurrentPlayer().getLocation()))
//		{
//			controller.step(0);
//			System.out.printf("Steps\n");
//			req.getSession().setAttribute("game", model);
//			req.getRequestDispatcher("/view/game.jsp").forward(req, resp);
//			try 
//			{
//				Thread.currentThread();
//				Thread.sleep(50);
//			} 
//			catch (InterruptedException e) 
//			{
//				e.printStackTrace();
//			}
//		}
//		try 
//		{
//			controller.updateMaxLevel(model.getLevel().getLevelNumber());
//			req.setAttribute("maxLevel", model.getUser().getMaxLevel());
//			req.getSession().setAttribute("game", model);
//			req.getRequestDispatcher("/view/levelSelect.jsp").forward(req, resp);
//		} 
//		catch (PersistenceException e) 
//		{
//			e.printStackTrace();
//		}		
	}
}
