package gerstle.weather;

import io.reactivex.rxjava3.core.Single;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Label;

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
        doReturn(Single.never()).when(controller.service).getCurrentWeather("New York","imperial");
        //when
        controller.updateForecast(mock(MouseEvent.class));

        //then
        verify(controller.local).getText();
        verify(controller.degree).getTypeSelector();
        verify(controller.service).getCurrentWeather("New York", "imperial");
    }
    public void onWeatherForecast(OpenWeatherMapForecast forecast)
    {
        OpenWeatherMapController controller = givenWeatherController();
        doReturn("New York").when(controller.local).getText();
        doReturn(true).when(controller.degree).isSelected();
        doReturn(Single.never()).when(controller.service).getCurrentWeather("New York","imperial");
        //when
        controller.onWeatherForecast(forecast);

        //then
        verify(controller.local).getText();
        verify(controller.degree).getTypeSelector();
        verify(controller.service).getCurrentWeather("New York","imperial");
    }

    public void onWeatherForecastRunL(OpenWeatherMapForecast forecast)
    {

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