package edu.android.randowik;

public enum AppContext {
	INSTANCE;

	public static final String getApiEntryPoint() {
		return "http://ru.wikipedia.org/w/api.php";
	}
}
