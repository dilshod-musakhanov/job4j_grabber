package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Connection;
import ru.job4j.utils.DateTimeParser;
import ru.job4j.utils.HabrCareerDateTimeParser;

import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {

    private static final String SOURCE_LINK = "https://career.habr.com";

    private static  final String PAGE_LINK = String.format("%s/vacancies/java_developer", SOURCE_LINK);

    private static final int PAGES_TO_PARSE = 5;

    private final DateTimeParser dateTimeParser;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    public List<Post> parseAsPerPage(Document document) {
        List<Post> list = new ArrayList<>();
        Elements rows = document.select(".vacancy-card__inner");
        rows.forEach(row -> {
            Element dateElement = row.select(".vacancy-card__date").first();
            Element dateEl = dateElement.child(0);
            String vacancyDate = dateEl.attr("datetime");
            Element titleElement = row.select(".vacancy-card__title").first();
            Element linkElement = titleElement.child(0);
            String vacancyName = titleElement.text();
            String link = String.format("%s%s%n", SOURCE_LINK, linkElement.attr("href"));
            list.add(new Post(
                    link,
                    vacancyName,
                    retrieveDescription(link),
                    dateTimeParser.parse(vacancyDate)
            ));
        });
        return list;
    }

    private static String retrieveDescription(String link) {
        String element = null;
        Connection connection = Jsoup.connect(link);
        Document document;
        try {
            document = connection.get();
            element = document.select(".collapsible-description__content").text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return element;
    }

    @Override
    public List<Post> list(String link) {
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i <= PAGES_TO_PARSE; i++) {
            String urlPages = String.format("%s?page=%s", link, i);
            Connection connection = Jsoup.connect(urlPages);
            try {
                Document document = connection.get();
                posts.addAll(parseAsPerPage(document));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return posts;
    }

    public static void main(String[] args) {
        HabrCareerParse hcp = new HabrCareerParse(new HabrCareerDateTimeParser());
        List<Post> postList = hcp.list(PAGE_LINK);
        postList.forEach(System.out::println);
    }
}
