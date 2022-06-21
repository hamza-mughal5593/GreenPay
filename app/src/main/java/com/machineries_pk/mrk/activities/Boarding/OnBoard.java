package com.machineries_pk.mrk.activities.Boarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.machineries_pk.mrk.R;
import com.machineries_pk.mrk.activities.ListActivity;

public class OnBoard extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    TextView letsGetStarted;
    Animation animation;
    int currentPos;
    TextView text_skip;

    RelativeLayout next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        viewPager = findViewById(R.id.view_slider);
        dotsLayout = findViewById(R.id.dot_layout);
        letsGetStarted = findViewById(R.id.text_next);
        next_btn = findViewById(R.id.next_btn);
        text_skip = findViewById(R.id.text_skip);

        //Call adapter
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        //Dots
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);


        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (letsGetStarted.getText().toString().equals("Next")){
                    next();
                }else {
                    skip();
                }
            }
        });
        text_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip();
            }
        });
    }

    public void skip() {
        startActivity(new Intent(this, ListActivity.class));
        finish();
    }

    public void next() {
        viewPager.setCurrentItem(currentPos + 1);
    }

    private void addDots(int position) {

        dots = new TextView[2];
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("•"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.light_green));

            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.green));
        }

    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPos = position;

            if (position == 0) {
                // letsGetStarted.setVisibility(View.VISIBLE);
                letsGetStarted.setText("Next");
            } else if (position == 1) {
                //letsGetStarted.setVisibility(View.VISIBLE);
                letsGetStarted.setText("Start");
            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}