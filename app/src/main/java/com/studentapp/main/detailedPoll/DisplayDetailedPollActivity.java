package com.studentapp.main.detailedPoll;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.studentapp.R;

public class DisplayDetailedPollActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private Toast toastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_detailed_poll);

        String arr[] = {"momentum","gravitation","mechanical properties of matter"};

        linearLayout = findViewById(R.id.parentOfOptionTextViews);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, (int) getResources().getDimension(R.dimen.margin_5dp),
                0,(int) getResources().getDimension(R.dimen.margin_5dp));

        for( int i = 0; i < 3; i++ )
        {
            TextView textView = new TextView(this);
            textView.setText(arr[i]);
            textView.setTextSize(getResources().getDimension(R.dimen.font_20dp));
            textView.setTextColor(getResources().getColor(R.color.colorGray));
            textView.setBackgroundResource(R.drawable.text_view_rounded_corner);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setLayoutParams(params);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setId(Integer.parseInt(String.valueOf(i)));
            final int id_ = textView.getId();
            textView.setPadding((int) getResources().getDimension(R.dimen.padding_50dp),
                    (int) getResources().getDimension(R.dimen.padding_15dp),
                    (int) getResources().getDimension(R.dimen.padding_50dp),
                    (int) getResources().getDimension(R.dimen.padding_15dp));
            linearLayout.addView(textView);
            textView.setOnClickListener(v -> {
                if (toastMessage!= null) {
                    toastMessage.cancel();
                }
                toastMessage = Toast.makeText(getApplicationContext(), "Button with id =" + id_ +
                        " is clicked",Toast.LENGTH_SHORT);
                toastMessage.show();

                setAllTextColorAsBlack();

                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setBackgroundResource(R.drawable.update_rounded_corner);
            });
        }
    }

    private void setAllTextColorAsBlack() {
        if(linearLayout == null) {
            return;
        }

        int childCount = linearLayout.getChildCount();

        for (int i = 0; i < childCount; i++) {
            TextView textView = (TextView) linearLayout.getChildAt(i);
            textView.setTextColor(getResources().getColor(R.color.colorGray));
            textView.setBackgroundResource(R.drawable.text_view_rounded_corner);
        }
    }

}
