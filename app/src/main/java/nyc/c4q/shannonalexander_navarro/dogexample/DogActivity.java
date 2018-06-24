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
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

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
        initRV();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDogBreed(chosenBreed).subscribe(addDogs);
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

    Observable<DogByBreed> getDogBreed(String chosenBreed){
        return dogNetwork.getDogsByBreed(chosenBreed).subscribeOn(Schedulers.newThread()).
                doOnNext(s-> Log.d("list?", "a list from observable"));
        //is now firing, but i still get an error later
    }

    Observer<DogByBreed> addDogs = new Subscriber<DogByBreed>() {
        @Override
        public void onCompleted() {

            Log.d("complete?", "completed");
        }

        @Override
        public void onError(Throwable e) {

            //i'm erroring
            Log.d("error?", "rxerror");
        }

        @Override
        public void onNext(DogByBreed dogByBreed) {
            List<String> responseDogs = dogByBreed.getMessage();
            list.addAll(responseDogs);
            dogAdapter.notifyDataSetChanged();
        }
    };
}
