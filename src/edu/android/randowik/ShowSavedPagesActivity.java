package edu.android.randowik;

import edu.android.randowik.db.DbHelper;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ShowSavedPagesActivity extends Activity {
	private static final String TAG = "ShowSavedPagesActivity";
	private DbHelper dbHelper;
	private PagesAdapter pagesAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		dbHelper = new DbHelper(this);

		ListView listView = (ListView) findViewById(R.id.randomTitles);
		pagesAdapter = new PagesAdapter(this, dbHelper.getAllPages());
		listView.setAdapter(pagesAdapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View child,
					int position, long id) {
				Intent intent = new Intent(ShowSavedPagesActivity.this,
						ShowSavedWikiPageActivity.class);
				Cursor cursor = (Cursor) pagesAdapter.getCursor();
				cursor.moveToPosition(position);
				String pageId = cursor.getString(2);
				String title = cursor.getString(3);
				intent.putExtra("title", title);
				intent.putExtra("id", pageId);
				Log.d(TAG, "pageId: " + pageId + "; title: " + title);
				startActivity(intent);
			}

		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbHelper.close();
	}
}
