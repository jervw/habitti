package com.example.habitti;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private SharedPreferences sharedPrefHabbits;
    private final String sharedPreferenceName = "shared preference";
    TextView level;
    TextView loginStreak;

    int[] clothesImages;
    int[] hairsImages;

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        level = (TextView) rootView.findViewById(R.id.levelSettings);
        loginStreak = (TextView) rootView.findViewById(R.id.loginStreakSettings);
        level.setText("Level: " + GlobalModel.getInstance().getUserLevel());
        updateCharacterDetails();
        return rootView;

    }

    public void updateCharacterDetails() {



        // GET CLOTHES FROM SHARED PREFERENCE.XML:
        clothesImages = new int[] {R.drawable.char_13, R.drawable.char_2, R.drawable.char_15, R.drawable.char_10, R.drawable.char_14, R.drawable.char_16};
        ImageView imageViewCharacterClothes = (ImageView) rootView.findViewById(R.id.userClothesImage);
        if (sharedPrefHabbits.contains("LastUserClothes")) {
            imageViewCharacterClothes.setImageResource(clothesImages[sharedPrefHabbits.getInt("LastUserClothes", -1)]);
        }

        // GET HAIRS FROM SHARED PREFERENCE.XML:
        hairsImages = new int[] {R.drawable.char_5, R.drawable.char_4, R.drawable.char_8, R.drawable.char_11, R.drawable.char_12, R.drawable.char_9};
        ImageView imageViewCharacterHairs = (ImageView) rootView.findViewById(R.id.userHairsImage);
        if (sharedPrefHabbits.contains("LastUserHairs")) {
            imageViewCharacterHairs.setImageResource(hairsImages[sharedPrefHabbits.getInt("LastUserHairs", -1)]);
        }
        // GET SEX FROM SHARED PREFERENCE.XML:
        ImageView imageViewCharacter = (ImageView) rootView.findViewById(R.id.userCharacterImage);
        int i = 0;
        if (sharedPrefHabbits.contains("LastUserSex")) {
            i = sharedPrefHabbits.getInt("LastUserSex", -1);
        }
        if (i == 1) {
            imageViewCharacter.setImageResource(R.drawable.char_6);
        } else if (i == 0) {
            imageViewCharacter.setImageResource(R.drawable.char_7);
        }

        // GET NAME FROM SHARED PREFERENCE.XML:
        sharedPrefHabbits = this.getActivity().getSharedPreferences("shared preference", Context.MODE_PRIVATE);
        TextView textViewUserName = (TextView) rootView.findViewById(R.id.userNameSettings);
        if (sharedPrefHabbits.contains("LastUserName")) {
            textViewUserName.setText(sharedPrefHabbits.getString("LastUserName", ""));
        }

        sharedPrefHabbits = this.getActivity().getSharedPreferences("shared preference", Context.MODE_PRIVATE);
        TextView textViewLevel = (TextView) rootView.findViewById(R.id.levelSettings);
        if (sharedPrefHabbits.contains("LastUserName")) {
            textViewUserName.setText(sharedPrefHabbits.getString("LastUserName", ""));
        }


    }
}