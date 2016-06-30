package com.skyline.rxjavademo.network;

import com.skyline.rxjavademo.meta.RequestResponse;
import com.skyline.rxjavademo.meta.WeatherData;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jairus on 16/6/28.
 */
public class ApiClient {

	protected static Retrofit retrofit = null;

	public static Retrofit retrofit() {
		if (retrofit != null) {
			return retrofit;
		}
		synchronized (ApiClient.class) {
			if (retrofit != null) {
				return retrofit;
			}
			retrofit = new Retrofit.Builder()
					.baseUrl("http://weather.mail.163.com/")
					.addConverterFactory(GsonConverterFactory.create())
					.build();
		}
		return retrofit;
	}

	public interface GetWeatherService {
		@GET("weather/xhr/weather/info.do")
		Call<RequestResponse<WeatherData>> get(@Query("city") String location);
	}

	public static Call<RequestResponse<WeatherData>> getWeatherData(String location) {
		return retrofit().create(GetWeatherService.class).get(location);
	}
}
