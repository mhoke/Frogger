package edu.ycp.cs496.frogger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import edu.ycp.cs496.model.Game;
import edu.ycp.cs496.model.Terrain;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback
{
	
    private ViewThread mThread = null;
    private Game game;

	public DrawView(Context context, Game game) 
	{
		super(context);
		getHolder().addCallback(this);
		this.game = game;
	}
	
	public DrawView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		getHolder().addCallback(this);
		mThread = new ViewThread(this);
		game = FroggerActivity.getGame();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
		if (!mThread.isAlive()) 
		{				
		      mThread.setRunning(true);
		      mThread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		// Stop thread
		   if (mThread.isAlive()) 
		   {
		      mThread.setRunning(false);
		   }
	}

	public void update() 
	{
		synchronized (game) 
		{
			//If player hit an enemy
			if(game.step(FroggerActivity.getKey()))
			{
				//Return to level select screen
				FroggerActivity.setFlag(1);
			}
			else if(game.getLevel().isFinished())
			{
				FroggerActivity.setFlag(2);
			}
		}
	}

	public void doDraw(Canvas canvas) 
	{
		synchronized (game)
		{
			Paint paint = new Paint();
			int index = 0;
			char ch;
			int offset = 40;
			
			for(int i = 0; i < 10; i ++)
			{
				for(int j = 0; j < 10; j ++)
				{				
					index = i * 10 + j;
					ch = game.getLevel().getCurrentMap().getEncodedRepresentation().charAt(index);
					
					if(ch == 'a')
					{
						paint.setColor(Color.BLACK);
						canvas.drawRect(i * offset, (10 - j) * offset, i * offset + offset, (10 - j) * offset + offset, paint);
					}
					if(ch == 'b')
					{
						if(game.getLevel().getCurrentPlayer().getPrevVal() == Terrain.FINISH.getValue() || game.getLevel().getCurrentPlayer().getPrevVal() == Terrain.START.getValue())
						{
							paint.setColor(Color.GREEN);
							canvas.drawRect(i * offset, (10 - j) * offset, i * offset + offset, (10 - j) * offset + offset, paint);
						}
						else
						{
							paint.setColor(Color.WHITE);
							canvas.drawRect(i * offset, (10 - j) * offset, i * offset + offset, (10 - j) * offset + offset, paint);	
						}
						paint.setColor(Color.BLUE);
						canvas.drawCircle(i * offset + offset/2, (10 - j) * offset + offset/2, offset/2, paint);
					}
					if(ch == 'c')
					{
						paint.setColor(Color.WHITE);
						canvas.drawRect(i * offset, (10 - j) * offset, i * offset + offset, (10 - j) * offset + offset, paint);
						
						paint.setColor(Color.rgb(255, 215, 0));
						canvas.drawCircle(i * offset + offset/2, (10 - j) * offset + offset/2, offset/4, paint);
					}
					if(ch == 'd')
					{
						paint.setColor(Color.WHITE);
						canvas.drawRect(i * offset, (10 - j) * offset, i * offset + offset, (10 - j) * offset + offset, paint);
						
						paint.setColor(Color.RED);
						canvas.drawCircle(i * offset + offset/2, (10 - j) * offset + offset/2, offset/2, paint);
					}
					if(ch == 'e' || ch == 'f')
					{
						paint.setColor(Color.GREEN);
						canvas.drawRect(i * offset, (10 - j) * offset, i * offset + offset, (10 - j) * offset + offset, paint);
					}
					if(ch == 'g')
					{
						paint.setColor(Color.WHITE);
						canvas.drawRect(i * offset, (10 - j) * offset, i * offset + offset, (10 - j) * offset + offset, paint);
					}
				}
			}
		}
	}
}
