package com.studentapp.main.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studentapp.R;
import com.studentapp.main.home.model.PollsModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PollListAdapter extends RecyclerView.Adapter<PollListAdapter.ViewHolder> {
    private Context context;
    private List<PollsModel> list;
    private View.OnClickListener onClickListener;

    public PollListAdapter(Context context, List<PollsModel> list, View.OnClickListener onClickListener){
        this.list = list;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.body_poll_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PollsModel pollsModel = list.get(position);
        holder.question.setText(pollsModel.getQuestion());
        holder.itemView.setOnClickListener(onClickListener);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public PollsModel getItem(int position){
        return list.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nameOfPollOwner, question;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfPollOwner = itemView.findViewById(R.id.pollOwner);
            question = itemView.findViewById(R.id.question);
        }
    }
}
