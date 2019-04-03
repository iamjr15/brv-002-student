package com.studentapp.main.detailedPoll;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
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
    private int current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_detailed_poll);

        linearLayout = findViewById(R.id.parentOfOptionTextViews);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,5,0,5);
        for( int i = 0; i < 3; i++ )
        {
            TextView textView = new TextView(this);
            textView.setText("option "+(i+1));
            textView.setBackgroundResource(R.drawable.rounded_corner);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setLayoutParams(params);
            textView.setId(Integer.parseInt(String.valueOf(i)));
            final int id_ = textView.getId();
            textView.setPadding(10,5,10,5);
            linearLayout.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (toastMessage!= null) {
                        toastMessage.cancel();
                    }
                    toastMessage = Toast.makeText(getApplicationContext(), "Button with id =" + id_ +
                            " is clicked",Toast.LENGTH_SHORT);
                    current = id_;
                    toastMessage.show();

                    setAllTextColorAsBlack();

                    textView.setTextColor(getResources().getColor(R.color.white));
                    textView.setBackgroundResource(R.drawable.update_rounded_corner);
                }
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
            textView.setTextColor(getResources().getColor(R.color.fontBlack));
            textView.setBackgroundResource(R.drawable.rounded_corner);
        }
    }

}
