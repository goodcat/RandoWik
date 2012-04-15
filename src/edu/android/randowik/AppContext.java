package edu.android.randowik;

public enum AppContext {
	INSTANCE;

	public static final String getApiEntryPoint() {
		return "http://ru.m.wikipedia.org/w/api.php";
	}
	
	public static final String getIndexEntryPoint() {
		return "http://ru.m.wikipedia.org/wiki";
	}
}
