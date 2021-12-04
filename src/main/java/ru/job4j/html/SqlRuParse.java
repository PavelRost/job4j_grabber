package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public List<Post> list(String link) {
        List<Post> rsl = new ArrayList<>();
        int index = link.lastIndexOf('/');
        String linkNew = link.substring(0, index + 1);
        for (int i = 1; i < 6; i++) {
            try {
                Document doc = Jsoup.connect(linkNew + i).get();
                Elements row = doc.select(".postslisttopic");
                for (Element td : row) {
                    Post temp = detail(td.child(0).attr("href"));
                    if (temp.getTitle().matches(".*\\bJava\\b.*")) {
                        rsl.add(temp);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        LocalDateTime created = dateTimeParser.parse(doc.select(".msgFooter").get(0).text().split(" \\[")[0]);
        return new Post(title, link, description, created);
    }
}
