package com.spacebar.firebasemvp.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.spacebar.firebasemvp.R;
import com.spacebar.firebasemvp.model.Player;

public class UpdatePlayerDialog extends AppCompatDialogFragment {

    public EditText mName;
    public EditText mAge;
    public EditText mPosition;
    public Button mSaveBtn;
    public UpdatePlayerDialog.UpdatePlayerDialogListener mListener;
    public Player player;

    public UpdatePlayerDialog(Player player) {
        this.player = player;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view);
        builder.setTitle("Update Player");
        builder.setCancelable(true);

        mName = (EditText) view.findViewById(R.id.edt_name);
        mAge = (EditText) view.findViewById(R.id.edt_age);
        mPosition = (EditText) view.findViewById(R.id.edt_position);
        mSaveBtn = (Button) view.findViewById(R.id.btn_save);

        mName.setText(player.getName());
        mAge.setText(player.getAge());
        mPosition.setText(player.getPosition());

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String age = mAge.getText().toString();
                String position = mPosition.getText().toString();

                if (name.isEmpty() || age.isEmpty() || position.isEmpty()){
                    return;
                }else {
                    Player updatePlayer = new Player(name, age, position, player.getKey());
                    mListener.updatePlayer(updatePlayer);
                    dismiss();
                }
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (UpdatePlayerDialog.UpdatePlayerDialogListener) context;

    }

    public interface UpdatePlayerDialogListener{
        void updatePlayer(Player player);
    }
}