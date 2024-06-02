package com.example.customshop;

import androidx.annotation.NonNull;
import com.example.customshop.Models.RickAndMortyCharacter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class PaymentsRemoteAPI {
    private static final String BASE_URL = "https://rickandmortyapi.com/";
    private final NetworkServiceInterface networkService;
    private Call<RickAndMortyCharacter> activeCharacterCall;

    public interface NetworkCallResult<Result> {
        void callSuccess(Result result);
        void callFailure(Throwable error);
    }

    public PaymentsRemoteAPI() {
        this(new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build());
    }

    private PaymentsRemoteAPI(Retrofit retrofit) {
        networkService = retrofit.create(NetworkServiceInterface.class);
    }

    public void fetchCharacter(final NetworkCallResult<RickAndMortyCharacter> callResult) {
        Call<RickAndMortyCharacter> characterCall = networkService.getCharacter();
        activeCharacterCall = characterCall;
        characterCall.enqueue(new Callback<RickAndMortyCharacter>() {
            @Override
            public void onResponse(@NonNull Call<RickAndMortyCharacter> call, @NonNull Response<RickAndMortyCharacter> response) {
                if (call.isCanceled()) return;
                if (response.isSuccessful() && response.body() != null) {
                    callResult.callSuccess(response.body());
                } else {
                    callResult.callFailure(new Exception("Response is null or unsuccessful"));
                }
                activeCharacterCall = null;
            }

            @Override
            public void onFailure(@NonNull Call<RickAndMortyCharacter> call, @NonNull Throwable error) {
                if (call.isCanceled()) return;
                callResult.callFailure(error);
                activeCharacterCall = null;
            }
        });
    }

    public void cancelActiveCharacterCall() {
        if (activeCharacterCall != null) {
            activeCharacterCall.cancel();
            activeCharacterCall = null;
        }
    }

    public interface NetworkServiceInterface {
        @GET("api/character/2")
        Call<RickAndMortyCharacter> getCharacter();
    }
}