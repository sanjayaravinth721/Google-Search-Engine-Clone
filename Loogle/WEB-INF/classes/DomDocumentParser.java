import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DomDocumentParser {
    private Document doc;

    public DomDocumentParser(String url) throws IOException {
        String userAgent = "doodleBot/0.1";
        this.doc = Jsoup.connect(url).userAgent(userAgent).get();
    }

    public Document getDoc() {
        return this.doc;
    }
    public Elements getLinks(){
       return this.doc.select("a[href]");
    }
    public Elements getTitles() {
        return this.doc.select("title");
    }
    public Elements getMetaTags(){
        return this.doc.select("meta");
     }

     public Elements getImages(){
        return this.doc.select("img");
     }
}