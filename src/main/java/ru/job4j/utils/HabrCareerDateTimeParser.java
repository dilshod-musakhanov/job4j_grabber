package ru.job4j.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HabrCareerDateTimeParser implements DateTimeParser {

    @Override
    public LocalDateTime parse(String parse) {
        int year = 0;
        int month = 0;
        int day = 0;
        int hour = 0;
        int minute = 0;
        int second = 0;
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(parse);
        List<String> numbers = new ArrayList<>();
        while (m.find()) {
            numbers.add((m.group()));
        }
        for (int i = 0; i < numbers.size(); i++) {
            switch (i) {
                case 0:
                    year = Integer.parseInt(numbers.get(i));
                    break;
                case 1:
                    month = Integer.parseInt(numbers.get(i));
                    break;
                case 2:
                    day = Integer.parseInt(numbers.get(i));
                    break;
                case 3:
                    hour = Integer.parseInt(numbers.get(i));
                    break;
                case 4:
                    minute = Integer.parseInt(numbers.get(i));
                    break;
                case 5:
                    second = Integer.parseInt(numbers.get(i));
                    break;
                default:
                    break;
            }
        }
        return LocalDateTime.of(year, month, day, hour, minute, second);
    }
}
