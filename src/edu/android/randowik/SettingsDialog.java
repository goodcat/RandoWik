package edu.android.randowik;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsDialog extends Dialog {

	public SettingsDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_dialog);

		EditText numOfRandPagesEditText = (EditText) SettingsDialog.this
				.findViewById(R.id.editNumberOfRandPages);
		int numOfRandPages = AppContext.getNumberOfPages(getContext());
		numOfRandPagesEditText.setText(String.valueOf(numOfRandPages));

		numOfRandPagesEditText
				.addTextChangedListener(new NumOfRandPagesEditTextListener());

		Button buttonCancel = (Button) findViewById(R.id.buttonSettingsCancel);
		buttonCancel.setOnClickListener(new ButtonCancelClicked());

		Button buttonYes = (Button) findViewById(R.id.buttonSettingsOk);
		buttonYes.setOnClickListener(new ButtonOkClicked());
	}

	class ButtonCancelClicked implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
			SettingsDialog.this.dismiss();
		}

	}

	class ButtonOkClicked implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
			EditText numOfRandPagesEditText = (EditText) SettingsDialog.this
					.findViewById(R.id.editNumberOfRandPages);
			String numOfRandPagesString = numOfRandPagesEditText.getText()
					.toString();
			int numOfRandPages = Integer.valueOf(numOfRandPagesString);

			SharedPreferences prefs = getContext().getSharedPreferences(
					AppContext.RANDOWIK_PREFERENCES, Context.MODE_PRIVATE);
			SharedPreferences.Editor prefEditor = prefs.edit();
			prefEditor
					.putInt(AppContext.NUMBER_OF_RANDOM_PAGES, numOfRandPages);
			prefEditor.commit();
			SettingsDialog.this.dismiss();
		}

	}

	class NumOfRandPagesEditTextListener implements TextWatcher {

		@Override
		public void afterTextChanged(Editable arg0) {
			String value = arg0.toString();
			if (value == null || "".equals(value.trim())) {
				arg0.replace(0, arg0.length(), String.valueOf(5));
			}
			value = arg0.toString();
			int num = Integer.valueOf(value);
			if (num <= 0 || num > 32) {
				arg0.replace(0, arg0.length(), String.valueOf(5));
			}
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

	}

}
