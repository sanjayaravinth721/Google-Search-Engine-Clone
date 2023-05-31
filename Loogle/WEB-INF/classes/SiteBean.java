public class SiteBean {
    private String url;
    private String title;
    private String description;
    private String keyword;
    private int clicks;

    
    // public SiteBean(String url, String title, String description, String keyword, int clicks) {
    //     this.url = url;
    //     this.title = title;
    //     this.description = description;
    //     this.keyword = keyword;
    //     this.clicks = clicks;

    // }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public int getClicks() {
        return clicks;
    }
    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    
}
