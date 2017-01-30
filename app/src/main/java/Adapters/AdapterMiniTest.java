package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import SupportClasses.TestObject;
import friday.project.notifier.R;

/**
 * Created by Manu Srivastava on 17-04-2016.
 */
public class AdapterMiniTest extends ArrayAdapter<TestObject>{

	private final Context mycontext;
	private final ArrayList<TestObject> notifObjects;


	public AdapterMiniTest(Context mycontext, ArrayList<TestObject> testObjects) {
		super(mycontext, -1, testObjects);
		this.mycontext = mycontext;
		this.notifObjects = testObjects;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mycontext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View myTests = inflater.inflate(R.layout.adapter_mini_notif, parent, false);
		TextView notifTitle = (TextView) myTests.findViewById(R.id.adapter_mini_notif_title);
		notifTitle.setText(notifObjects.get(position).getSubname());
		TextView date = (TextView) myTests.findViewById(R.id.adapter_mini_notif_date);
		date.setText(notifObjects.get(position).getDate());

		return myTests;
	}


	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public int getPosition(TestObject item) {
		return super.getPosition(item);
	}

	@Override
	public TestObject getItem(int position) {
		return super.getItem(position);
	}

	@Override
	public int getCount() {
		return super.getCount();
	}
}
