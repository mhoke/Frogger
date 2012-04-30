package edu.ycp.cs.webapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs.frogger.controller.GameController;
import edu.ycp.cs496.model.Game;
import edu.ycp.cs496.model.Terrain;
import edu.ycp.cs496.model.persist.PersistenceException;

public class UpdateGame extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Game model = (Game) req.getSession().getAttribute("game");
		if (model == null) 
		{
			throw new ServletException("User is not logged in");
		}
		
		GameController controller = new GameController();
		controller.setModel(model);
		resp.setContentType("application/json");
		
		PrintWriter out = resp.getWriter();
		boolean result = controller.step(Integer.parseInt(req.getParameter("key")));
		
		out.print("{ \"map\": \"");
		
		if(model.getLevel().isFinished() || result)
		{
			out.print("0");
		}
		else
		{
			out.print(model.getLevel().getCurrentMap().getEncodedRepresentation());
		}
		
		out.print("\" , \"green\": \"");
		
		if(model.getLevel().getCurrentPlayer().getPrevVal() == Terrain.START.getValue() || model.getLevel().getCurrentPlayer().getPrevVal() == Terrain.FINISH.getValue())
		{
			out.print("1");
		}
		else
		{
			out.print("0");
		}
		
		out.println("\" }");
	}
}
