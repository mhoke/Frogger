package edu.ycp.cs496.frogger;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ViewThread extends Thread 
{
	private DrawView view;
	private SurfaceHolder mHolder;
	private boolean mRun = false;
	private long mStartTime;
	private long mElapsed;
	
	public ViewThread(DrawView view) 
	{
	   this.view = view;
	   mHolder = view.getHolder();
	}
	
	// Set current thread state
	public void setRunning(boolean run) 
	{
	   mRun = run;
	}

	@Override
	public void run() 
	{
		Canvas canvas = null;
		
		// Retrieve time when thread starts
		mStartTime = System.currentTimeMillis();
		
		canvas = mHolder.lockCanvas();
		if(canvas != null)
		{
			view.update();
			view.doDraw(canvas);
			mHolder.unlockCanvasAndPost(canvas);
		}
		
		// Thread loop
		while (mRun) 
		{
			// Obtain lock on canvas object
			canvas = mHolder.lockCanvas();
			
			if (canvas != null) 
			{
				// Update state based on elapsed time
				mElapsed = System.currentTimeMillis() - mStartTime;
				
				view.doDraw(canvas);
				
				if(mElapsed >= 250)
				{
					view.update();
					mStartTime = System.currentTimeMillis();
				}
				
				view.doDraw(canvas);
				
				// Release lock on canvas object
				mHolder.unlockCanvasAndPost(canvas);
			}
		}
	}
}
