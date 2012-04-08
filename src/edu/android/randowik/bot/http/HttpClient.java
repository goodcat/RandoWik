package edu.android.randowik.bot.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import android.util.Log;

public class HttpClient {
	public static final String TAG = "HttpClient";

	public String loadPage(URL url) throws IOException {
		if (url == null)
			throw new IllegalArgumentException("Url cannot be null");
		String content = null;
		BufferedReader reader = null;
		StringBuilder buf = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				buf.append(line);
			}
			content = buf.toString();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (Exception e) {
				Log.e(TAG, "Error close resource", e);
			}
		}
		return content;
	}
}
