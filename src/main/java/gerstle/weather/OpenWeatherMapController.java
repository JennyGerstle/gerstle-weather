package gerstle.weather;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;

public class OpenWeatherMapController<temp>
{
    @FXML
    TextField local;
    @FXML
    RadioButton degree;
    @FXML
    Button cast;
    @FXML
    List<ImageView> iconIm;
    @FXML
    List<Label> dForecasts;
    @FXML
    List<Label> daysLab;

    String degreeType;

    OpenWeatherMapService service;
    public OpenWeatherMapController(OpenWeatherMapService service)
    {
        this.service = service;
    }

    public void initialize()
    {
        degree.setSelected(true);
    }
    public void updateForecast(MouseEvent event)
    {
        if(degree.isSelected())
        {
            degreeType = "imperial";
        }
        else
        {
            degreeType = "metric";
        }
        String loc = local.getText();
        Disposable disposable = service.getWeatherForecast(loc, degreeType)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.trampoline())
                .subscribe(this::onWeatherForecast, this::onError);
    }
    public void onWeatherForecast(OpenWeatherMapForecast forecast)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                onWeatherForecastRunL(forecast);
            }
        });
    }
    public void onWeatherForecastRunL(OpenWeatherMapForecast forecast)
    {
        for(int days = 0; days < dForecasts.size(); days++)
        {
            daysLab.get(days).setText(forecast.getForecastFor(days).getDate().toString().substring(0, forecast.getForecastFor(days).getDate().toString().indexOf(" ")));
            dForecasts.get(days).setText(forecast.getForecastFor(days).main.temp + "");
            iconIm.get(days).setImage(new Image(forecast.getForecastFor(days).weather.get(0).getIconUrl()));
        }
    }

    public void onError(Throwable throwable)
    {
        // bad but better
        throwable.printStackTrace();
    }

}