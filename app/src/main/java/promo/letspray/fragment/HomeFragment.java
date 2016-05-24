package promo.letspray.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import promo.letspray.Model.Prayer;
import promo.letspray.R;
import promo.letspray.database.DatabaseHelper;
import promo.letspray.utility.ApplicationUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    TextView tvFajrTime, tvDohrTime, tvAsrTime, tvMaghribTime, tvIshaTime, tvHadithFull, tv_more,
            tvIfterTime, tvSehriTIme, tvNextPrayer, tvNextPrayTime, tvWeekDay, tvDate, tvHour,
            tvMinute, tvSecond;
    LinearLayout llIfter, llSehri;
    RelativeLayout rlFragmentBg;

    long fazrWaqtMs, sunriseMs, dohrWaqtMs, asrWaqtMs, maghribWaqtMs, maghribEnd, ishaWaqtMs;
    Prayer currentPrayer = new Prayer();
    Prayer nextPrayer = new Prayer();

    int day_state = 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Reading all contacts
        day_state = promo.letspray.utility.ApplicationUtils.getDayState();

        // Log.e("Time", day_state+"");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        setPrayerTime();
        setDate();
        setDay();
        setNextPrayerTime();
    }


    private void initUI(View view) {
        tvFajrTime = (TextView) view.findViewById(R.id.tvFajrTime);
        tvAsrTime = (TextView) view.findViewById(R.id.tvAsrTime);
        tvDohrTime = (TextView) view.findViewById(R.id.tvDohrTime);
        tvMaghribTime = (TextView) view.findViewById(R.id.tvMaghribTime);
        tvIshaTime = (TextView) view.findViewById(R.id.tvIshaTime);
        tvHadithFull = (TextView) view.findViewById(R.id.tvHadithFull);
        tv_more = (TextView) view.findViewById(R.id.tvMore);
        tvIfterTime = (TextView) view.findViewById(R.id.tvIftarTime);
        tvSehriTIme = (TextView) view.findViewById(R.id.tvSeheriTime);
        tvNextPrayer = (TextView) view.findViewById(R.id.tvNextPrayer);
        tvNextPrayTime = (TextView) view.findViewById(R.id.tvNextPrayTime);
        tvWeekDay = (TextView) view.findViewById(R.id.tvWeekDay);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvHour = (TextView) view.findViewById(R.id.tvHour);
        tvMinute = (TextView) view.findViewById(R.id.tvMinute);
        tvSecond = (TextView) view.findViewById(R.id.tvSecond);
        llIfter = (LinearLayout) view.findViewById(R.id.llIfter);
        llSehri = (LinearLayout) view.findViewById(R.id.llSehri);
        rlFragmentBg = (RelativeLayout) view.findViewById(R.id.rlFragmentBg);

        setBackgroundNdBarColor();

    }

    private void setBackgroundNdBarColor() {

        switch (day_state) {
            case ApplicationUtils.MORNING:
                rlFragmentBg.setBackgroundResource(R.drawable.morning);
                break;
            case ApplicationUtils.NOON:
                rlFragmentBg.setBackgroundResource(R.drawable.afternoon);
                getActivity().setTheme(R.style.AfterNoonTheme);
                break;
            case ApplicationUtils.EVENING:
                rlFragmentBg.setBackgroundResource(R.drawable.evening);
                getActivity().setTheme(R.style.EveningTheme);
            case ApplicationUtils.NIGHT:
                getActivity().setTheme(R.style.NightTheme);
                rlFragmentBg.setBackgroundResource(R.drawable.night);

        }
    }

    private void setNextPrayerTime() {
        Calendar calendar = Calendar.getInstance();
        long currentTimeMs = calendar.getTimeInMillis();
        Log.e("CurrentTime",currentTimeMs+"");
        if(currentTimeMs < fazrWaqtMs){
            //nextPrayer Fazr
            setNextPrayer("Fazr",tvFajrTime.getText().toString());
            setCountDown(fazrWaqtMs);

        }else if(currentTimeMs>=fazrWaqtMs&&currentTimeMs<sunriseMs){
            //currentPrayer Fazr
        }else if(currentTimeMs>=sunriseMs&&currentTimeMs<dohrWaqtMs){
            //nextPrayer Dohr
        }else if(currentTimeMs>=dohrWaqtMs&&currentTimeMs<asrWaqtMs){
            //currentPrayer Dohr
            //nextPrayer Asr
        }else if(currentTimeMs>=asrWaqtMs&&currentTimeMs<maghribWaqtMs){
            //currentPrayer Asr
            //nextPrayer Maghrib
        }else if(currentTimeMs>=maghribWaqtMs&&currentTimeMs<maghribEnd){
            //currentPrayer Maghrib
            //nextPrayer Isha
        }else if(currentTimeMs>=maghribEnd&&currentTimeMs<ishaWaqtMs){
            //nextPrayer Isha
        }else if (currentTimeMs>=ishaWaqtMs){
            //currentPrayer Isha
        }

    }

    public void setPrayerTime() {

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        ArrayList<Prayer> prayers = databaseHelper.getPrayer();
        for (int i = 0; i < prayers.size(); i++) {
            if (i == 0) {
                tvFajrTime.setText(prayers.get(i).getPrayerTime().toString());
                fazrWaqtMs = ApplicationUtils.getPrayerTimeInMs(prayers.get(i).getPrayerTime().toString());
                Log.e("Fajr In Ms", fazrWaqtMs +"");
            }
            if (i == 1) {
                sunriseMs = ApplicationUtils.getPrayerTimeInMs(prayers.get(i).getPrayerTime().toString());
                Log.e("Sunruse In Ms", sunriseMs+"");
            }
            if (i == 2) {
                tvDohrTime.setText(prayers.get(i).getPrayerTime().toString());
                dohrWaqtMs = ApplicationUtils.getPrayerTimeInMs(prayers.get(i).getPrayerTime().toString());
                Log.e("Dohr In Ms", dohrWaqtMs+"");
            }
            if (i == 3) {
                tvAsrTime.setText(prayers.get(i).getPrayerTime().toString());
                asrWaqtMs = ApplicationUtils.getPrayerTimeInMs(prayers.get(i).getPrayerTime().toString());
                Log.e("Asr In Ms", asrWaqtMs+"");
            }
            if (i == 4) {
                tvMaghribTime.setText(prayers.get(i).getPrayerTime().toString());
                maghribWaqtMs = ApplicationUtils.getPrayerTimeInMs(prayers.get(i).getPrayerTime().toString());
                Log.e("Maghrib In Ms", maghribWaqtMs+"");
                maghribEnd = maghribWaqtMs+1000*60;
                Log.e("Maghrib End", maghribEnd+"");
            }
            if (i == 5) {
                tvIshaTime.setText(prayers.get(i).getPrayerTime().toString());
                ishaWaqtMs = ApplicationUtils.getPrayerTimeInMs(prayers.get(i).getPrayerTime().toString());
                Log.e("Isha In Ms", ishaWaqtMs+"");
            }

        }

    }

    public void setDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy ");
        String strDate = mdformat.format(calendar.getTime());
        tvDate.setText(strDate);
    }

    public void setDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        tvWeekDay.setText(dayOfTheWeek);
    }

    private void setNextPrayer(String prayerName, String prayerTime){
        tvNextPrayer.setText(prayerName);
        tvNextPrayTime.setText(prayerTime);
    }

    private void setCountDown(long waqt){

    }

}
