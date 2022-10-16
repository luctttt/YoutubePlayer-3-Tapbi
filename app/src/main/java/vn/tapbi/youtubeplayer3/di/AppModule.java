package vn.tapbi.youtubeplayer3.di;

import android.app.Application;

import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.tapbi.youtubeplayer3.data.local.db.RecentDatabase;
import vn.tapbi.youtubeplayer3.data.remote.ApiInterface;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    public static String BASE_URL = "https://www.googleapis.com/youtube/v3/";

    @Provides
    @Singleton
    public static Retrofit getApiClient() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public RecentDatabase recentDatabase(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(),RecentDatabase.class ,RecentDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .addMigrations(RecentDatabase.MIGRATION_1_2)
                .build();
    }

    @Provides
    @Singleton
    public static ApiInterface providesVideoApi(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }
}
