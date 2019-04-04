package com.studentapp.main.home.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.studentapp.R;

public class DetailedPollFragment extends Fragment {

    private LinearLayout linearLayout;
    private Toast toastMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_poll, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String arr[] = {"momentum","gravitation","mechanical properties of matter"};

        linearLayout = getView().findViewById(R.id.parentOfOptionTextViews);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, (int) getResources().getDimension(R.dimen.margin_5dp),
                0,(int) getResources().getDimension(R.dimen.margin_5dp));

        for( int i = 0; i < 3; i++ )
        {
            TextView textView = new TextView(getContext());
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
                toastMessage = Toast.makeText(getContext(), "Button with id =" + id_ +
                        " is clicked",Toast.LENGTH_SHORT);
                toastMessage.show();

                setAllTextColorAsBlack();

                textView.setTextColor(getResources().getColor(R.color.white));
                textView.setBackgroundResource(R.drawable.update_rounded_corner);
            });
        }
    }

    private void setAllTextColorAsBlack() {
        if (linearLayout == null) {
            return;
        }

        int childCount = linearLayout.getChildCount();

        for (int i = 0; i < childCount; i++) {
            TextView textView = (TextView) linearLayout.getChildAt(i);
            textView.setTextColor(getResources().getColor(R.color.colorGray));
            textView.setBackgroundResource(R.drawable.text_view_rounded_corner);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
