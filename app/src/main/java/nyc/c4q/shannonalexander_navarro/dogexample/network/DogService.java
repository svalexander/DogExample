package nyc.c4q.shannonalexander_navarro.dogexample.network;

import nyc.c4q.shannonalexander_navarro.dogexample.models.DogByBreed;
import nyc.c4q.shannonalexander_navarro.dogexample.models.RandomDog;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


public interface DogService {


    @GET("api/breed/{breed}/images/random")
    Call<RandomDog> getARandomDog(@Path("breed") String breed);

    @GET("api/breed/{breed}/images")
    Observable<DogByBreed> getDogsByBreed(@Path("breed") String breed);
}
