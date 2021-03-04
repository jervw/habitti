package com.example.habitti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class MainFragment extends Fragment {

    private SharedPreferences sharedPrefHabbits;
    private final String sharedPreferenceName = "shared preference";

    SaveLoad saveLoad = SaveLoad.getInstance();

    int[] clothesImages;
    int[] hairsImages;

    ListView habbitsListView;
    View rootView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Log.d("MAIN FRAGMENT", "OnCreate");
        Button devButton = (Button) rootView.findViewById(R.id.buttonDevAddDay);
        dateCheck dateCheck = new dateCheck(getActivity());

       if (!LoginActivity.developerMode) {
           devButton.setVisibility(View.GONE);
       }

        //Load saved preferences and put them on screen
        loadHabbitData();
        updateUI();

        devButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Tag", "devButton pressed");
                dateCheck.devAddDay();
                updateUI();
                Log.d("Tag", "DevButton executed");
            }
        });

        Button shopBtn = (Button) rootView.findViewById(R.id.ShopBtn);
        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MAIN", "Shop onClick()");
                Intent intent = new Intent(getActivity(), ShopPopUp.class);
                getActivity().startActivity(intent);
                //startActivity(new Intent(getActivity(), PopUp.class));
            }
        });
        return rootView;
    }

    private void saveHabbitData() {
        sharedPrefHabbits = getActivity().getSharedPreferences(sharedPreferenceName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefHabbits.edit();
        Gson gson = new Gson();
        String jsonHabbits = gson.toJson(GlobalModel.getInstance().getHabbitsList());
        editor.putString("Habbits list", jsonHabbits);
        editor.apply();
        Log.d("Tag", "Saved");
    }

    public void loadHabbitData() {
        sharedPrefHabbits = getActivity().getSharedPreferences(sharedPreferenceName, Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonHabbits = sharedPrefHabbits.getString("Habbits list", null);
        Type typeHabbits = new TypeToken<Collection<Habbit>>() {
        }.getType();
        GlobalModel.getInstance().replaceListHabbits(gson.fromJson(jsonHabbits, typeHabbits));
        //Go check if day has passed since last app start and give points accordingly
        if (MainActivity.firstCheckOfDay == true) {
            dateCheck.checkDate();
            MainActivity.firstCheckOfDay = false;
        }
    }

    private void updateUI() {
        //Check if ArrayList from GlobalModel is set null by sharedPreferences
        //If yes, create new ArrayList. If no get that ArrayList from GlobalModel. Put that ArrayList to ArrayAdapter
        //Find listView by id and set ArrayAdapter to it. Then save current habbits from that ArrayList.
        HabbitsViewAdapter habbitsArrayAdapter;
        if (GlobalModel.getInstance().getHabbitsView() == null) {
            habbitsArrayAdapter = new HabbitsViewAdapter(getActivity(), new ArrayList<HabbitsView>());
        } else {
            habbitsArrayAdapter = new HabbitsViewAdapter(getActivity(), GlobalModel.getInstance().getHabbitsView());
        }
        habbitsListView = (ListView) rootView.findViewById(R.id.listViewHabbits);
        habbitsListView.setAdapter(habbitsArrayAdapter);
        saveHabbitData();


        habbitsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> AdapterView, View view, int i, long l) {
                Intent showDetails = new Intent(getActivity(), HabbitDetails.class);
                showDetails.putExtra("EXTRA", i);
                startActivity(showDetails);
            }
        });


        // GET NAME FROM SHARED PREFERENCE.XML:
        sharedPrefHabbits = this.getActivity().getSharedPreferences("shared preference", Context.MODE_PRIVATE);
        TextView textViewUserName = (TextView) rootView.findViewById(R.id.username);
        if (sharedPrefHabbits.contains("LastUserName")) {
            textViewUserName.setText(sharedPrefHabbits.getString("LastUserName", ""));
        }

        // GET CLOTHES FROM SHARED PREFERENCE.XML:
        clothesImages = new int[] {R.drawable.char_13, R.drawable.char_2, R.drawable.char_15, R.drawable.char_10, R.drawable.char_14};
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
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
