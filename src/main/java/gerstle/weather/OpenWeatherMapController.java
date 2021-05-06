package gerstle.weather;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;

public class OpenWeatherMapController<temp>
{
    @FXML
    TextField location;
    @FXML
    RadioButton degree;
    @FXML
    Button cast;
    @FXML
    List<ImageView> iconIm;
    @FXML
    List<Label> dForecasts;

    private OpenWeatherMapService service;
    public OpenWeatherMapController(OpenWeatherMapService service)
    {
        this.service = service;
    }

    public void initialize()
    {
        OpenWeatherMapServiceFactory factory = new OpenWeatherMapServiceFactory();
        OpenWeatherMapService service = factory.newInstance();
    }
    public void updateForecast(ActionEvent actionEvent)
    {
        Disposable disposable = service.getWeatherForecast(location.getText(), degree.getText())
                // request the data in the background
                .subscribeOn(Schedulers.io())
                // work with the data in the foreground
                .observeOn(Schedulers.trampoline())
                // work with the feed whenever it gets downloaded
                .subscribe(this::onWeatherForecast, this::onError);
    }
    public void onWeatherForecast(OpenWeatherMapForecast forecast)
    {
        Platform.runLater(() -> onWeatherForecastRunL(forecast));
    }
    public void onWeatherForecastRunL(OpenWeatherMapForecast forecast)
    {
        //dForecasts, iconIm;
        for(int days = 0; days < dForecasts.size(); ++days)
        {
            dForecasts.get(days).setText(forecast.getForecastFor(days) + "");
            //iconIm.get(days).setImage(OpenWeatherMapForecast.HourlyForecast.Weather.getIconURL(days));
        }
    }

    public void onError(Throwable throwable)
    {
        // bad but better
        throwable.printStackTrace();
    }
}