import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import Database.*;

@WebServlet("/Crawl")
public class Crawl extends HttpServlet {
    List<String> alreadyCrawled = new LinkedList<>();
    List<String> crawling = new LinkedList<>();
    List<String> alreadyFoundImages = new LinkedList<>();

    WebDAO webDAO = new WebDAO();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String startUrl = "http://www.bbc.com";
        followLinks(startUrl, response);
    }

    public void getDetails(String url, HttpServletResponse response) throws IOException {
        DomDocumentParser dom = new DomDocumentParser(url);
        Elements getTitles = dom.getTitles();
        String title = getTitles.get(0).text();
        title = title.replace("\n", "");

        if (title == "") {
            return;
        }
        // System.out.println("title: " + title);

        String description = "";
        String keywords = "";

        Elements metas = dom.getMetaTags();
        for (Element meta : metas) {
            if (meta.attr("name").equalsIgnoreCase("description")) {
                description = meta.attr("content");
            }
            if (meta.attr("name").equalsIgnoreCase("keywords")) {
                keywords = meta.attr("content");
            }
        }
        description = description.replace("\n", "");
        keywords = keywords.replace("\n", "");

        // response.getWriter().println("description : "+description+" , keywords:
        // "+keywords+"<br>");
        try {
            webDAO.insertSite(url, title, description, keywords);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String src = "";
        String alt = "";
        String imgTitle = "";

        for (Element img : dom.getImages()) {
            src = img.attr("abs:src");
            alt = img.attr("alt");
            imgTitle = img.attr("title");

            if (imgTitle.equals("") && alt.equals("")) {
                continue;
            }

            // if(src.equals("")){
            // continue;
            // }

            if (!alreadyFoundImages.contains(src)) {
                alreadyFoundImages.add(src);
                // Insert src
                try {
                    webDAO.insertImage(url, src, alt, imgTitle);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("image: " + src);
            }

        }

        // response.getWriter().println(title+"<br>");

    }

    private void followLinks(String url, HttpServletResponse response) throws IOException {

        crawling.add(url);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        String site = crawling.get(0);
        DomDocumentParser dom = new DomDocumentParser(site);
        Elements links = dom.getLinks();
        for (Element link : links) {
            String href = link.attr("abs:href");
            if (href.contains("#")) {
                continue;
            } else if (href.startsWith("javascript:")) {
                continue;
            }
            if (!alreadyCrawled.contains(href)) {
                alreadyCrawled.add(href);
                crawling.add(href);
                // Insert href

                getDetails(href, response);
            }

        }
        crawling.remove(0);

        for (String sites : crawling) {
            followLinks(sites, response);
        }

        out.println("</body></html>");
        out.close();
    }

}
