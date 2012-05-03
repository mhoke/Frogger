package edu.ycp.cs496.frogger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity 
{
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setDefaultView();
	}

	public void setDefaultView() 
	{
		setContentView(R.layout.login);

		final Button LoginButton = (Button) findViewById(R.id.LoginButton);
		LoginButton.setOnClickListener(new View.OnClickListener() 
		{

			public void onClick(View v) 
			{
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(LoginButton.getWindowToken(), 0);			

				String username = ((TextView) findViewById(R.id.editText1)).getText().toString();
				String password = ((TextView) findViewById(R.id.editText2)).getText().toString();
				
				if(!username.equals("") && !password.equals(""))
				{					
					try 
					{
						// Create HTTP client
						HttpClient client = new DefaultHttpClient();

						HttpPost request = new HttpPost("http://10.0.2.2:8081/frogger/verifyLogin");
						
						StringEntity entity = new StringEntity("<Information><Method>LOGIN</Method>" +
								"<Username>" + username + "</Username>" +
								"<Password>" + password + "</Password></Information>");
						
						entity.setContentType("text/xml");
						
						request.setEntity(entity);
						
						HttpResponse response = client.execute(request);
						
						DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				    	
				    	builderFactory.setNamespaceAware(true);
				    	
				    	DocumentBuilder builder = builderFactory.newDocumentBuilder();
				    	
				    	Document doc = builder.parse(response.getEntity().getContent());
				    	
				    	XPath xpath = XPathFactory.newInstance().newXPath();
				    	
				    	String Continue = (String) xpath.evaluate("//Exists", doc, XPathConstants.STRING);
				    	
				    	if(Continue.equals("YES"))
				    	{
				    		//Go to level select
				    		Intent intent = new Intent(LoginActivity.this, LevelSelectActivity.class);
				    	    startActivity(intent);
				    	    finish();
				    	}
				    	else
				    	{
				    		Toast.makeText(LoginActivity.this, "Invalid information", Toast.LENGTH_SHORT).show();
				    	}
					} 
					catch (Exception e) 
					{
						Toast.makeText(LoginActivity.this, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
				else
				{
					Toast.makeText(LoginActivity.this, "Fill in both fields", Toast.LENGTH_SHORT).show();
				}
			}
		});

		
		  final Button CreateAccountButton = (Button) findViewById(R.id.CreateAccountButton);
		  CreateAccountButton.setOnClickListener(new View.OnClickListener() 
		  {
		  
			  public void onClick(View v) 
			  { 
				  InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				  imm.hideSoftInputFromWindow(CreateAccountButton.getWindowToken(), 0);
				  
				  String username = ((TextView) findViewById(R.id.editText1)).toString(); 
				  String password = ((TextView) findViewById(R.id.editText2)).toString();
				  
				  
				  if(!username.equals("") && !password.equals(""))
				  {
					  try 
					  {
							// Create HTTP client
							HttpClient client = new DefaultHttpClient();
	
							HttpPost request = new HttpPost("http://10.0.2.2:8081/frogger/verifyLogin");
							
							StringEntity entity = new StringEntity("<Information><Method>CREATEACCOUNT</Method>" +
									"<Username>" + username + "</Username>" +
									"<Password>" + password + "</Password></Information>");
							
							entity.setContentType("text/xml");
							
							request.setEntity(entity);
							
							HttpResponse response = client.execute(request);
							
							DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
					    	
					    	builderFactory.setNamespaceAware(true);
					    	
					    	DocumentBuilder builder = builderFactory.newDocumentBuilder();
					    	
					    	Document doc = builder.parse(response.getEntity().getContent());
					    	
					    	XPath xpath = XPathFactory.newInstance().newXPath();
					    	
					    	String Continue = (String) xpath.evaluate("//Continue", doc, XPathConstants.STRING);
					    	
					    	if(Continue.equals("YES"))
					    	{
					    		//Go to level select
					    		Intent intent = new Intent(LoginActivity.this, LevelSelectActivity.class);
					    	    startActivity(intent);
					    	    finish();
					    	}
					    	else
					    	{
					    		Toast.makeText(LoginActivity.this, "Account Already Exists", Toast.LENGTH_SHORT).show();
					    	}
						} 
						catch (Exception e) 
						{
							Toast.makeText(LoginActivity.this, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
						}
				  }
				  else
				  {
					  Toast.makeText(LoginActivity.this, "Fill in both fields", Toast.LENGTH_SHORT).show();
				  }
			}
		});		 
	}
}
