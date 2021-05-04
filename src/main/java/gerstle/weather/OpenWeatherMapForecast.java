package gerstle.weather;

import java.util.List;

public class OpenWeatherMapForcast
{

    List<HourlyForcast> list;
    static class HourlyForcast
    {
        long dt;
        Main main;
        List<Weather> weather;

        static class Main
        {
            double temp;
        }

        static class Weather
        {
            String main;
            String icon;
        }
    }


}
