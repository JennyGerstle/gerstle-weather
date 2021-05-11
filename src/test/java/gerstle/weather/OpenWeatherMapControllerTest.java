package gerstle.weather;

import io.reactivex.rxjava3.core.Single;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import javafx.scene.control.Label;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class OpenWeatherMapControllerTest
{
    @FXML
    List<Label> dForecasts;
    @FXML
    List<Label> daysLab;
    @FXML
    List<ImageView> iconIm;
    private MouseEvent MouseEvent;

    @BeforeClass
    public static void beforeClass()
    {
        com.sun.javafx.application.PlatformImpl.startup(() ->
        {
        });
    }

    // Test to make sure that service getCurrentWeather() is called when initialize() is called
    @Test
    public void initialize()
    {
        //given
        OpenWeatherMapController controller = givenWeatherController();
        //when
        controller.initialize();
        //then
        verify(controller.degree).setSelected(true);
    }

    @Test
    public void updateForecast()
    {
        //given
        OpenWeatherMapController controller = givenWeatherController();
        doReturn("New York").when(controller.local).getText();
        doReturn(true).when(controller.degree).isSelected();
        doReturn(Single.never()).when(controller.service).getWeatherForecast("New York","imperial");
        //when
        controller.updateForecast(MouseEvent);

        //then
        verify(controller.degree).isSelected();
        Assert.assertEquals(controller.degreeType, "imperial");
        verify(controller.local).getText();
        verify(controller.service).getWeatherForecast("New York", "imperial");
    }

    @Test
    public void updateForecastCelsius()
    {
        //given
        OpenWeatherMapController controller = givenWeatherController();
        doReturn("New York").when(controller.local).getText();
        doReturn(false).when(controller.degree).isSelected();
        doReturn(Single.never()).when(controller.service).getWeatherForecast("New York","metric");
        //when
        controller.updateForecast(MouseEvent);

        //then
        verify(controller.degree).isSelected();
        Assert.assertEquals(controller.degreeType, "metric");
        verify(controller.local).getText();
        verify(controller.service).getWeatherForecast("New York", "metric");
    }

    @Test
    public void onWeatherForecastRunL()
    {
        Date date = Calendar.getInstance().getTime();
        OpenWeatherMapController controller = givenWeatherController();
        OpenWeatherMapForecast forecast = mock(OpenWeatherMapForecast.class);
        OpenWeatherMapForecast.HourlyForecast hourlyForecast = mock(OpenWeatherMapForecast.HourlyForecast.class);
        OpenWeatherMapForecast.HourlyForecast.Main main = mock(OpenWeatherMapForecast.HourlyForecast.Main.class);
        List<OpenWeatherMapForecast.HourlyForecast.Weather> weather = Arrays.asList(
                mock(OpenWeatherMapForecast.HourlyForecast.Weather.class));


        hourlyForecast.weather = weather;
        hourlyForecast.main = main;
        hourlyForecast.main.temp = 67;

        doReturn(hourlyForecast).when(forecast).getForecastFor(anyInt());
        doReturn(date).when(hourlyForecast).getDate();
        doReturn("http://openweathermap.org/img/wn/10d@2x.png").when(hourlyForecast.weather.get(0)).getIconUrl();

        //when
        controller.onWeatherForecastRunL(forecast);

        //then
            String fDate = date + " ";
            verify(daysLab.get(0)).setText(anyString());
            verify(daysLab.get(0)).setText(fDate.substring(0, fDate.indexOf(" ")));
            verify(dForecasts.get(0)).setText(forecast.getForecastFor(0).main.temp + "");
            verify(iconIm.get(0)).setImage(any());

    }

    public OpenWeatherMapController givenWeatherController()
    {
        OpenWeatherMapService service = mock(OpenWeatherMapService.class);
        OpenWeatherMapController controller = new OpenWeatherMapController(service);
        controller.local = mock(TextField.class);
        controller.degree = mock(RadioButton.class);
        OpenWeatherMapFeed feed = mock(OpenWeatherMapFeed.class);

        controller.daysLab  = daysLab;
        controller.dForecasts  = dForecasts;
        controller.iconIm  = iconIm;
        daysLab = Arrays.asList(
                mock(Label.class),
                mock(Label.class),
                mock(Label.class)
        );
        controller.daysLab = daysLab;
        dForecasts = Arrays.asList(
                mock(Label.class),
                mock(Label.class),
                mock(Label.class)
        );
        controller.dForecasts = dForecasts;
        iconIm = Arrays.asList(
                mock(ImageView.class),
                mock(ImageView.class),
                mock(ImageView.class)
        );
        controller.iconIm = iconIm;

        return controller;
    }
}