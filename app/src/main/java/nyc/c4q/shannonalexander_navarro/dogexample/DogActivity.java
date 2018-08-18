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
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class DogActivity extends AppCompatActivity {

    TextView chosenBreedTV;
    String chosenBreed;
    RecyclerView rv;
    DogAdapter dogAdapter;
    List<String> list = new ArrayList<>();
    DogService dogService = DogNetwork.createService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);
        initViews();
        initRV();
    }

    @Override
    protected void onStart() {
        super.onStart();
        dogService.getDogsByBreed(chosenBreed)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DogByBreed>() {
            @Override
            public void onCompleted() {
                Log.d("complete?", "completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("error?", "rxerror");
            }

            @Override
            public void onNext(DogByBreed dogByBreed) {
                Log.d("next?", "rxnext");
                List<String> responseDogs = dogByBreed.getMessage();
                list.addAll(responseDogs);
                dogAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initViews() {
        chosenBreedTV = findViewById(R.id.breedTV);
        getBreed();
        chosenBreedTV.setText(chosenBreed);
    }

    private String getBreed() {
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
}
