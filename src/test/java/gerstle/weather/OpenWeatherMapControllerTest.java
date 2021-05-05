package gerstle.weather;

import io.reactivex.rxjava3.core.Single;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.scene.image.ImageView;
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
    List<ImageView> iconIm;

    @BeforeClass
    public static void beforeClass() {
        com.sun.javafx.application.PlatformImpl.startup(()->{});
    }
    // Test to make sure that service getCurrentWeather() is called when initialize() is called
    @Test
    public void initialize()
    {
        //given
        OpenWeatherMapService service = mock(OpenWeatherMapService.class);
        OpenWeatherMapController controller = givenWeatherController();
        doReturn("New York").when(controller.location).getText();
        doReturn("imperial").when(controller.degree).getText();
        doReturn(Single.never()).when(service).getCurrentWeather("New York", "imperial");
        //when
        controller.initialize();

        //then
        verify(controller.location).getText();
        verify(controller.degree).getText();
        verify(service).getCurrentWeather("New York", "imperial");
    }
    public OpenWeatherMapController givenWeatherController()
    {
        OpenWeatherMapController controller = new OpenWeatherMapController();
        controller.location = mock(TextField.class);
        controller.degree = mock(TextField.class);
        OpenWeatherMapFeed feed = mock(OpenWeatherMapFeed.class);

        controller.dForecasts  = dForecasts;
        temperature = Arrays.asList(
                mock(Label.class),
                mock(Label.class),
                mock(Label.class)
        );
        return controller;
    }
}