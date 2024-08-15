package com.example.javaproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public SliderAdapter(Context context) {
        this.context=context;
    }
    int[] imagesArray ={
            R.drawable.loginpic1,
            R.drawable.loginpic2,
            R.drawable.loginpic3
    };
    String[] headingArray ={
            "Fast Delivery",
            "Quality Products",
            "Easy Return Policy"
    };
    String[] descriptionArray ={
            "As the leader of our E-commerce App, I made sure that when customers order something, we get it to them quickly. We did this by working with fast delivery companies and making our delivery process smoother. This helps make our customers happy and keeps them coming back to shop with us again.",
            "As the leader of our E-commerce App, I emphasized offering only the best quality products to our customers. We carefully select items from trusted suppliers, ensuring they meet our high standards for durability, performance, and safety. Providing top-notch products ensures customer satisfaction and builds long-lasting trust in our brand.",
            "As the Head of our E-commerce App, I prioritized a hassle-free return policy, ensuring customers can easily return items they're not satisfied with. Our user-friendly process provides peace of mind, fostering trust and encouraging repeat purchases. We believe in making shopping with us a positive experience from start to finish."
    };
    @Override
    public int getCount() {
        return descriptionArray.length;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slidinglayout,container,false);
        ImageView image=view.findViewById(R.id.slider_img);
        TextView heading=view.findViewById(R.id.heading);
        TextView description=view.findViewById(R.id.description);

        image.setImageResource(imagesArray[position]);
        heading.setText(headingArray[position]);
        description.setText(descriptionArray[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
