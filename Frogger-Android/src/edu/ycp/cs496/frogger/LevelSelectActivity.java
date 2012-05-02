package edu.ycp.cs496.frogger;

import android.app.Activity;
import android.os.Bundle;

public class LevelSelectActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDefaultView();
	}

	public void setDefaultView() {
		setContentView(R.layout.levelselect);
	}
}
