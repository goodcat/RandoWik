package edu.android.randowik;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void buttonRandomPagesClicked(View view) {
		Intent intent = new Intent(this, RandoWikActivity.class);
		startActivity(intent);
	}

	public void buttonSavedPagesClicked(View view) {
		Intent intent = new Intent(this, ShowSavedPagesActivity.class);
		startActivity(intent);
	}
}
