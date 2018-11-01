package layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import Adapters.TimeTablePagesAdapter;
import friday.project.notifier.Notifier;
import friday.project.notifier.R;

public class NotifTimeTable extends Fragment {

	public static final String MyPREFERENCES = "MyPrefs";
//	ExpandableListAdapter listAdapter;
	SharedPreferences sharedpreferences;
//	ExpandableListView expListView;
//	List<String> listDataHeader;
//	HashMap<String, List<String>> listDataChild;

//	private FragmentTabHost tabHost;

	public NotifTimeTable() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_timetable, container, false);
		((Notifier) getActivity()).setActionBarTitle("Timetable");
		sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

		ViewPager pager = (ViewPager) view.findViewById(R.id.view_pager);
		pager.setAdapter(new TimeTablePagesAdapter(getChildFragmentManager(), 5, sharedpreferences ));

		// Bind the tabs to the ViewPager
		PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);

		tabs.setIndicatorColorResource(R.color.bootstrap_gray_lightest);
		tabs.setTextColorResource(R.color.bootstrap_gray_lightest);
		tabs.setDividerColorResource(R.color.bootstrap_gray_light);
		tabs.setIndicatorHeight(5);
		tabs.setViewPager(pager);
		return view;
	}


}
