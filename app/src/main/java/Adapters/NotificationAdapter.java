package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import SupportClasses.NotificationObject;
import friday.project.notifier.R;

/**
 * Created by Manu Srivastava on 17-04-2016.
 */
public class NotificationAdapter extends ArrayAdapter<NotificationObject>{

	private final Context mycontext;
	private final ArrayList<NotificationObject> notifObjects;


	public NotificationAdapter(Context mycontext, ArrayList<NotificationObject> testObjects) {
		super(mycontext, -1, testObjects);
		this.mycontext = mycontext;
		this.notifObjects = testObjects;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mycontext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View myTests = inflater.inflate(R.layout.adapter_notif, parent, false);
		RelativeLayout rl= (RelativeLayout) myTests.findViewById(R.id.notif_adapt_rel);
		TextView notifTitle = (TextView) myTests.findViewById(R.id.adapter_notif_title);
		notifTitle.setText(notifObjects.get(position).getTitle());
		TextView date = (TextView) myTests.findViewById(R.id.adapter_notif_date);
		date.setText(notifObjects.get(position).getDate());

		return myTests;
	}


	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public int getPosition(NotificationObject item) {
		return super.getPosition(item);
	}

	@Override
	public NotificationObject getItem(int position) {
		return super.getItem(position);
	}

	@Override
	public int getCount() {
		return super.getCount();
	}
}
