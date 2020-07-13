package com.spacebar.firebasemvp.view.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.spacebar.firebasemvp.model.Player;

import java.util.ArrayList;

public class MainActivityInteractor implements MainActivityContract.Interactor {

    private MainActivityContract.onOperationListener mListener;
    private ArrayList<Player> players = new ArrayList<>();

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

    @Override
    public void performReadPlayer(DatabaseReference reference) {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Player player = dataSnapshot.getValue(Player.class);
                players.add(player);
                mListener.onRead(players);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Player player = dataSnapshot.getValue(Player.class);
                mListener.onUpdate(player);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void performUpdatePlayer(DatabaseReference reference, Player player) {
        mListener.onStart();
        reference.child(player.getKey()).setValue(player).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    mListener.onEnd();
                }else {
                    mListener.onEnd();
                }

            }
        });
    }
}
