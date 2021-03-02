package com.example.habitti;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

public class HabbitDetails extends AppCompatActivity {
    private TextView detailsHabbitName;
    private ImageView detailsHabbitImage;
    private TextView detailsHabbitDateCreated;
    private TextView detailsHabbitDaysStreak;
    private TextView detailsHabbitScores;
    private TextView detailsHabbitMultiplier;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habbit_details);
        Bundle b = getIntent().getExtras();
        int i = b.getInt("EXTRA");

        detailsHabbitName = findViewById(R.id.textViewDetailsHabbitName);
        detailsHabbitImage = findViewById(R.id.imageViewDetailsHabbitImage);
        detailsHabbitDateCreated = findViewById(R.id.textViewDetailsHabbitDateCreated);
        detailsHabbitDaysStreak = findViewById(R.id.textViewDetailsHabbitDaysStreak);
        detailsHabbitScores = findViewById(R.id.textViewDetailsHabbitScores);
        detailsHabbitMultiplier = findViewById(R.id.textViewDetailsHabbitMultiplier);

        String dateCreated = sdf.format(GlobalModel.getInstance().getHabbitViewItem(i).getDateCreated());
        detailsHabbitName.setText(GlobalModel.getInstance().getHabbitViewItem(i).getmHabbitName());
        detailsHabbitImage.setImageResource(GlobalModel.getInstance().getHabbitViewItem(i).getIvHabbitImageIdImageId());
        detailsHabbitDateCreated.setText("Date created: " + dateCreated);
        detailsHabbitDaysStreak.setText(GlobalModel.getInstance().getHabbitViewItem(i).getmHabbitDaysStreak());
        detailsHabbitScores.setText(GlobalModel.getInstance().getHabbitViewItem(i).getmHabbitScores());
        detailsHabbitMultiplier.setText("Current score multiplier: " + Double.toString(GlobalModel.getInstance().getHabbitViewItem(i).getScoreMultiplier()));
        }

    public void buttonClickedDelete(View view) {
        Bundle b = getIntent().getExtras();
        int i = b.getInt("EXTRA");
        GlobalModel.getInstance().deleteHabbit(i);
        finish();

    }


}
