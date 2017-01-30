package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import friday.project.notifier.R;

public class Test1 extends Fragment {
	public static final String ARG_PAGE = "ARG_PAGE";

	String mday;
	String mtimetable[];
	int mpos;

	public Test1() {
		// Required empty public constructor
	}

	public static Test1 newInstance(String day,String timetable[], int pos) {
		Bundle args = new Bundle();
		args.putString(ARG_PAGE + "_DAY", day);
		args.putStringArray(ARG_PAGE+"_TT",timetable);
		args.putInt(ARG_PAGE + "_POS",pos);
		Test1 fragment = new Test1();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mday = getArguments().getString(ARG_PAGE+"_DAY");
		mtimetable= getArguments().getStringArray(ARG_PAGE+"_TT");
		mpos=getArguments().getInt(ARG_PAGE+"_POS");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_test1, container, false);
		Log.i("Test1","here");

		TextView one= (TextView) view.findViewById(R.id.timetable_one);
		TextView two= (TextView) view.findViewById(R.id.timetable_two);
		TextView three= (TextView) view.findViewById(R.id.timetable_three);
		TextView four= (TextView) view.findViewById(R.id.timetable_four);
		TextView five= (TextView) view.findViewById(R.id.timetable_five);
		TextView six= (TextView) view.findViewById(R.id.timetable_six);
		TextView seven= (TextView) view.findViewById(R.id.timetable_seven);
		TextView eight= (TextView) view.findViewById(R.id.timetable_eight);
		one.setText(mtimetable[mpos*8+0]);
		two.setText(mtimetable[mpos*8+1]);
		three.setText(mtimetable[mpos*8+2]);
		four.setText(mtimetable[mpos*8+3]);
		five.setText(mtimetable[mpos*8+4]);
		six.setText(mtimetable[mpos*8+5]);
		seven.setText(mtimetable[mpos*8+6]);
		eight.setText(mtimetable[mpos*8+7]);
		return view;
	}
}