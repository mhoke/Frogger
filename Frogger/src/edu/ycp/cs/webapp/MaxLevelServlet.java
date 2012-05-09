package edu.ycp.cs.webapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import edu.ycp.cs496.model.User;
import edu.ycp.cs496.model.persist.Database;

public class MaxLevelServlet extends HttpServlet 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		SAXReader r = new SAXReader();
		
		Document doc = DocumentHelper.createDocument();

		try
		{
			doc = r.read(req.getInputStream());
			
			String username = doc.selectSingleNode("//Information/Username").getText();
			String password = doc.selectSingleNode("//Information/Password").getText();
			
			doc = DocumentHelper.createDocument();
			Element parent = doc.addElement("Level");
			
			int Level = Database.getInstance().getMaxLevelNumber(new User(username, password));
			
			String storedLevel;
			
			switch(Level)
			{
				case 1:
					storedLevel = "ONE";
					break;
				case 2:
					storedLevel = "TWO";
					break;
				case 3:
					storedLevel = "THREE";
					break;
				case 4:
					storedLevel = "FOUR";
					break;
				case 5:
					storedLevel = "FIVE";
					break;
				case 6:
					storedLevel = "SIX";
					break;
				case 7:
					storedLevel = "SEVEN";
					break;
				case 8:
					storedLevel = "EIGHT";
					break;
				case 9:
					storedLevel = "NINE";
					break;
				case 10:
					storedLevel = "TEN";
					break;
				default:
					storedLevel = "";
					break;
			}
			
			parent.setText(storedLevel);
			
			OutputFormat format = OutputFormat.createPrettyPrint();
			resp.setContentType("text/xml");
			XMLWriter writer = new XMLWriter(resp.getWriter(), format);
			writer.write(doc);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
