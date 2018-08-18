package nyc.c4q.shannonalexander_navarro.dogexample.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;


public class DogNetwork {

    static final String BASE_URL = "https://dog.ceo/";

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build();

//moved this class and interface to diff package, bc of this i needed to create a method that would return a dogservice
    //so that id have access to it in other classes outside of the package
    public static DogService createService() {

        DogService dogService = retrofit.create(DogService.class);

        return dogService;
    }
}
