package gerstle.weather;

import io.reactivex.rxjava3.core.Single;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import static org.junit.Assert.*;

public class WeatherJSonServiceTest
{
    @Test
    public void getCurrentWeather()
    {
        //given
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        WeatherJSonService service = retrofit.create(WeatherJSonService.class);

        //when
        Single<OpenWeatherMapFeed> single = service.getCurrentWeather("New York");
        //no sapposed to but allowed for right now
        OpenWeatherMapFeed feed = single.blockingGet();

        //then
        assertNotNull(feed);
        assertNotNull(feed.main);
        assertTrue(feed.main.temp > 0.0);
    }
}