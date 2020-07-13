package com.spacebar.firebasemvp.view.main;

import com.google.firebase.database.DatabaseReference;
import com.spacebar.firebasemvp.model.Player;

import java.util.ArrayList;

public interface MainActivityContract {
    interface View{
        void onCreatePlayerSuccessful();
        void onCreatePlayerFailure();
        void onProcessStart();
        void onProcessEnd();
        void onPlayerRead(ArrayList<Player> players);
        void onPlayerUpdate(Player player);
    }

    interface Presenter{
        void createNewPlayer(DatabaseReference reference, Player player);
        void readPlayer(DatabaseReference reference);
        void updatePlayer(DatabaseReference reference, Player player);
    }

    interface Interactor{
        void performCreatePlayer(DatabaseReference reference, Player player);
        void performReadPlayer(DatabaseReference reference);
        void performUpdatePlayer(DatabaseReference reference, Player player);
    }

    interface onOperationListener{
        void onSuccess();
        void onFailure();
        void onStart();
        void onEnd();
        void onRead(ArrayList<Player> players);
        void onUpdate(Player player);
    }
}
