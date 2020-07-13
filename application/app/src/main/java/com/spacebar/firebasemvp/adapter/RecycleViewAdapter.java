package com.spacebar.firebasemvp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spacebar.firebasemvp.R;
import com.spacebar.firebasemvp.model.Player;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.RecycleViewHolder> {

    public ArrayList<Player> players;
    public onPlayerListener mOnPlayerListener;

    public RecycleViewAdapter(ArrayList<Player> players, onPlayerListener onPlayerListener) {
        this.players = players;
        this.mOnPlayerListener = onPlayerListener;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item,null);
        RecycleViewHolder recycleViewHolder = new RecycleViewHolder(view, mOnPlayerListener);

        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {

        holder.mName.setText(players.get(position).getName());
        holder.mAge.setText(players.get(position).getAge());
        holder.mPosition.setText(players.get(position).getPosition());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mName, mAge, mPosition;
        public Button mUpdateBtn, mDeleteBtn;
        public onPlayerListener mListener;

        public RecycleViewHolder(@NonNull View itemView, onPlayerListener onPlayerListener) {
            super(itemView);

            this.mListener = onPlayerListener;

            mName = (TextView) itemView.findViewById(R.id.txt_name);
            mAge = (TextView) itemView.findViewById(R.id.txt_age);
            mPosition = (TextView) itemView.findViewById(R.id.txt_position);
            mUpdateBtn = (Button) itemView.findViewById(R.id.btn_update);
            mDeleteBtn = (Button) itemView.findViewById(R.id.btn_delete);

            mUpdateBtn.setOnClickListener(this);
            mDeleteBtn.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_update:
                    mListener.onPlayerUpdateClick(getAdapterPosition());
                    break;
                case R.id.btn_delete:
                    mListener.onPlayerDeleteClick(getAdapterPosition());
                    break;
            }
        }
    }

    public interface onPlayerListener{
        void onPlayerUpdateClick(int position);
        void onPlayerDeleteClick(int position);
    }



}
