package edu.android.randowik;

import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;
import edu.android.randowik.bot.Bot;
import edu.android.randowik.bot.Page;
import edu.android.randowik.db.DbHelper;

public class WebViewWikiPageActivity extends Activity {
	private final static String TAG = "WebViewWikiPageActivity";

	Handler handler;
	Page page;
	DbHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_page_content);

		handler = new Handler();

		dbHelper = new DbHelper(this);

		final ProgressDialog dialog = ProgressDialog.show(this, "Loading",
				"Loading wiki article");
		Thread th = new Thread() {
			public void run() {
				page = new Page();
				String id = WebViewWikiPageActivity.this.getIntent()
						.getStringExtra("id");
				String title = WebViewWikiPageActivity.this.getIntent()
						.getStringExtra("title");
				page.setId(id);
				page.setTitle(title);
				Bot bot = new Bot();
				try {
					bot.fillPageHtmlContent(page);
				} catch (Exception e) {
					Log.e(TAG, "fillPageContent ERROR", e);
				}

				handler.post(new Runnable() {

					@Override
					public void run() {
						WebView webView = (WebView) findViewById(R.id.webview);

						if (page.getContent() == null) {
							String data = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
									+ String.format(
											"Не удалось загрузить страницу [%1s]",
											page.getTitle());
							webView.loadData(data, "text/html", null);
						} else {
							String data = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><html><body>"
									+ page.getContent() + "</body></html>";
							Log.d(TAG, data);
							webView.loadDataWithBaseURL(
									AppContext.getApiEntryPoint(), data,
									"text/html", null, null);
						}

						dialog.dismiss();
					}
				});
			}
		};
		th.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.webview_page_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.go_to_full_version_menu_item) {
			String id = WebViewWikiPageActivity.this.getIntent()
					.getStringExtra("id");
			String address = AppContext.getIndexEntryPoint() + "?curid=" + id;
			Log.d(TAG, "onOptionsItemSelected address: " + address);
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(address));
			startActivity(i);
			return true;
		}
		if (item.getItemId() == R.id.save_page_menu_item) {
			dbHelper.savePage(new Date(), page.getId(), page.getTitle(),
					page.getContent());
			Toast.makeText(this, "Page saved", Toast.LENGTH_SHORT).show();
		}
		return super.onOptionsItemSelected(item);
	}
	

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbHelper.close();
	}
}
