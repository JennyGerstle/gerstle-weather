package gerstle.weather;

import java.util.Date;

public class OpenWeatherMapFeed
{
    Main main;
    String name;
    long dt;
    static class Main
    {
        double temp;
    }
    public Date getTime()
    {
        return new Date(dt * 1000);
    }
}
