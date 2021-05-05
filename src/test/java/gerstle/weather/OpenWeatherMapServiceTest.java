package gerstle.weather;

import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import static org.junit.Assert.*;

public class OpenWeatherMapServiceTest
{
    @Test
    public void getCurrentWeather()
    {
        //given
        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService service = factory.newInstance();

        //when
        //no sapposed to but allowed for right now
        OpenWeatherMapFeed feed = service.getCurrentWeather("New York", "imperial").blockingGet();

        //then
        assertNotNull(feed);
        assertNotNull(feed.main);
        assertTrue(feed.main.temp > 0.0);
        assertTrue(feed.main.temp < 150.0);
        assertTrue(feed.dt > 0);
        assertEquals("New York", feed.name);
    }
    @Test
    public void getWeatherForecast()
    {
        //given
        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService service = factory.newInstance();

        //when
        //no sapposed to but allowed for right now
        OpenWeatherMapForecast forcast = service.getWeatherForecast("New York", "imperial").blockingGet();

        //then
        assertNotNull(forcast);
        assertNotNull(forcast.list);
        assertFalse(forcast.list.isEmpty());
        assertTrue(forcast.list.get(0).dt > 0);
        assertNotNull(forcast.list.get(0).weather);
    }
    @Test
    public void getWeatherForecast_getForecastFor()
    {
        // given
        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService service = factory.newInstance();
        OpenWeatherMapForecast forecast = service.getWeatherForecast("New York", "imperial")
                .blockingGet();

        // when
        OpenWeatherMapForecast.HourlyForecast hourlyForecast = forecast.getForecastFor(1);

        // then
        assertNotNull(hourlyForecast);
        assertEquals(11, hourlyForecast.getDate().getHours());
    }
}
