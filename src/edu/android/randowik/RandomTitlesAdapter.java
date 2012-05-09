package edu.android.randowik;

import java.util.ArrayList;
import java.util.List;

import edu.android.randowik.bot.Bot;
import edu.android.randowik.bot.Page;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RandomTitlesAdapter extends BaseAdapter {
	private static final String TAG = "RandomTitlesAdapter";
	private List<Page> pages = new ArrayList<Page>();
	private Context context;

	public RandomTitlesAdapter(Context context) {
		this.context = context;
		Bot bot = new Bot();
		try {
			int numOfRandPages = AppContext.getNumberOfPages(this.context);
			for (int i = 0; i < numOfRandPages; i++) {
				Page page = bot.fetchIndexRandomPage();
				pages.add(page);
			}
		} catch (Exception e) {
			Log.e(TAG, "RandomTitlesAdapter.fetchRandomPages ERROR", e);
		}
	}

	@Override
	public int getCount() {
		return pages.size();
	}

	@Override
	public Object getItem(int index) {
		return pages.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			view = inflater.inflate(R.layout.title_list_item, parent, false);
		}
		Page page = pages.get(position);
		TextView titleTextView = (TextView) view.findViewById(R.id.titleView);
		titleTextView.setText(page.getTitle());
		return view;
	}

}
