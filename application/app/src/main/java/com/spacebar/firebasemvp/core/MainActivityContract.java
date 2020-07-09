package com.spacebar.firebasemvp.core;

import com.google.firebase.database.DatabaseReference;
import com.spacebar.firebasemvp.model.Player;

public interface MainActivityContract {
    interface View{
        void onCreatePlayerSuccessful();
        void onCreatePlayerFailure();
        void onProcessStart();
        void onProcessEnd();
    }

    interface Presenter{
        void createNewPlayer(DatabaseReference reference, Player player);
    }

    interface Interactor{
        void performCreatePlayer(DatabaseReference reference, Player player);
    }

    interface onOperationListener{
        void onSuccess();
        void onFailure();
        void onStart();
        void onEnd();
    }
}
