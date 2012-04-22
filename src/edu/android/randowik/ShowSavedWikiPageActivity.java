package edu.android.randowik;

import edu.android.randowik.db.DbHelper;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class ShowSavedWikiPageActivity extends Activity {
	private static final String TAG = "ShowSavedWikiPageActivity";
	private DbHelper dbHelper;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_page_content);

		dbHelper = new DbHelper(this);

		String id = getIntent().getStringExtra("id");
		String title = getIntent().getStringExtra("title");
		Log.d(TAG, "ShowSavedWikiPageActivity PAGE_ID: " + id
				+ "; PAGE_TITLE: " + title);
		String content = null;
		Cursor cursor = dbHelper.findPageByPageId(id);
		cursor.moveToFirst();
		content = cursor.getString(3);
		cursor.close();

		WebView webView = (WebView) findViewById(R.id.webview);
		String data = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><html><body>"
				+ content + "</body></html>";
		Log.d(TAG, data);
		webView.loadDataWithBaseURL(AppContext.getApiEntryPoint(), data,
				"text/html", null, null);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbHelper.close();
	}

}
