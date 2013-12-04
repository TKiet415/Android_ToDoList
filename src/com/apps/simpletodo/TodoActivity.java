package com.apps.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TodoActivity extends Activity {
	
	private ArrayList<String> items;
	private ArrayAdapter<String> itemsAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	private final int REQUEST_CODE = 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		
		readItems();
		lvItems = (ListView) findViewById(R.id.lvItems);
		itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		editListViewListener();
		setupListViewListener();
	}
	
	private void editListViewListener() {
		// TODO Auto-generated method stub
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> aView, View item, int pos,
					long id) {
				// TODO Auto-generated method stub
				Intent editIntent = new Intent( TodoActivity.this, EditItemActivity.class );
				editIntent.putExtra("editData", items.get(pos));
				editIntent.putExtra("index", pos);
				startActivityForResult(editIntent, REQUEST_CODE);
			}
		});
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//super.onActivityResult(requestCode, resultCode, data);
		if ( resultCode == RESULT_OK && requestCode == REQUEST_CODE ) {
			final String name = data.getExtras().getString( "saveData" );
			final int pos = data.getExtras().getInt( "pos" );
			
			items.set(pos, name);
			itemsAdapter.notifyDataSetChanged();
			writeItems();
			
			Toast.makeText( this, name, Toast.LENGTH_SHORT ).show();
		}
	}

	public void addTodoItem(View v) {
		itemsAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
		writeItems();
	}

	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> aView, View item,
					int pos, long id) {
				// TODO Auto-generated method stub
				items.remove(pos);
				itemsAdapter.notifyDataSetInvalidated();
				return false;
			}
		});
	}
	
	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			items = new ArrayList<String>
				(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			items = new ArrayList<String>();
			e.printStackTrace();
		}
	}
	
	private void writeItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.writeLines(todoFile, items);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}
	
}
