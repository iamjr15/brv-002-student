package com.studentapp.main.detailedPoll;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.studentapp.R;
import com.studentapp.contants.Constants;
import com.studentapp.main.home.model.PollsModel;
import com.studentapp.utils.Utils;
import com.studentapp.viewmodel.DisplayDetailedPollViewModel;

import java.util.HashMap;
import java.util.Map;

public class DisplayDetailedPollActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private Toast toastMessage;
    private TextView questionTextView;
    private PollsModel pollsModel;
    private DisplayDetailedPollViewModel displayDetailedPollViewModel;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_detailed_poll);

        displayDetailedPollViewModel = ViewModelProviders.of(this).get(DisplayDetailedPollViewModel.class);

        Intent intent = getIntent();
        pollsModel = (PollsModel) intent.getSerializableExtra("data");
        position = intent.getIntExtra("position",-1);


        questionTextView = findViewById(R.id.question);
        questionTextView.setText(pollsModel.getQuestion());

        linearLayout = findViewById(R.id.parentOfOptionTextViews);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.removeAllViews();
        initiateLayout();
    }

    private void initiateLayout(){

        HashMap<String, String> userMap = new HashMap<String, String>();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, (int) getResources().getDimension(R.dimen.margin_5dp),
                0,(int) getResources().getDimension(R.dimen.margin_5dp));

        for( int i = 0; i < pollsModel.getOptions().size(); i++ )
        {
            TextView textView = new TextView(this);
            textView.setText(pollsModel.getOptions().get(i));
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

                userMap.put("pollId",pollsModel.getPollId());
                userMap.put("pollOption", textView.getText().toString());
                displayDetailedPollViewModel.insertAnsweredOption(userMap,
                        Utils.getString(Constants.SCHOOL_ID),Utils.getString(Constants.STUDENT_ID))
                        .observe(this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean success) {
                                if (success)
                                    insertOptionInPoll(textView.getText().toString());


                            }
                        });
            });
        }
    }

    private void insertOptionInPoll(String selectedOption){
        HashMap<String, String> pollMap = new HashMap<String, String>();
        pollMap.put("studentId",Utils.getString(Constants.STUDENT_ID));
        pollMap.put("option", selectedOption);


        displayDetailedPollViewModel.insertAnsweredOptionInPoll(pollMap,
                Utils.getString(Constants.SCHOOL_ID),pollsModel.getPollId())
                .observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean success) {
                        pollsModel.setAnswered(success);
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("answer",pollsModel);
                        resultIntent.putExtra("position",position);
                        setResult(1,resultIntent);
                        Toast.makeText(getApplicationContext(),"Result: "+success,Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

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
