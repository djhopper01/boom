package com.busted.boom;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

public class ConfirmationDialog extends Dialog {
	
	private Button mConfirmationClose;
	private Button mConfirmationCaption;

	public ConfirmationDialog(Context context) {
		super(context);
		
		this.setContentView(R.layout.confirmation_dialog);
		
		mConfirmationClose = (Button) findViewById(R.id.confirmation_close);
		
		mConfirmationClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
			
		});
	}

}
