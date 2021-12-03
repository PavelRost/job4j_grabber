package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;
import java.io.IOException;
import java.time.LocalDateTime;

public class PostParse {

    public Post getPost(String param) {
        Document doc = null;
        try {
            doc = Jsoup.connect(param).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String description = doc.select(".msgBody").get(1).text();
        String title = doc.select(".messageHeader").get(0).ownText();
        String link = param;
        SqlRuDateTimeParser parserDate = new SqlRuDateTimeParser();
        LocalDateTime created = parserDate.parse(doc.select(".msgFooter").get(0).text().split(" \\[")[0]);
        return new Post(title, link, description, created);
    }
}
