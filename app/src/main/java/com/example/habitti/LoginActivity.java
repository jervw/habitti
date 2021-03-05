package com.example.habitti;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPrefs;
    private String UserName = "UserName";
    private final String UserNameKey = "LastUserName";
    private int UserClothes;
    private final String UserClothesKey = "LastUserClothes";
    private int UserHairs;
    private final String UserHairsKey = "LastUserHairs";
    private int UserSex;
    private final String UserSexKey = "LastUserSex";

    private EditText SingUpName;
    private Button btnNext;

    private ImageView imageViewCharacter;
    private ImageView imageViewClothes;
    private Button btnChangeToFemale;
    private Button btnChangeToMale;
    private Button btnChangeClothes;
    private Button btnChangeHairs;
    private ImageView imageViewHairs;

    int[] hairsImages;
    int[] clothesImages;
    private int currentImageClothes;
    private int currentImageHairs;
    private int currentCharacterSex;
    boolean devMode = false;

    public static final String EXTRA_MESSAGE = "com.example.habitti";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Log.d("LOGIN", "OnCreate()");

        // GO TO THE MAIN FRAGMENT, IF THE NAME WAS ALREADY CREATED
        sharedPrefs = this.getSharedPreferences("shared preference", MODE_PRIVATE);
        String name;

        if (sharedPrefs.contains("LastUserName")) {
            name = sharedPrefs.getString("LastUserName", "");

            if (!name.equals("")) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

            if (name.equals("")) {
                Log.d("LOGIN", "What is your name?");
            }

            if (name.equals("...dev...")) {
                Log.d("LOGIN", "dev mode");
                devMode = true;
            }
        }

        
        SingUpName = (EditText) findViewById(R.id.editTextSingUpName);
        btnNext = (Button) findViewById(R.id.btnSingUp);


        // NEXT BUTTON:
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // SAVE:
                sharedPrefs = getSharedPreferences("shared preference", Context.MODE_PRIVATE);
                UserName = sharedPrefs.getString(UserNameKey, "0");

                UserClothes = sharedPrefs.getInt(UserClothesKey, 0);
                UserHairs = sharedPrefs.getInt(UserHairsKey, 0);
                UserSex = sharedPrefs.getInt(UserSexKey, 0);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

                //SAVE USER DATA:
                SaveLoad.getInstance().saveCharacterImages(LoginActivity.this, SingUpName.getText().toString(), UserNameKey, currentImageClothes, UserClothesKey,
                        currentImageHairs, UserHairsKey, currentCharacterSex, UserSexKey);
            }
        });


        // CHARACTER SEX:
        imageViewCharacter = (ImageView) findViewById(R.id.imageViewCharacter);

        btnChangeToFemale = (Button) findViewById(R.id.btnFemale);
        btnChangeToFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewCharacter.setImageResource(R.drawable.char_6);
                currentCharacterSex = 1;
            }
        });

        btnChangeToMale = (Button) findViewById(R.id.btnMale);
        btnChangeToMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewCharacter.setImageResource(R.drawable.char_7);
                currentCharacterSex = 0;
            }
        });


        // CHARACTER CLOTHES:
        imageViewClothes = (ImageView) findViewById(R.id.imageViewClothes);
        btnChangeClothes = (Button) findViewById(R.id.btnChangeClothes);
        clothesImages = new int[] {R.drawable.char_13, R.drawable.char_2, R.drawable.char_15, R.drawable.char_10, R.drawable.char_14};

        btnChangeClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentImageClothes++;
                currentImageClothes = currentImageClothes % clothesImages.length;
                imageViewClothes.setImageResource(clothesImages[currentImageClothes]);
            }
        });


        // CHARACTER HAIRS:
        imageViewHairs = (ImageView) findViewById(R.id.imageViewHairs);
        btnChangeHairs = (Button) findViewById(R.id.btnChangeHairs);
        hairsImages = new int[] {R.drawable.char_5, R.drawable.char_4, R.drawable.char_8, R.drawable.char_11, R.drawable.char_12, R.drawable.char_9};

        btnChangeHairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentImageHairs++;
                currentImageHairs = currentImageHairs % hairsImages.length;
                imageViewHairs.setImageResource(hairsImages[currentImageHairs]);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LOGIN", "OnPause() LoginActivity");
    }
}
