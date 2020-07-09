package com.spacebar.firebasemvp.core;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.spacebar.firebasemvp.model.Player;

public class MainActivityInteractor implements MainActivityContract.Interactor {

    private MainActivityContract.onOperationListener mListener;

    public MainActivityInteractor(MainActivityContract.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performCreatePlayer(DatabaseReference reference, Player player) {
        mListener.onStart();
        reference.child(player.getKey()).setValue(player).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    mListener.onSuccess();
                }else {
                    mListener.onFailure();
                }
                mListener.onEnd();
            }
        });
    }
}
