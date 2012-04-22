package edu.android.randowik;

import edu.android.randowik.bot.Page;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class RandoWikActivity extends Activity {
	RandomTitlesAdapter titlesAdapter;
	Handler handler;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.random_pages_list);

		handler = new Handler();

		ListView listView = (ListView) findViewById(R.id.randomTitles);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View child, int position, long id) {
				Page page = (Page) titlesAdapter.getItem(position);
				Intent intent = new Intent(RandoWikActivity.this, WebViewWikiPageActivity.class);
				intent.putExtra("pageUrl", page.getPageUrl());
				intent.putExtra("title", page.getTitle());
				intent.putExtra("id", page.getId());
				startActivity(intent);
			}

		});

		process();
	}

	public void onRefreshClicked(View view) {
		process();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.show_saved_pages) {
			Intent intent = new Intent(this, ShowSavedPagesActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void process() {
		final ProgressDialog dialog = ProgressDialog.show(this, "Refreshing", "Loading random wiki articles");
		Thread th = new Thread() {
			public void run() {
				refreshData();
				handler.post(new Runnable() {

					@Override
					public void run() {
						ListView listView = (ListView) findViewById(R.id.randomTitles);
						listView.setAdapter(titlesAdapter);
						dialog.dismiss();
					}
				});
			}
		};
		th.start();
	}

	private void refreshData() {
		titlesAdapter = new RandomTitlesAdapter();
	}
}