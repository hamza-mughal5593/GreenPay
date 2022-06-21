package com.machineries_pk.mrk.activities.Boarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.machineries_pk.mrk.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    public SliderAdapter(Context context){
        this.context=context;
    }
    public int[] slide_images={
            R.drawable.img_1,
            R.drawable.img_2
    };
    public String[] slide_headings={
            "Track","My Forest Card"
    };
    public String[] slide_paras={
            "Calculate your carbon footprint and understand your lifestyle with simple tracker",
            "After calculation, start helping climate change and offsetting carbon footprint by planting trees, and get real rewards of forest card from Santa Claus hometown, Finland!"
    };

    @Override
    public int getCount() {
        return 2;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view== (LinearLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_layout_2, container,false);


        ImageView slide_image= view.findViewById(R.id.slide_img);
        TextView slide_heading= view.findViewById(R.id.slide_heading);
        TextView slide_para= view.findViewById(R.id.slide_para);

        slide_image.setImageResource(slide_images[position]);
        slide_heading.setText(slide_headings[position]);
        slide_para.setText(slide_paras[position]);
        container.addView(view);

        return  view;
    }

    @Override
    public void destroyItem( ViewGroup container, int position,  Object object) {
        super.destroyItem(container, position, object);
        container.removeView((RelativeLayout)object);
    }
}