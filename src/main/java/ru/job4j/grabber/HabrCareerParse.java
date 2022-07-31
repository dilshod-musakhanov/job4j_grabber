package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Connection;

import java.io.IOException;

public class HabrCareerParse {

    private static final String SOURCE_LINK = "https://career.habr.com";

    private static  final String PAGE_LINK = String.format("%s/vacancies/java_developer", SOURCE_LINK);

    public static void parseAsPerPage(String url, int pages) {
        String urlPage;
        for (int i = 1; i <= pages; i++) {
            urlPage = String.format("%s?page=%s", PAGE_LINK, i);
            Connection connection = Jsoup.connect(urlPage);
            Document document;
            try {
                document = connection.get();
                System.out.printf("*** Страница № %s ***%n", i);
                Elements rows = document.select(".vacancy-card__inner");
                rows.forEach(row -> {
                    Element dateElement = row.select(".vacancy-card__date").first();
                    Element dateEl = dateElement.child(0);
                    String vacancyDate = dateEl.attr("datetime");
                    Element titleElement = row.select(".vacancy-card__title").first();
                    Element linkElement = titleElement.child(0);
                    String vacancyName = titleElement.text();
                    String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
                    System.out.printf("%s, Опубликовано: %s, %s%n", vacancyName, vacancyDate, link);
                });
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        parseAsPerPage(PAGE_LINK, 5);
    }

}
