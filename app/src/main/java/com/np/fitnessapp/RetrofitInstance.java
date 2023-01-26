package com.np.fitnessapp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofitFood;
    private static Retrofit retrofitExercise;
    public static final String BOOK_API_URL = "https://world.openfoodfacts.org/api/v2/";
    public static final String EXERCISE_API_URL = "https://api.api-ninjas.com/v1/";

    public static Retrofit getFoodRetrofitInstance() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        UserAgentInterceptor userAgentInterceptor = new UserAgentInterceptor("User-Agent: FitnessApp - Android - Version 1.0 - www.example.com");

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(userAgentInterceptor)
                .build();

        if(retrofitFood == null) {
            retrofitFood = new Retrofit.Builder()
                    .baseUrl(BOOK_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofitFood;
    }

    public static Retrofit getExerciseRetrofitInstance() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        ApiKeyHeaderInterceptor apiKeyHeaderInterceptor = new ApiKeyHeaderInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(apiKeyHeaderInterceptor)
                .build();

        if(retrofitExercise == null) {
            retrofitExercise = new Retrofit.Builder()
                    .baseUrl(EXERCISE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofitExercise;
    }

    private static class UserAgentInterceptor implements Interceptor {
        private final String userAgent;

        public UserAgentInterceptor(String userAgent) {
            this.userAgent = userAgent;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .header("User-Agent", userAgent)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }

    private static class ApiKeyHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("X-Api-Key", "mRQnTZt2DaRjsdhqWojWbg==wT4Tgwp6pUKn1XSp")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        }
    }
}
