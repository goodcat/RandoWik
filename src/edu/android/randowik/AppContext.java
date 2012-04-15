package edu.android.randowik;

public enum AppContext {
	INSTANCE;

	public static final String getApiEntryPoint() {
		return "http://ru.m.wikipedia.org/w/api.php";
	}

	public static final String getIndexEntryPoint() {
		return "http://ru.m.wikipedia.org/wiki";
	}

	public static final String getRandomServicePageEntryPoint() {
		return "http://ru.m.wikipedia.org/wiki/%D0%A1%D0%BB%D1%83%D0%B6%D0%B5%D0%B1%D0%BD%D0%B0%D1%8F:Random";
	}

	public static int getNumberOfPages() {
		return 5;
	}
}
