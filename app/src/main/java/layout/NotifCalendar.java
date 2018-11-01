package layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import SupportClasses.DrawableEventRenderer;
import friday.project.notifier.Notifier;
import friday.project.notifier.R;

public class NotifCalendar extends Fragment implements CalendarPickerController {
//	private static final String LOG_TAG = "FUCK YOU";

	SharedPreferences.Editor editor;
	SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs";

	public NotifCalendar() {
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

		View rv = inflater.inflate(R.layout.fragment_calendar, container, false);
		((Notifier) getActivity()).setActionBarTitle("Calendar");
		// Inflate the layout for this fragment
		Calendar minDate = Calendar.getInstance();
		Calendar maxDate = Calendar.getInstance();

		minDate.add(Calendar.MONTH, -2);
		minDate.set(Calendar.DAY_OF_MONTH, 1);
		maxDate.add(Calendar.YEAR, 1);

		List<CalendarEvent> eventList = new ArrayList<>();
		mockList(eventList);

		AgendaCalendarView mAgendaCalendarView = (AgendaCalendarView) rv.findViewById(R.id.agenda_calendar_view);
		mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);

		mAgendaCalendarView.addEventRenderer(new DrawableEventRenderer());


		return rv;
	}


	private void mockList(List<CalendarEvent> eventList) {
		JSONArray jsonData = null;

		sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		editor = sharedpreferences.edit();

		String data = sharedpreferences.getString("calendar","hello");
		try {
			jsonData = new JSONArray(data);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONObject d = null;
		Date startdate = null;
		Date enddate = null;

		SimpleDateFormat curformat = new SimpleDateFormat("dd-MM-yyyy");


		for (int i = 0; i < jsonData.length(); i++) {


			try {
				d = (JSONObject) jsonData.get(i);
				try {
					startdate = curformat.parse(d.getString("start"));
					enddate = curformat.parse(d.getString("end"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Calendar startTime2 = Calendar.getInstance();
				startTime2.setTime(startdate);

				Calendar endTime2 = Calendar.getInstance();
				endTime2.setTime(enddate);


				String Title = d.getString("title");
				BaseCalendarEvent event2 = new BaseCalendarEvent(Title, "A beautiful small town", "Akshay",
						ContextCompat.getColor(this.getActivity(), R.color.yellow), startTime2, endTime2, true);
				eventList.add(event2);


			} catch (JSONException e) {

				e.printStackTrace();
			}


		}

	}

	@Override
	public void onDaySelected(DayItem dayItem) {

	}

	@Override
	public void onEventSelected(CalendarEvent event) {

	}

	@Override
	public void onScrollToDate(Calendar calendar) {

	}
}
