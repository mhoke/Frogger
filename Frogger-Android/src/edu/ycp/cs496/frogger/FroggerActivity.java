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
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.ycp.cs496.model.Game;
import edu.ycp.cs496.model.Level;
import edu.ycp.cs496.model.User;

public class FroggerActivity extends Activity
{
	private String Username;
	private String Password;
	private int levelNum;
	private static Game game;
	private static int key;
	private static int flag = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		game = new Game();
		setLoginView();
	}

	public void setLoginView() 
	{
		setContentView(R.layout.login);
		
		((TextView) findViewById(R.id.editText2)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

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
				    	
				    	String Continue = (String) xpath.evaluate("//Continue", doc, XPathConstants.STRING);
				    	
				    	if(Continue.equals("YES"))
				    	{
				    		//Go to level select
				    		Username = username;
				    		Password = password;
				    		setLevelSelectView();
				    	}
				    	else
				    	{
				    		Toast.makeText(FroggerActivity.this, "Invalid information", Toast.LENGTH_SHORT).show();
				    	}
					} 
					catch (Exception e) 
					{
						Toast.makeText(FroggerActivity.this, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
				else
				{
					Toast.makeText(FroggerActivity.this, "Fill in both fields", Toast.LENGTH_SHORT).show();
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
				  
				  String username = ((TextView) findViewById(R.id.editText1)).getText().toString(); 
				  String password = ((TextView) findViewById(R.id.editText2)).getText().toString();
				  
				  
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
					    		Username = username;
					    		Password = password;
					    		setLevelSelectView();
					    	}
					    	else
					    	{
					    		Toast.makeText(FroggerActivity.this, "Account Already Exists", Toast.LENGTH_SHORT).show();
					    	}
						} 
						catch (Exception e) 
						{
							Toast.makeText(FroggerActivity.this, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
						}
				  }
				  else
				  {
					  Toast.makeText(FroggerActivity.this, "Fill in both fields", Toast.LENGTH_SHORT).show();
				  }
			}
		});		 
	}
	
	public void setLevelSelectView() 
	{
		setContentView(R.layout.levelselect);
		
		flag = 0;
		
		try
		{
			// Create HTTP client
			HttpClient client = new DefaultHttpClient();
	
			HttpPost request = new HttpPost("http://10.0.2.2:8081/frogger/maxLevel");
			
			StringEntity entity = new StringEntity("<Information>" +
					"<Username>" + Username + "</Username>" +
					"<Password>" + Password + "</Password></Information>");
			
			entity.setContentType("text/xml");
			
			request.setEntity(entity);
			
			HttpResponse response = client.execute(request);
			
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	    	
	    	builderFactory.setNamespaceAware(true);
	    	
	    	DocumentBuilder builder = builderFactory.newDocumentBuilder();
	    	
	    	Document doc = builder.parse(response.getEntity().getContent());
	    	
	    	XPath xpath = XPathFactory.newInstance().newXPath();
	    	
	    	String Level = (String) xpath.evaluate("//Level", doc, XPathConstants.STRING);
	    	
	    	int MLevel = 1;
	    	
	    	if(Level.equals("ONE"))
	    	{
	    		MLevel = 1;
	    	}
	    	else if(Level.equals("TWO"))
	    	{
	    		MLevel = 2;
	    	}
	    	else if(Level.equals("THREE"))
	    	{
	    		MLevel = 3;
	    	}
	    	else if(Level.equals("FOUR"))
	    	{
	    		MLevel = 4;
	    	}
	    	else if(Level.equals("FIVE"))
	    	{
	    		MLevel = 5;
	    	}
	    	else if(Level.equals("SIX"))
	    	{
	    		MLevel = 6;
	    	}
	    	else if(Level.equals("SEVEN"))
	    	{
	    		MLevel = 7;
	    	}
	    	else if(Level.equals("EIGHT"))
	    	{
	    		MLevel = 8;
	    	}
	    	else if(Level.equals("NINE"))
	    	{
	    		MLevel = 9;
	    	}
	    	else if(Level.equals("TEN"))
	    	{
	    		MLevel = 10;
	    	}
	    	
	    	if(MLevel < 2)
	    	{
	    		((Button) findViewById(R.id.Button01)).setEnabled(false);
	    	}
	    	if(MLevel < 3)
	    	{
	    		((Button) findViewById(R.id.Button02)).setEnabled(false);
	    	}
	    	if(MLevel < 4)
	    	{
	    		((Button) findViewById(R.id.Button03)).setEnabled(false);
	    	}
	    	if(MLevel < 5)
	    	{
	    		((Button) findViewById(R.id.Button04)).setEnabled(false);
	    	}
	    	if(MLevel < 6)
	    	{
	    		((Button) findViewById(R.id.Button05)).setEnabled(false);
	    	}
	    	if(MLevel < 7)
	    	{
	    		((Button) findViewById(R.id.Button06)).setEnabled(false);
	    	}
	    	if(MLevel < 8)
	    	{
	    		((Button) findViewById(R.id.Button07)).setEnabled(false);
	    	}
	    	if(MLevel < 9)
	    	{
	    		((Button) findViewById(R.id.Button08)).setEnabled(false);
	    	}
	    	if(MLevel < 10)
	    	{
	    		((Button) findViewById(R.id.Button09)).setEnabled(false);
	    	}
	    	
	    	final int maxLevel = MLevel;
	    	
	    	game.setUser(new User(Username, Password));
	    	
	    	final Button Level1 = (Button) findViewById(R.id.button1);
	    	Level1.setOnClickListener(new View.OnClickListener() 
			{
				@Override
				public void onClick(View v) 
				{
					if(maxLevel >= 1)
					{
						levelNum = 1;
						game.setLevel(new Level(1));
						setGameView();
					}
				} 		
			});
	    	
	    	final Button Level2 = (Button) findViewById(R.id.Button01);
	    	Level2.setOnClickListener(new View.OnClickListener()
	    	{				
				public void onClick(View v) 
				{
					if(maxLevel >= 2)
					{
						levelNum = 2;
						game.setLevel(new Level(2));
						setGameView();
					}
				}
			});
	    	
	    	final Button Level3 = (Button) findViewById(R.id.Button02);
	    	Level3.setOnClickListener(new View.OnClickListener()
	    	{				
				public void onClick(View v) 
				{
					if(maxLevel >= 3)
					{
						levelNum = 3;
						game.setLevel(new Level(3));
						setGameView();
					}
				}
			});
	    	
	    	final Button Level4 = (Button) findViewById(R.id.Button03);
	    	Level4.setOnClickListener(new View.OnClickListener()
	    	{				
				public void onClick(View v) 
				{
					if(maxLevel >= 4)
					{
						levelNum = 4;
						game.setLevel(new Level(4));
						setGameView();
					}
				}
			});
	    	
	    	final Button Level5 = (Button) findViewById(R.id.Button04);
	    	Level5.setOnClickListener(new View.OnClickListener()
	    	{				
				public void onClick(View v) 
				{
					if(maxLevel >= 5)
					{
						levelNum = 5;
						game.setLevel(new Level(5));
						setGameView();
					}
				}
			});
	    	
	    	final Button Level6 = (Button) findViewById(R.id.Button05);
	    	Level6.setOnClickListener(new View.OnClickListener()
	    	{				
				public void onClick(View v) 
				{
					if(maxLevel >= 6)
					{
						levelNum = 6;
						game.setLevel(new Level(6));
						setGameView();
					}
				}
			});
	    	
	    	final Button Level7 = (Button) findViewById(R.id.Button06);
	    	Level7.setOnClickListener(new View.OnClickListener()
	    	{				
				public void onClick(View v) 
				{
					if(maxLevel >= 7)
					{
						levelNum = 7;
						game.setLevel(new Level(7));
						setGameView();
					}
				}
			});
	    	
	    	final Button Level8 = (Button) findViewById(R.id.Button07);
	    	Level8.setOnClickListener(new View.OnClickListener()
	    	{				
				public void onClick(View v) 
				{
					if(maxLevel >= 8)
					{
						levelNum = 8;
						game.setLevel(new Level(8));
						setGameView();
					}
				}
			});
	    	
	    	final Button Level9 = (Button) findViewById(R.id.Button08);
	    	Level9.setOnClickListener(new View.OnClickListener()
	    	{				
				public void onClick(View v) 
				{
					if(maxLevel >= 9)
					{
						levelNum = 9;
						game.setLevel(new Level(9));
						setGameView();
					}
				}
			});
	    	
	    	final Button Level10 = (Button) findViewById(R.id.Button09);
	    	Level10.setOnClickListener(new View.OnClickListener()
	    	{				
				public void onClick(View v) 
				{
					if(maxLevel >= 10)
					{
						levelNum = 10;
						game.setLevel(new Level(10));
						setGameView();
					}
				}
			});
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	public void setGameView()
	{	
		setContentView(R.layout.game);		
		
		new DrawView(this, game);
		
		new Thread() 
		{
			public void run() 
			{
				while(flag == 0)
				{
					try 
					{
						Thread.sleep(250);
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
				runOnUiThread(new Runnable() 
				{
			        @Override
			        public void run() 
			        {
			            LSV();
			        }
			    });

			}
		}.start();
	}
	
	public void LSV()
	{
		if(flag == 2)
		{
			try
			{
				// Create HTTP client
				HttpClient client = new DefaultHttpClient();
		
				HttpPost request = new HttpPost("http://10.0.2.2:8081/frogger/updateMaxLevel");
				
				StringEntity entity = new StringEntity("<Information>" +
						"<Username>" + Username + "</Username>" +
						"<Password>" + Password + "</Password>" +
						"<Level>" + levelNum + "</Level></Information>");
				
				entity.setContentType("text/xml");
				
				request.setEntity(entity);
				
				client.execute(request);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		setLevelSelectView();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent msg)
	{
		switch(keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_DOWN:
				key = 40;
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				key = 39;
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				key = 38;
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				key = 37;
				break;
			default:
				key = 0;
				break;
		}
		
		return true;
	}
	
	public static Game getGame()
	{
		return game;
	}
	
	public static int getKey()
	{
		int temp = key;
		key = 0;
		return temp;
	}
	
	public static void setFlag(int i)
	{
		flag = i;
	}
}
