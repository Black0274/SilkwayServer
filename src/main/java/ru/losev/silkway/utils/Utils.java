package ru.losev.silkway.utils;

import com.google.maps.model.LatLng;
import ru.losev.silkway.domain.Point;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Utils {

    private static File file = new File("src/main/resources/res.properties");
    private static Properties properties;

    static {
        try {
            properties = new Properties();
            properties.load(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static List<LatLng> convert(List<Point> route){
        return route.stream()
                .map(point -> new LatLng(point.getLat(), point.getLng()))
                .collect(Collectors.toList());
    }
}
