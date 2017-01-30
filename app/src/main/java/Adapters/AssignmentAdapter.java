package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import SupportClasses.AssignmentObject;
import friday.project.notifier.R;

/**
 * Created by Manu Srivastava on 17-04-2016.
 */
public class AssignmentAdapter extends ArrayAdapter<AssignmentObject> {
	private final Context mycontext;
	private final ArrayList<AssignmentObject> assignmentObjects;

	public AssignmentAdapter(Context context, ArrayList<AssignmentObject> object) {
		super(context, -1, object);
		this.mycontext = context;
		this.assignmentObjects = object;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mycontext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View myAssignments = inflater.inflate(R.layout.adapter_assignment, parent, false);
		TextView subName = (TextView) myAssignments.findViewById(R.id.adapter_assign_sub);
		subName.setText(assignmentObjects.get(position).getSubname());
		TextView date = (TextView) myAssignments.findViewById(R.id.adapter_assign_date);
		date.setText(assignmentObjects.get(position).getDate());
		return myAssignments;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public int getPosition(AssignmentObject item) {
		return super.getPosition(item);
	}

	@Override
	public AssignmentObject getItem(int position) {
		return super.getItem(position);
	}

	@Override
	public int getCount() {
		return super.getCount();
	}
}
