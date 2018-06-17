package nyc.c4q.shannonalexander_navarro.dogexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.shannonalexander_navarro.dogexample.models.DogByBreed;
import nyc.c4q.shannonalexander_navarro.dogexample.network.DogNetwork;
import nyc.c4q.shannonalexander_navarro.dogexample.network.DogService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogActivity extends AppCompatActivity {

    TextView chosenBreedTV;
    String chosenBreed;
    RecyclerView rv;
    DogAdapter dogAdapter;
    List<String> list = new ArrayList<>();
    DogService dogNetwork = DogNetwork.createService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);
        initViews();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getDogsJSON(chosenBreed);
        initRV();

    }

    private void initViews(){
        chosenBreedTV = findViewById(R.id.breedTV);
        getBreed();
        chosenBreedTV.setText(chosenBreed);
    }
    private String getBreed(){
        Intent intent = getIntent();
         chosenBreed = intent.getStringExtra("BreedKey");

        return chosenBreed;
    }

    private void initRV() {

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        dogAdapter = new DogAdapter(list);
        rv.setAdapter(dogAdapter);
    }

    private void getDogsJSON(String breed){

      //  DogNetwork dogNetwork = new DogNetwork();

        Call<DogByBreed> breedCall = dogNetwork.getDogsByBreed(breed);
        breedCall.enqueue(new Callback<DogByBreed>() {
            @Override
            public void onResponse(Call<DogByBreed> call, Response<DogByBreed> response) {
                DogByBreed dogByBreed = response.body();

                List<String> responseDogs = dogByBreed.getMessage();
                list.clear();
                list.addAll(responseDogs);
                dogAdapter.notifyDataSetChanged();
                if (response.isSuccessful()){

                    Log.d("item?", list.get(0)+"");
                } else {
                    Log.d("failure", response.errorBody().toString()+"");

                }

            }


            @Override
            public void onFailure(Call<DogByBreed> call, Throwable t) {

            }
        });
    }
}
