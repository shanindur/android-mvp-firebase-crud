package com.spacebar.firebasemvp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spacebar.firebasemvp.R;
import com.spacebar.firebasemvp.core.MainActivityContract;
import com.spacebar.firebasemvp.core.MainActivityPresenter;
import com.spacebar.firebasemvp.helper.Config;
import com.spacebar.firebasemvp.helper.CreatePlayerDialog;
import com.spacebar.firebasemvp.model.Player;

public class MainActivity extends AppCompatActivity implements CreatePlayerDialog.CreatePlayerDialogListener, MainActivityContract.View {

    public MainActivityPresenter mPresenter;
    public ProgressBar mProgressBar;
    public DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mReference = FirebaseDatabase.getInstance().getReference().child(Config.USER_NODE);

        mPresenter = new MainActivityPresenter(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

    }

    private void openDialog() {
        CreatePlayerDialog createPlayerDialog = new CreatePlayerDialog();
        createPlayerDialog.show(getSupportFragmentManager(),"create dialog");
    }

    @Override
    public void savePlayer(Player player) {
        String key = mReference.push().getKey();
        Player newPlayer = new Player(player.getName(),player.getAge(),player.getPosition(),key);
        mPresenter.createNewPlayer(mReference, newPlayer);
    }

    @Override
    public void onCreatePlayerSuccessful() {
        Toast.makeText(this, "New Player Created!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreatePlayerFailure() {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessStart() {
        mProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void onProcessEnd() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}