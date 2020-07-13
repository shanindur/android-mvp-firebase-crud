package com.spacebar.firebasemvp.view.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spacebar.firebasemvp.R;
import com.spacebar.firebasemvp.adapter.RecycleViewAdapter;
import com.spacebar.firebasemvp.dialog.UpdatePlayerDialog;
import com.spacebar.firebasemvp.helper.Config;
import com.spacebar.firebasemvp.dialog.CreatePlayerDialog;
import com.spacebar.firebasemvp.model.Player;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CreatePlayerDialog.CreatePlayerDialogListener,
        MainActivityContract.View, UpdatePlayerDialog.UpdatePlayerDialogListener, RecycleViewAdapter.onPlayerListener {

    public MainActivityPresenter mPresenter;
    public ProgressBar mProgressBar;
    public DatabaseReference mReference;
    public RecyclerView mRecyclerView;
    public RecycleViewAdapter mRecycleViewAdapter;
    public ArrayList<Player> mPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycleView);
        mReference = FirebaseDatabase.getInstance().getReference().child(Config.USER_NODE);

        mPresenter = new MainActivityPresenter(this);
        mPresenter.readPlayer(mReference);

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

    @Override
    public void onPlayerRead(ArrayList<Player> players) {

        this.mPlaylist = players;
        mRecycleViewAdapter = new RecycleViewAdapter(players, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mRecycleViewAdapter);
    }

    @Override
    public void onPlayerUpdate(Player player) {
        int index = getIndex(player);
        mPlaylist.set(index, player);
        mRecycleViewAdapter.notifyItemChanged(index);
    }

    @Override
    public void updatePlayer(Player player) {
        mPresenter.updatePlayer(mReference, player);
    }


    @Override
    public void onPlayerUpdateClick(int position) {
        Player player = mPlaylist.get(position);
        UpdatePlayerDialog updatePlayerDialog = new UpdatePlayerDialog(player);
        updatePlayerDialog.show(getSupportFragmentManager(),"update dialog");
    }

    @Override
    public void onPlayerDeleteClick(int position) {

    }

    public int getIndex(Player player){
        int index = 0;
        for (Player countPlayer: mPlaylist){
            if (countPlayer.getKey().trim().equals(player.getKey())){
                break;
            }
            index++;
        }
        return index;
    }
}