package edu.android.randowik;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

public class AboutDialog extends Dialog {

	public AboutDialog(Context context) {
		super(context);
		setContentView(R.layout.about_dialog);

		Button btnClose = (Button) findViewById(R.id.buttonAboutClose);
		btnClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AboutDialog.this.dismiss();
			}
		});
	}

}
