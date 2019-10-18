package com.example.fantasyfootball;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddPlayer extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Intent intent;
    Bundle bundle;
    long id;

    Spinner positionSpinner;
    EditText nameEditText;
    Spinner teamSpinner;

    String positions;
    String teams;

    DBHandler dbHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        positionSpinner = (Spinner) findViewById(R.id.positionSpinner);
        teamSpinner = (Spinner) findViewById(R.id.teamSpinner);

        ArrayAdapter<CharSequence> positionAdapter = ArrayAdapter.createFromResource(this, R.array.positions, android.R.layout.simple_spinner_item);
        positionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        positionSpinner.setAdapter(positionAdapter);
        positionSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> teamAdapter = ArrayAdapter.createFromResource(this, R.array.teams, android.R.layout.simple_spinner_item);
        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamSpinner.setAdapter(teamAdapter);
        teamSpinner.setOnItemSelectedListener(this);


        dbHandler = new DBHandler(this, null);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_home :
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_player :
                intent = new Intent(this, AddPlayer.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }

    public void addPlayer(MenuItem menuItem){
        String name = nameEditText.getText().toString();

        if(name.trim().equals("") || positions.trim().equals("") || teams.trim().equals("")){
            Toast.makeText(this, "Please enter a name, position, and team!", Toast.LENGTH_LONG).show();
        }
        else{
            dbHandler.addPlayerList(name, positions, teams);
            Toast.makeText(this, "You just added " + name + " to the " + teams, Toast.LENGTH_LONG).show();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

            //set it icon, title, and text
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("Fantasy Football");
            builder.setContentText("Way to go " +  teams + "'s! Way to go!");

            //initialize an Intent for the Main Activity
            intent = new Intent(this, MainActivity.class);

            //initialize a PendingIntent
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            //set the content intent of the Notification
            builder.setContentIntent(pendingIntent);

            //initialize a NotificationManger
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            //have the NotificationManager send the Notification
            notificationManager.notify(2142, builder.build());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView.equals(positionSpinner)) {
            positions = adapterView.getItemAtPosition(position).toString();
        }
        else if(adapterView.equals(teamSpinner)) {
            teams = adapterView.getItemAtPosition(position).toString();
        }
}


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
