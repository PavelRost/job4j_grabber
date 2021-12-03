package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    private final SqlRuDateTimeParser sqlRuDateTimeParser;

    public SqlRuParse(SqlRuDateTimeParser sqlRuDateTimeParser) {
        this.sqlRuDateTimeParser = sqlRuDateTimeParser;
    }

    @Override
    public List<Post> list(String link) {
        List<Post> rsl = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                rsl.add(detail(td.child(0).attr("href")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public Post detail(String link) {
        Document doc = null;
        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String description = doc.select(".msgBody").get(1).text();
        String title = doc.select(".messageHeader").get(0).ownText();
        LocalDateTime created = sqlRuDateTimeParser.parse(doc.select(".msgFooter").get(0).text().split(" \\[")[0]);
        return new Post(title, link, description, created);
    }
}
