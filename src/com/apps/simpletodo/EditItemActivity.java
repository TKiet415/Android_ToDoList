package com.apps.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends Activity implements OnClickListener {
	
	private EditText editString;
	private Button saveButton;
	public int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
		editString = (EditText) findViewById(R.id.etEditItem);
		saveButton = (Button) findViewById(R.id.bSave);
		
		Bundle extras = getIntent().getExtras();
		String data = extras.getString( "editData" );
		position = extras.getInt( "index" );
		editString.setText( data );
		editString.setSelection( editString.getText().length(), editString.getText().length() );
		saveButton.setOnClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Intent newData = new Intent();
		newData.putExtra( "saveData", editString.getText().toString() );
		newData.putExtra( "pos", position );
		setResult( RESULT_OK, newData );
		finish();
	
	}

}
