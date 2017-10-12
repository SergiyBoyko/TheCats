package com.example.android.thecats;

import android.app.Application;

import com.example.android.thecats.api.TheCatApi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by fbrsw on 11.10.2017.
 */

public class App extends Application {
    private static TheCatApi api;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://thecatapi.com") //Базовая часть адреса
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        api = retrofit.create(TheCatApi.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public static TheCatApi getApi() {
        return api;
    }
}
