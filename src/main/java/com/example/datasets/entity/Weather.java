package com.example.datasets.entity;


import com.example.datasets.interfaces.DeleteObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Weather")
@AllArgsConstructor
@NoArgsConstructor
public class Weather implements DeleteObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dataTime;

    private String irradiance;

    private String precipitation;

    private String temperature;

    private String humidity;

    private String windSpeedAVG;

    private String illuminance;

    private String windSpeedGust;

    @Override
    public boolean shouldDelete(){
        int numEmpty = 0;
        int totalArgs = 0;

        if (irradiance == null || irradiance.isEmpty()) {
            numEmpty++;
        }
        totalArgs++;
        if (precipitation == null || precipitation.isEmpty()) {
            numEmpty++;
        }
        totalArgs++;
        if (temperature == null || temperature.isEmpty()) {
            numEmpty++;
        }
        totalArgs++;
        if (humidity == null || humidity.isEmpty()) {
            numEmpty++;
        }
        totalArgs++;
        if (windSpeedAVG == null || windSpeedAVG.isEmpty()) {
            numEmpty++;
        }
        totalArgs++;
        if (illuminance == null || illuminance.isEmpty()) {
            numEmpty++;
        }
        totalArgs++;
        if (windSpeedGust == null || windSpeedGust.isEmpty()) {
            numEmpty++;
        }
        totalArgs++;

        return (numEmpty * 100.0 / totalArgs) > 30.0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getIrradiance() {
        return irradiance;
    }

    public void setIrradiance(String irradiance) {
        this.irradiance = irradiance;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeedAVG() {
        return windSpeedAVG;
    }

    public void setWindSpeedAVG(String windSpeedAVG) {
        this.windSpeedAVG = windSpeedAVG;
    }

    public String getIlluminance() {
        return illuminance;
    }

    public void setIlluminance(String illuminance) {
        this.illuminance = illuminance;
    }

    public String getWindSpeedGust() {
        return windSpeedGust;
    }

    public void setWindSpeedGust(String windSpeedGust) {
        this.windSpeedGust = windSpeedGust;
    }
}
