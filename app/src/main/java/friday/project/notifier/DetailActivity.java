package friday.project.notifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		Intent i = getIntent();
		String text;
		String head;
		String date;
		String type;
		Bundle b = i.getExtras();
		text = (String) b.get("text");
		head = (String) b.get("head");
		date = (String) b.get("date");
		type = (String) b.get("type");
		type += " Details";
		TextView head_text = (TextView) findViewById(R.id.detail_head);
		TextView date_text = (TextView) findViewById(R.id.detail_date);
		TextView text_text = (TextView) findViewById(R.id.detail_text);
		head_text.setText(head);
		date_text.setText(date);
		text_text.setText(text);
		RelativeLayout headrel = (RelativeLayout) findViewById(R.id.detail_rel_head);
		ScrollView textrel = (ScrollView) findViewById(R.id.detail_rel_text);
		setTitle(type);
		if (type.compareTo("Assignment Details") == 0 || type.compareTo("Test Details") == 0) {

			headrel.setBackgroundColor(getResources().getColor(R.color.bootstrap_brand_danger));
			textrel.setBackgroundColor(getResources().getColor(R.color.bootstrap_brand_danger_dark));
		} else {
			headrel.setBackgroundColor(getResources().getColor(R.color.bootstrap_brand_success));
			textrel.setBackgroundColor(getResources().getColor(R.color.bootstrap_brand_success_dark));

		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
