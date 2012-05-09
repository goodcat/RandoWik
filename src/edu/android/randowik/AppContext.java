package edu.android.randowik;

import android.content.Context;
import android.content.SharedPreferences;

public enum AppContext {
	INSTANCE;

	public static final String NUMBER_OF_RANDOM_PAGES = "NUMBER_OF_RANDOM_PAGES";
	public static final String RANDOWIK_PREFERENCES = "RANDOWIK_PREFERENCES";

	public static final String getApiEntryPoint() {
		return "http://ru.m.wikipedia.org/w/api.php";
	}

	public static final String getIndexEntryPoint() {
		return "http://ru.m.wikipedia.org/wiki";
	}

	public static final String getRandomServicePageEntryPoint() {
		return "http://ru.m.wikipedia.org/wiki/%D0%A1%D0%BB%D1%83%D0%B6%D0%B5%D0%B1%D0%BD%D0%B0%D1%8F:Random";
	}

	public static int getNumberOfPages(Context context) {
		if (context == null)
			throw new IllegalArgumentException("Context cannot be null");
		SharedPreferences prefs = context.getSharedPreferences(RANDOWIK_PREFERENCES, Context.MODE_PRIVATE);
		int numOfRandPages = prefs.getInt(NUMBER_OF_RANDOM_PAGES, 5);
		return numOfRandPages;
	}
}
