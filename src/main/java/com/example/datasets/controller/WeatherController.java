package com.example.datasets.controller;

import com.example.datasets.entity.Weather;
import com.example.datasets.repository.WeatherRepository;
import com.example.datasets.services.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class WeatherController {

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/get_data")
    public ResponseEntity<Object> getDataSet(@RequestParam("data_type") String dataType, @RequestParam("snippets") Boolean snippet) throws IOException {
        ObjectNode jsonLdObject = new ObjectNode(new JsonNodeFactory(false));
        switch (dataType){
            case "weather":
                List<Weather> dataSet = new ArrayList<>();
                dataSet=weatherRepository.findAll();
                dataSet=weatherService.validateData(dataSet);
                if(!snippet){
                    jsonLdObject = weatherService.convertWeatherObjectToJsonLD(dataSet);
                    return new ResponseEntity<>(jsonLdObject, HttpStatus.OK);
                }
                dataSet = weatherService.snippetList(dataSet);
                jsonLdObject = weatherService.convertWeatherObjectToJsonLD(dataSet);
                return new ResponseEntity<>(jsonLdObject, HttpStatus.OK);
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

    }
    @GetMapping("/put_data")
    public ResponseEntity<List<Weather>> putDataSet(){
        List<Weather> dataSet = new ArrayList<>();
        for(int i =0; i<6555;i++){
            dataSet.add(new Weather());
        }
        Weather weather =new Weather();
        String file = "C:\\Users\\drago\\Desktop\\output7.txt";


        String line = "";
        String delimiter = ",";


        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Read the first line (header) and discard it
            br.readLine();
            int count=0;
            // Read the rest of the lines and parse the fields
            while ((line = br.readLine()) != null) {
                Scanner scanner = new Scanner(line);
                scanner.useDelimiter(delimiter);
                String field1 = scanner.next();
                String field2 = scanner.next();
                String field3 = scanner.next();
                String field4 = scanner.next();
                String field5 = scanner.next();
                String field6 = scanner.next();
                String field7 = scanner.next();
                String field8 = scanner.next();
                dataSet.get(count).setDataTime(field1);
                dataSet.get(count).setHumidity(field2);
                dataSet.get(count).setIlluminance(field3);
                dataSet.get(count).setIrradiance(field4);
                dataSet.get(count).setPrecipitation(field5);
                dataSet.get(count).setTemperature(field6);
                dataSet.get(count).setWindSpeedAVG(field7);
                dataSet.get(count).setWindSpeedGust(field8);
                count++;
                // Parse additional fields as needed

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        weatherRepository.saveAll(dataSet);

        return new ResponseEntity<>(dataSet, HttpStatus.OK);

    }


}
