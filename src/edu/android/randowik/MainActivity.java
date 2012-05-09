package edu.android.randowik;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.app_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.item_settings) {
			SettingsDialog settings = new SettingsDialog(this);
			settings.show();
			return true;
		}
		if (item.getItemId() == R.id.item_about) {
			AboutDialog about = new AboutDialog(this);
			about.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
