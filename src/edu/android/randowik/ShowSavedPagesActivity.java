package edu.android.randowik;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import edu.android.randowik.db.DbHelper;

public class ShowSavedPagesActivity extends Activity {
	private static final String TAG = "ShowSavedPagesActivity";
	private DbHelper dbHelper;
	private PagesAdapter pagesAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saved_pages_list);

		dbHelper = new DbHelper(this);

		ListView listView = (ListView) findViewById(R.id.savedPages);
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
				String pageId = cursor.getString(0);
				String title = cursor.getString(2);
				intent.putExtra("title", title);
				intent.putExtra("id", pageId);
				Log.d(TAG, "pageId: " + pageId + "; title: " + title);
				startActivity(intent);
				cursor.close();
			}

		});
		
		registerForContextMenu(listView);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if(v.getId() == R.id.savedPages) {
			menu.setHeaderTitle("Редактировать");
			menu.add(Menu.NONE, 0, 0, "Удалить");
		}
	}
	
	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Удалить запись?")
				.setCancelable(false)
				.setPositiveButton("Да", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
						int pageIndex = info.position;
						Cursor cursor = (Cursor) pagesAdapter.getCursor();
						cursor.moveToPosition(pageIndex);
						int pageId = cursor.getInt(0);
						cursor.close();
						dbHelper.deleteById(String.valueOf(pageId));
						pagesAdapter.notifyDataSetChanged();
						ListView listView = (ListView) findViewById(R.id.savedPages);
						pagesAdapter = new PagesAdapter(ShowSavedPagesActivity.this, dbHelper.getAllPages());
						listView.setAdapter(pagesAdapter);
					}
				})
				.setNegativeButton("Нет",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();

		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		ListView listView = (ListView) findViewById(R.id.savedPages);
		pagesAdapter = new PagesAdapter(this, dbHelper.getAllPages());
		listView.setAdapter(pagesAdapter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbHelper.close();
	}
}
