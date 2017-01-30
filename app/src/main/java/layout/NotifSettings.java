package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import friday.project.notifier.Notifier;
import friday.project.notifier.R;

public class NotifSettings extends Fragment {
	public NotifSettings() {
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
		// Inflate the layout for this fragment
		View rv = inflater.inflate(R.layout.fragment_settings, container, false);
		((Notifier) getActivity()).setActionBarTitle("Settings");

		return rv;
	}

}
