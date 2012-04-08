package edu.android.randowik;

import edu.android.randowik.bot.Bot;
import edu.android.randowik.bot.Page;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class WikiPageActivity extends Activity {
	private final static String TAG = "WikiPageActivity";

	Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_content);

		handler = new Handler();

		final ProgressDialog dialog = ProgressDialog.show(this, "Loading",
				"Loading wiki article");
		Thread th = new Thread() {
			public void run() {
				final Page page = new Page();
				String id = WikiPageActivity.this.getIntent().getStringExtra(
						"id");
				String title = WikiPageActivity.this.getIntent()
						.getStringExtra("title");
				page.setId(id);
				page.setTitle(title);
				Bot bot = new Bot();
				try {
					bot.fillPageContent(page);
				} catch (Exception e) {
					Log.e(TAG, "fillPageContent ERROR", e);
				}

				handler.post(new Runnable() {

					@Override
					public void run() {
						TextView titleTextView = (TextView) findViewById(R.id.pageTitleTextView);
						TextView contentTextView = (TextView) findViewById(R.id.pageContentTextView);

						titleTextView.setText(page.getTitle());

						if (page.getContent() == null) {
							contentTextView.setText(String.format(
									"Не удалось загрузить страницу [%1s]",
									page.getTitle()));
						} else {
							contentTextView.setText(page.getContent());
						}

						dialog.dismiss();
					}
				});
			}
		};
		th.start();
	}
}
