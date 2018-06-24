package nyc.c4q.shannonalexander_navarro.dogexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nyc.c4q.shannonalexander_navarro.dogexample.models.RandomDog;
import nyc.c4q.shannonalexander_navarro.dogexample.network.DogNetwork;
import nyc.c4q.shannonalexander_navarro.dogexample.network.DogService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreedsActivity extends AppCompatActivity {

    TextView welcomeTV;
    ImageView terrierIV, retrieverIV, spanielIV, poodleIV;
    CardView spanielCard, terrierCard, retrieverCard, poodleCard;
    DogService dogNetwork = DogNetwork.createService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds);

        welcomeTV = findViewById(R.id.welcomeTV);
        checkSharedPref();
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setImageViews("terrier", terrierIV);
        setImageViews("poodle", poodleIV);
        setImageViews("retriever", retrieverIV);
        setImageViews("spaniel", spanielIV);
        selectBreed();
    }

    private void checkSharedPref() {
        Intent intent = getIntent();
        String got = intent.getStringExtra("pass");
        welcomeTV.setText("What kind of dog would you like to see " +got+ "?");
        Log.d("tellme", got + "");
    }

    private void initViews(){

        terrierIV = findViewById(R.id.terrierIV);
        retrieverIV = findViewById(R.id.retrieverIV);
        spanielIV = findViewById(R.id.spanielIV);
        poodleIV = findViewById(R.id.poodleIV);
        terrierCard = findViewById(R.id.terrierCard);
        retrieverCard = findViewById(R.id.retrieverCard);
        spanielCard = findViewById(R.id.spanielCard);
        poodleCard = findViewById(R.id.poodleCard);

    }

    private void goToDogActivity(String breed){

        Intent intent = new Intent(BreedsActivity.this, DogActivity.class);
        intent.putExtra("BreedKey", breed+"");
        startActivity(intent);
    }

    private void selectBreed(){

        poodleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDogActivity("poodle");
            }
        });
        retrieverCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDogActivity("retriever");
            }
        });
        terrierCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDogActivity("terrier");
            }
        });
        spanielCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDogActivity("spaniel");
            }
        });
    }

    private void setImageViews(String breed, final ImageView v){

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        DogService dogService = retrofit.create(DogService.class);


        Call<RandomDog> dogCall =dogNetwork.getARandomDog(breed);

        dogCall.enqueue(new Callback<RandomDog>() {
            @Override
            public void onResponse(Call<RandomDog> call, Response<RandomDog> response) {
                RandomDog randomDog = response.body();

                String dogURL = randomDog.getMessage();
                if (response.isSuccessful()){

                    Picasso.with(BreedsActivity.this).load(dogURL).into(v);
                    Log.d("breed?", dogURL+"");
                } else {
                    Log.d("failure", response.errorBody().toString()+"");

                }

            }

            @Override
            public void onFailure(Call<RandomDog> call, Throwable t) {

            }
        });

    }
}
