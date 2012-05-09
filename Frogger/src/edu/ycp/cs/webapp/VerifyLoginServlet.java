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

public class VerifyLoginServlet extends HttpServlet 
{
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
			
			String method = doc.selectSingleNode("//Information/Method").getText();
			
			String username = doc.selectSingleNode("//Information/Username").getText();
			String password = doc.selectSingleNode("//Information/Password").getText();
			
			doc = DocumentHelper.createDocument();
			Element parent = doc.addElement("Continue");
			
			if(method.equals("LOGIN"))
			{								
				if(Database.getInstance().getUser(username, password) != null)
				{
					parent.setText("YES");
				}
				else
				{
					parent.setText("NO");
				}
			}
			else if(method.equals("CREATEACCOUNT"))
			{
				if (!Database.getInstance().userExists(username))
				{
				    // Create account				    
				    Database.getInstance().addUser(new User(username, password));
				    parent.setText("YES");
			    }
				else
				{
					parent.setText("NO");
				}
			}
			
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
