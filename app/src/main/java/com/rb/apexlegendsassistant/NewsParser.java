package com.rb.apexlegendsassistant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NewsParser {

    private String newsLink;
    public static final String DOMAIN = "https://www.ea.com";

    public NewsParser(String newsLink) {
        this.newsLink = newsLink;
    }

    public List<News> parse() throws IOException {
        List<News> newsLinkArray = parseNewsLinks();
        return parsNewsContent(newsLinkArray);
    }

    public List<News> parseNewsLinks() throws IOException {
        Document document;
        List<News> newsArray = new ArrayList<News>();
        byte[] bytes;
        String textToDecode;
        String textUncoded;

        document = Jsoup.connect(newsLink)
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("https://www.google.com")
                .get();

        Elements elementsLink = document.select("ea-cta");

        for (Element element : elementsLink) {
            News news = new News();

            textToDecode = DOMAIN + element.attr("link-url");
            bytes = textToDecode.getBytes(StandardCharsets.UTF_8);
            textUncoded = new String(bytes);
            news.setLink(textUncoded);
            newsArray.add(news);
        }

        List<News> newsArrayFiltered = newsArray.stream().filter(e -> {
            return e.getLink().contains("/news/");
        }).limit(10).collect(Collectors.toList());

        return newsArrayFiltered;
    }

    public List<News> parsNewsContent(List<News> newsArray) throws IOException {
        // List<News> newsContentArray = new ArrayList<News>();
        String textToDecode;
        String textUncoded;
        Element element;
        byte[] bytes;

        for (News news : newsArray) {
            Document document;
            document = Jsoup.connect(news.getLink())
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("https://www.google.com")
                    .get();

            // Title
            element = document.select("meta[property=og:title]").first();
            textToDecode = element.attr("content");
            bytes = textToDecode.getBytes(StandardCharsets.UTF_8);
            textUncoded = new String(bytes);
            news.setTitle(textUncoded);

            // Description
            element = document.select("meta[property=og:description]").first();
            textToDecode = element.attr("content");
            bytes = textToDecode.getBytes(StandardCharsets.UTF_8);
            textUncoded = new String(bytes);
            news.setPreviewText(textUncoded);

            // Image
            element = document.select("meta[property=og:image]").first();
            textToDecode = element.attr("content");
            bytes = textToDecode.getBytes(StandardCharsets.UTF_8);
            textUncoded = new String(bytes);
            news.setImage(textUncoded);

            // Date
            element = document.select("meta[property=article:published_time]").first();
            textToDecode = element.attr("content");
            bytes = textToDecode.getBytes(StandardCharsets.UTF_8);
            textUncoded = new String(bytes);
            news.setDate(textUncoded);


            // Content
            Elements elements = document.select("ea-section[slot=section]");
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < elements.size() - 1; i++) {
                bytes = elements.get(i).toString().getBytes(StandardCharsets.UTF_8);
                textUncoded = new String(bytes);
                stringBuilder.append(textUncoded);
            }

            news.setText(stringBuilder.toString());
        }

        return newsArray;
    }

}
