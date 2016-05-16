package promo.letspray.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import promo.letspray.R;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
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
    }
}
