package com.example.datasets.services;

import com.example.datasets.entity.Weather;
import com.example.datasets.repository.WeatherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    public ObjectNode convertWeatherObjectToJsonLD(List<Weather> dataSet) {
        ObjectMapper objectMapper = new ObjectMapper();

        // create a JSON-LD context object
        ObjectNode context = objectMapper.createObjectNode();
        context.put("schema", "MySQL/weather");

        // create a JSON-LD data array
        ArrayNode data = objectMapper.createArrayNode();

        // iterate through the Weather objects in the dataSet list and convert them to JSON-LD objects
        for (int i = 0; i < dataSet.size(); i++) {
            Weather weather = dataSet.get(i);

            // create a JSON-LD object for the current Weather object
            ObjectNode weatherObject = objectMapper.createObjectNode();
            weatherObject.put("@id", "urn:uuid:" + UUID.randomUUID().toString()); // generate a random UUID for the ID
            weatherObject.put("id", weather.getId().toString());
            weatherObject.put("dataTime", weather.getDataTime());
            weatherObject.put("irradiance", weather.getIrradiance());
            weatherObject.put("precipitation", weather.getPrecipitation());
            weatherObject.put("temperature", weather.getTemperature());
            weatherObject.put("humidity", weather.getHumidity());
            weatherObject.put("windSpeedAVG", weather.getWindSpeedAVG());
            weatherObject.put("illuminance", weather.getIlluminance());
            weatherObject.put("windSpeedGust", weather.getWindSpeedGust());

            // add the JSON-LD object to the data array
            data.add(weatherObject);
        }

        // create the JSON-LD object with the context and data array
        ObjectNode jsonLdObject = objectMapper.createObjectNode();
        jsonLdObject.put("@context", context);
        jsonLdObject.set("@list", data);
        return jsonLdObject;
    }
    public List<Weather> snippetList(List<Weather> dataSet) {
        List<Weather> newDataSet = new ArrayList<>();
        for(int i=0; i<20; i++){
            newDataSet.add(dataSet.get(i));
        }
        return newDataSet;
    }

    public List<Weather> validateData(List<Weather> dataSet){
        Iterator<Weather> iterator = dataSet.iterator();
        while (iterator.hasNext()) {
            Weather data = iterator.next();
            if (data.shouldDelete()) {
                iterator.remove();
            }
        }
        return dataSet;
    }
}
