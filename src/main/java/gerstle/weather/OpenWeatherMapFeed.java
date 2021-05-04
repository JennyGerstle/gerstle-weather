package gerstle.weather;

import java.util.Date;

public class OpenWeatherMapFeed
{
    Main main;
    String name;
    long dt;
    public static class Main
    {
        double temp;

        public double getTemp()
        {
            return temp;
        }
    }
    public Date getDate()
    {
        return new Date(dt * 100);
    }
}
