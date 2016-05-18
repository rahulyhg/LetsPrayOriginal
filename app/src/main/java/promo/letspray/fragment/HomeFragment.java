package promo.letspray.fragment;


import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import promo.letspray.Model.Prayer;
import promo.letspray.R;
import promo.letspray.database.DatabaseHelper;
import promo.letspray.MainActivity;
import promo.letspray.R;
import promo.letspray.utility.ApplicationUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    TextView  tv_Fajr_Time;
    TextView tv_Dhur_Time;
    TextView tv_Asr_Time;
    TextView tv_Maghrib_Time;
    TextView tv_Isa_Time;
    TextView tv_hadith_of_the_Day;
    TextView tv_more;
    TextView tv_ifter_time;
    TextView tv_seheri_time;
    TextView tv_prayer_name;
    TextView tv_prayer_time;
    TextView tv_day_name;
    TextView tv_prayer_date;
    TextView tv_hour;
    TextView tv_miniute;
    TextView tv_second;
    LinearLayout ifter_linearlayout;
    LinearLayout seheri_linearlayout;
    public RelativeLayout relativeLayout;
    private int day_state=0;




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
        day_state= promo.letspray.utility.ApplicationUtils.getDayState();

       // Log.e("Time", day_state+"");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        setPrayerTime();
    }


    private void initUI(View view){

        tv_Fajr_Time=(TextView)view.findViewById(R.id.tv_fajr_time);
        tv_Asr_Time=(TextView)view.findViewById(R.id.tv_asr_time);
        tv_Dhur_Time=(TextView)view.findViewById(R.id.tv_duhur_time);
        tv_Maghrib_Time=(TextView)view.findViewById(R.id.tv_maghrib_time);
        tv_Isa_Time=(TextView)view.findViewById(R.id.tv_isa_time);
        tv_hadith_of_the_Day=(TextView)view.findViewById(R.id.tvHaditFull);
        tv_more=(TextView)view.findViewById(R.id.tvMore);
        tv_ifter_time=(TextView)view.findViewById(R.id.tvIftarTime);
        tv_seheri_time=(TextView)view.findViewById(R.id.tvSeheriTime);
        tv_prayer_name=(TextView)view.findViewById(R.id.tv_prayer_name);
        tv_prayer_time=(TextView)view.findViewById(R.id.tv_prayer_time);
        tv_day_name=(TextView)view.findViewById(R.id.tv_day);
        tv_prayer_date=(TextView)view.findViewById(R.id.tv_date);
        tv_hour=(TextView)view.findViewById(R.id.tvHour);
        tv_miniute=(TextView)view.findViewById(R.id.tvMinute);
        tv_second=(TextView)view.findViewById(R.id.tvSecond);
        ifter_linearlayout=(LinearLayout)view.findViewById(R.id.tv_Ifter);
        seheri_linearlayout=(LinearLayout)view.findViewById(R.id.tv_Seheri);
        relativeLayout=(RelativeLayout)view.findViewById(R.id.ui_relative_layout);


        setBackgroundNdBarColor();

    }

    private void setBackgroundNdBarColor(){

        switch(day_state){
            case ApplicationUtils.MORNING:
                relativeLayout.setBackgroundResource(R.drawable.morning);
                break;
            case ApplicationUtils.NOON:
                relativeLayout.setBackgroundResource(R.drawable.afternoon);
                getActivity().setTheme(R.style.AfterNoonTheme);
                break;
            case ApplicationUtils.EVENING:
                relativeLayout.setBackgroundResource(R.drawable.evening);
                getActivity().setTheme(R.style.EveningTheme);
            case ApplicationUtils.NIGHT:
                getActivity().setTheme(R.style.NightTheme);
                relativeLayout.setBackgroundResource(R.drawable.night);

        }
    }

    public void setPrayerTime(){

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        ArrayList<Prayer> contacts = databaseHelper.getPrayer();
        for (int i = 0; i < contacts.size(); i++) {
            if (i == 0) {
                tv_Fajr_Time.setText(contacts.get(i).getPrayerTime().toString());
            }
            if(i == 1){
                tv_Dhur_Time.setText(contacts.get(i).getPrayerTime().toString());
            }
            if(i == 2){
                tv_Asr_Time.setText(contacts.get(i).getPrayerTime().toString());
            }
            if(i == 3){
                tv_Maghrib_Time.setText(contacts.get(i).getPrayerTime().toString());
            }
            if(i == 4){
                tv_Isa_Time.setText(contacts.get(i).getPrayerTime().toString());
            }

        }
    }

}
