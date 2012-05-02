package edu.ycp.cs496.frogger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.ycp.cs496.model.User;
import edu.ycp.cs496.model.persist.Database;
import edu.ycp.cs496.model.persist.PersistenceException;

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

				String username = ((TextView) findViewById(R.id.editText1)).toString();
				String password = ((TextView) findViewById(R.id.editText2)).toString();
				
				if(!username.equals("") && !password.equals(""))
				{
					try 
					{
						User user = Database.getInstance().getUser(username, password);
						if (user != null) 
						{
							// Go to level Select Screen
							Intent intent = new Intent(LoginActivity.this, LevelSelectActivity.class);
							startActivity(intent);
							finish();
						} 
						else 
						{
							Toast.makeText(LoginActivity.this, "Username/Password combination does not exist", Toast.LENGTH_SHORT).show();
						}
					} 
					catch (PersistenceException e) 
					{
						e.printStackTrace();
					}
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
						  if(!Database.getInstance().userExists(username)) 
						  { 
							  //Go to level Select Screen
							  Database.getInstance().addUser(new User(username, password));
							  Intent intent = new Intent(LoginActivity.this, LevelSelectActivity.class);
							  startActivity(intent);
							  finish();
						  } 
						  else 
						  {
							  Toast.makeText(LoginActivity.this, "Username/Password combination already exists", Toast.LENGTH_SHORT).show(); 
						  } 
					  } 
					  catch (PersistenceException e) 
					  {
						  e.printStackTrace(); 
					  }
				  }
			}
		});		 
	}
}
