package edu.ycp.cs.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

import edu.ycp.cs496.model.User;
import edu.ycp.cs496.model.persist.Database;

public class updateMaxLevel extends HttpServlet 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		SAXReader r = new SAXReader();
		
		Document doc = DocumentHelper.createDocument();

		try
		{
			doc = r.read(req.getInputStream());
			
			int level = Integer.parseInt(doc.selectSingleNode("//Information/Level").getText());
			
			String username = doc.selectSingleNode("//Information/Username").getText();
			String password = doc.selectSingleNode("//Information/Password").getText();
			
			if(level == Database.getInstance().getMaxLevelNumber(new User(username, password)) && level != 10)
			{
				Database.getInstance().incrementMaxLevel(new User(username, password), ++level);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
}
