package promo.letspray.fragment;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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

import promo.letspray.AlarmReceiver;
import promo.letspray.Model.Prayer;
import promo.letspray.R;
import promo.letspray.StaticData;
import promo.letspray.Utility.ApplicationUtils;
import promo.letspray.database.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    TextView tvFajrTime, tvDohrTime, tvAsrTime, tvMaghribTime, tvIshaTime, tvHadithFull, tv_more,
            tvIfterTime, tvSehriTIme, tvIfter, tvSehri, tvNextPrayer, tvNextPrayTime, tvWeekDay, tvDate, tvHour,
            tvMinute, tvSecond,tvHadit,tvNPTR,tvSecondText, fajr,dohr,asr,magrib,isha;

    LinearLayout llIfter, llSehri , fajr_layout,duhur_layout,asr_layout,maghrib_layout,isha_layout;
    RelativeLayout rlFragmentBg,rlBottomLayout;
    Toolbar toolbar;

    long fazrWaqtMs, sunriseMs, dohrWaqtMs, asrWaqtMs, maghribWaqtMs, maghribEnd, ishaWaqtMs, dayEnd;
    Context context;

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
        day_state = ApplicationUtils.getDayState();
        context = getActivity();
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
        setDayEnd();
        setNextPrayerTime();
        setIfterTime();
        setTvSehriTIme();

    }


    private void initUI(View view) {
        tvFajrTime = (TextView) view.findViewById(R.id.tvFajrTime);
        tvAsrTime = (TextView) view.findViewById(R.id.tvAsrTime);
        tvDohrTime = (TextView) view.findViewById(R.id.tvDohrTime);
        tvMaghribTime = (TextView) view.findViewById(R.id.tvMaghribTime);
        tvIshaTime = (TextView) view.findViewById(R.id.tvIshaTime);
        fajr = (TextView) view.findViewById(R.id.fajr);
        dohr = (TextView) view.findViewById(R.id.dohr);
        asr = (TextView) view.findViewById(R.id.asr);
        magrib = (TextView) view.findViewById(R.id.magrib);
        isha = (TextView) view.findViewById(R.id.isha);
        tvHadithFull = (TextView) view.findViewById(R.id.tvHadithFull);
        tv_more = (TextView) view.findViewById(R.id.tvMore);
        tvIfter = (TextView) view.findViewById(R.id.tvIftar);
        tvSehri = (TextView) view.findViewById(R.id.tvSeheri);
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
        tvHadit = (TextView) view.findViewById(R.id.tvHadit);
        tvNPTR = (TextView) view.findViewById(R.id.tvNPTR);
        tvSecondText=(TextView) view.findViewById(R.id.tvSecondText);
        llIfter = (LinearLayout) view.findViewById(R.id.llIfter);
        llSehri = (LinearLayout) view.findViewById(R.id.llSehri);
        rlFragmentBg = (RelativeLayout) view.findViewById(R.id.rlFragmentBg);
        rlBottomLayout = (RelativeLayout) view.findViewById(R.id.rlBottomLayout);
        fajr_layout = (LinearLayout)view.findViewById(R.id.fajr_layout);
        duhur_layout = (LinearLayout)view.findViewById(R.id.duhur_layout);
        asr_layout = (LinearLayout)view.findViewById(R.id.asr_layout);
        maghrib_layout = (LinearLayout)view.findViewById(R.id.maghrib_layout);
        isha_layout = (LinearLayout)view.findViewById(R.id.isha_layout);

        setBackgroundNdBarColor();
        setTypeface();

    }


    private void setTypeface(){
        tvHour.setTypeface(ApplicationUtils.setTimeTypeface(context));
        tvMinute.setTypeface(ApplicationUtils.setTimeTypeface(context));
        tvSecond.setTypeface(ApplicationUtils.setTimeTypeface(context));

        tvNextPrayer.setTypeface(ApplicationUtils.setTimeTypeface(context));
        tvNextPrayTime.setTypeface(ApplicationUtils.setTimeTypeface(context));
        tvWeekDay.setTypeface(ApplicationUtils.setTimeTypeface(context));
        tvDate.setTypeface(ApplicationUtils.setTimeTypeface(context));

        tvIfter.setTypeface(ApplicationUtils.setTimeTypeface(context));
        tvIfterTime.setTypeface(ApplicationUtils.setTimeTypeface(context));
        tvSehri.setTypeface(ApplicationUtils.setTimeTypeface(context));
        tvSehriTIme.setTypeface(ApplicationUtils.setTimeTypeface(context));
        tvSecondText.setTypeface(ApplicationUtils.setTimeDateTypeface(context));

        tvHadithFull.setTypeface(ApplicationUtils.setHadithDetailTypeface(context));
        tvNPTR.setTypeface(ApplicationUtils.setHadithDetailTypeface(context));
    }

    private void setBackgroundNdBarColor() {

        switch (day_state) {
            case ApplicationUtils.MORNING:
                rlFragmentBg.setBackgroundResource(R.drawable.morning);
                getActivity().setTheme(R.style.MorningTheme);
                tvHadit.setBackgroundResource(R.drawable.hadit_of_the_day_tv_morning);
                rlBottomLayout.setBackgroundColor(getResources().getColor(R.color.morningrlBottomLayout));
                tvHadithFull.setText("HADITH AT MORNING..............................");
                break;

            case ApplicationUtils.NOON:
                rlFragmentBg.setBackgroundResource(R.drawable.afternoon);
                getActivity().setTheme(R.style.AfterNoonTheme);
                tvHadit.setBackgroundResource(R.drawable.hadit_of_the_day_tv_day);
                //rlBottomLayout.setBackgroundColor(Color.parseColor("#923000"));
                rlBottomLayout.setBackgroundColor(getResources().getColor(R.color.afternoonrlBottomLayout));
                tvHadithFull.setText("HADITH AT NOON..............................");
                break;

            case ApplicationUtils.EVENING:
                rlFragmentBg.setBackgroundResource(R.drawable.evening);
                getActivity().setTheme(R.style.EveningTheme);
                tvHadit.setBackgroundResource(R.drawable.hadit_of_the_day_tv_evening);
                //rlBottomLayout.setBackgroundColor(Color.parseColor("#4f0420"));
                rlBottomLayout.setBackgroundColor(getResources().getColor(R.color.eveningrlBottomLayout));
                tvHadithFull.setText("HADITH AT EVENING..............................");
                break;

            case ApplicationUtils.NIGHT:
                getActivity().setTheme(R.style.NightTheme);
                rlFragmentBg.setBackgroundResource(R.drawable.night);
                tvHadit.setBackgroundResource(R.drawable.hadit_of_the_day_tv_night);
                tvHadithFull.setText("HADITH AT NIGHT..............................");
               // rlBottomLayout.setBackgroundColor(Color.parseColor("#2d003d"));
                rlBottomLayout.setBackgroundColor(getResources().getColor(R.color.nightrlBottomLayout));
                break;
        }
    }

    private void setNextPrayerTime() {
        Calendar calendar = Calendar.getInstance();
        long currentTimeMs = calendar.getTimeInMillis();
        Log.e("CurrentTime", currentTimeMs + "");
        if (currentTimeMs < fazrWaqtMs) {
            //current
            setCurrentPrayer("Fajr");
            //cahnge round shape color
            setDrawableFajar();
            //nextPrayer Fazr
            setNextPrayer("Fazr", tvFajrTime.getText().toString(),fazrWaqtMs);
            setCountDown(fazrWaqtMs-currentTimeMs);

        } else if (currentTimeMs >= fazrWaqtMs && currentTimeMs < sunriseMs) {
            //current
            setCurrentPrayer("Fajr");
            //cahnge round shape color
            setDrawableFajar();
            //currentPrayer Fazr
            setNextPrayer("Fazr", tvFajrTime.getText().toString(),fazrWaqtMs);
            setCountDown(sunriseMs-currentTimeMs);
        } else if (currentTimeMs >= sunriseMs && currentTimeMs < dohrWaqtMs) {
            //current
            setCurrentPrayer("Duhr");
            //cahnge round shape color
            setDrawableDuhur();
            //nextPrayer Dohr
            setNextPrayer("Dohr", tvDohrTime.getText().toString(),dohrWaqtMs);
            setCountDown(dohrWaqtMs-currentTimeMs);
        } else if (currentTimeMs >= dohrWaqtMs && currentTimeMs < asrWaqtMs) {
            //currentPrayer Dohr
            setCurrentPrayer("Duhr");
            //cahnge round shape color
            setDrawableDuhur();
            //nextPrayer Asr
            setNextPrayer("Asr", tvAsrTime.getText().toString(),asrWaqtMs);
            setCountDown(asrWaqtMs-currentTimeMs);
        } else if (currentTimeMs >= asrWaqtMs && currentTimeMs < maghribWaqtMs) {
            //currentPrayer Asr
            setCurrentPrayer("Asr");
            //cahnge round shape color
            setDrawableAsr();
            //nextPrayer Maghrib
            setNextPrayer("Maghrib", tvMaghribTime.getText().toString(),maghribWaqtMs);
            setCountDown(maghribWaqtMs-currentTimeMs);
        } else if (currentTimeMs >= maghribWaqtMs && currentTimeMs < maghribEnd) {
            //currentPrayer Maghrib
            setCurrentPrayer("Maghrib");
            //cahnge round shape color
            setDrawableMagrib();
            //nextPrayer Isha
            setNextPrayer("Isha", tvIshaTime.getText().toString(),ishaWaqtMs);
            setCountDown(maghribEnd-currentTimeMs);
        } else if (currentTimeMs >= maghribEnd && currentTimeMs < ishaWaqtMs) {
            //currentPrayer Maghrib
            setCurrentPrayer("Isha");
            //cahnge round shape color
            setDrawableIsha();
            //nextPrayer Isha
            setNextPrayer("Isha", tvIshaTime.getText().toString(),ishaWaqtMs);
            setCountDown(ishaWaqtMs-currentTimeMs);
        } else if (currentTimeMs >= ishaWaqtMs&&currentTimeMs<dayEnd) {
            //currentPrayer Maghrib
            setCurrentPrayer("Isha");
            setDrawableIsha();
            //currentPrayer Isha
            setNextPrayer("Fazr", tvIshaTime.getText().toString(),fazrWaqtMs);
            setCountDown(dayEnd-currentTimeMs);
        }

    }

    public void setDrawableFajar(){

        fajr_layout.setBackgroundResource(R.drawable.border_green_bg_transparent_green_rounded);
        duhur_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
        asr_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
        maghrib_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
        isha_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
    }

    public void setDrawableDuhur(){

        fajr_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
        duhur_layout.setBackgroundResource(R.drawable.border_green_bg_transparent_green_rounded);
        asr_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
        maghrib_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
        isha_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
    }

    public void setDrawableAsr(){

        fajr_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
        duhur_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
        asr_layout.setBackgroundResource(R.drawable.border_green_bg_transparent_green_rounded);
        maghrib_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
        isha_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
    }

    public void setDrawableMagrib(){

        fajr_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
        duhur_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
        asr_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
        maghrib_layout.setBackgroundResource(R.drawable.border_green_bg_transparent_green_rounded);
        isha_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
    }

    public void setDrawableIsha(){

        fajr_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
        duhur_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
        asr_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
        maghrib_layout.setBackgroundResource(R.drawable.border_white_bg_transparent_white_rounded);
        isha_layout.setBackgroundResource(R.drawable.border_green_bg_transparent_green_rounded);
    }

    public void setPrayerTime() {

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        ArrayList<Prayer> prayers = databaseHelper.getPrayer();
        for (int i = 0; i < prayers.size(); i++) {
            if (i == 0) {
                tvFajrTime.setText(prayers.get(i).getPrayerTime().toString());
                fazrWaqtMs = ApplicationUtils.getPrayerTimeInMs(prayers.get(i).getPrayerTime().toString());
                Log.e("Fajr In Ms", fazrWaqtMs + "");
            }
            if (i == 1) {
                sunriseMs = ApplicationUtils.getPrayerTimeInMs(prayers.get(i).getPrayerTime().toString());
                Log.e("Sunruse In Ms", sunriseMs + "");
            }
            if (i == 2) {
                tvDohrTime.setText(prayers.get(i).getPrayerTime().toString());
                dohrWaqtMs = ApplicationUtils.getPrayerTimeInMs(prayers.get(i).getPrayerTime().toString());
                Log.e("Dohr In Ms", dohrWaqtMs + "");
            }
            if (i == 3) {
                tvAsrTime.setText(prayers.get(i).getPrayerTime().toString());
                asrWaqtMs = ApplicationUtils.getPrayerTimeInMs(prayers.get(i).getPrayerTime().toString());
                Log.e("Asr In Ms", asrWaqtMs + "");
            }
            if (i == 4) {
                tvMaghribTime.setText(prayers.get(i).getPrayerTime().toString());
                maghribWaqtMs = ApplicationUtils.getPrayerTimeInMs(prayers.get(i).getPrayerTime().toString());
                Log.e("Maghrib In Ms", maghribWaqtMs + "");
                maghribEnd = maghribWaqtMs + 1000 *60* 45;
                Log.e("Maghrib End", maghribEnd + "");
            }
            if (i == 5) {
                tvIshaTime.setText(prayers.get(i).getPrayerTime().toString());
                ishaWaqtMs = ApplicationUtils.getPrayerTimeInMs(prayers.get(i).getPrayerTime().toString());
                Log.e("Isha In Ms", ishaWaqtMs + "");
            }

        }
        saveAlarm(fazrWaqtMs,dohrWaqtMs,asrWaqtMs,maghribWaqtMs,ishaWaqtMs);

    }

    private void saveAlarm(long alarmTimeFajr,long alarmTimeDuhr,long alarmTimeAsr,long alarmTimeMagrib,long alarmTimeIsha){
        SharedPreferences preferences = context.getSharedPreferences(StaticData.KEY_PREFERENCE,context.MODE_PRIVATE);
        SharedPreferences.Editor editor =preferences.edit();
        editor.putLong(StaticData.PRAYER_TIME_FAJR,alarmTimeFajr);
        editor.putLong(StaticData.PRAYER_TIME_DUHR,alarmTimeDuhr);
        editor.putLong(StaticData.PRAYER_TIME_ASR,alarmTimeAsr);
        editor.putLong(StaticData.PRAYER_TIME_MAGRIB,alarmTimeMagrib);
        editor.putLong(StaticData.PRAYER_TIME_ISHA,alarmTimeIsha);
        editor.commit();
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

    private void setNextPrayer(String prayerName, String prayerTime,long alarmTime) {
        tvNextPrayer.setText(prayerName);
        tvNextPrayTime.setText(prayerTime);

        SharedPreferences preferences = context.getSharedPreferences(StaticData.KEY_PREFERENCE,context.MODE_PRIVATE);
        SharedPreferences.Editor editor =preferences.edit();
        editor.putLong(StaticData.NEXT_PRAYER_TIME,alarmTime);
        Log.e("NEXT PRAYER TIME",alarmTime +"");
        boolean isAlarm =  preferences.getBoolean(StaticData.IS_ALARMED,false);
        if(!isAlarm){
            setAlarm(alarmTime);
        }
    }

    private void setAlarm(long alarmTime){
        Intent myIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, alarmTime, pendingIntent);
    }

    private void setCurrentPrayer(String prayerName){
        tvNPTR.setText(prayerName + " Time Remaining");
    }

    private void setIfterTime(){
        tvIfterTime.setText(tvMaghribTime.getText().toString());
    }

    private void setTvSehriTIme(){
        tvIfterTime.setText(tvFajrTime.getText().toString());
    }

    private void setCountDown(long waqt) {
        Log.e("Countdown", "Begin");
        CounterClass timer = new CounterClass(waqt,
                1000, context);
        timer.start();
    }

    private void setDayEnd() {
        Calendar calendar = Calendar.getInstance();
        String dateString = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+" "+"23:59:59";
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date postDate = ApplicationUtils.formatDate(dateString, dtFormat);
        calendar.setTime(postDate);
        dayEnd = calendar.getTimeInMillis();
        Log.e("DayEnd", dayEnd + "");
    }

    public class CounterClass extends CountDownTimer {
        private static final long HOUR_DIVISOR = 3600000;
        private static final long MIN_DIVISOR = 60000;
        private static final long SEC_DIVISOR = 1000;
        Context context;

        public CounterClass(long millisInFuture, long countDownInterval, Context context) {
            super(millisInFuture, countDownInterval);
            this.context = context;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            long hour = millis/HOUR_DIVISOR;
            long remaining = (millis%HOUR_DIVISOR);
            long minute = remaining/MIN_DIVISOR;
            remaining = remaining%MIN_DIVISOR;
            long second = remaining/SEC_DIVISOR;
            String hourStr = String.format("%02d",hour);
            String minStr = String.format("%02d",minute);
            String secStr = String.format("%02d",second);
//            Log.e("Time Remaining",hourStr+":"+minStr+":"+secStr);
            tvHour.setText(hourStr);
            tvMinute.setText(minStr);
            tvSecond.setText(secStr);
        }

        @Override
        public void onFinish() {
                setNextPrayerTime();
        }

    }
}
