package com.spacebar.firebasemvp.core;

import com.google.firebase.database.DatabaseReference;
import com.spacebar.firebasemvp.model.Player;

public class MainActivityPresenter implements MainActivityContract.Presenter, MainActivityContract.onOperationListener {

    private MainActivityContract.View mView;
    private MainActivityInteractor mInteractor;

    public MainActivityPresenter(MainActivityContract.View mView) {
        this.mView = mView;
        mInteractor = new MainActivityInteractor(this);
    }

    @Override
    public void createNewPlayer(DatabaseReference reference, Player player) {
        mInteractor.performCreatePlayer(reference, player);
    }

    @Override
    public void onSuccess() {
        mView.onCreatePlayerSuccessful();
    }

    @Override
    public void onFailure() {
        mView.onCreatePlayerFailure();
    }

    @Override
    public void onStart() {
        mView.onProcessStart();
    }

    @Override
    public void onEnd() {
        mView.onProcessEnd();
    }
}
