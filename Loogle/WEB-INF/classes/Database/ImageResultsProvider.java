package Database;

import java.sql.SQLException;

public class ImageResultsProvider {

    public int getNums(String term) throws SQLException {
        WebDAO webDAO = new WebDAO();
        int count = webDAO.countImageTerms(term);
        return count;
    }

    public String getResultsHtml(int page, int pageSize, String term) throws SQLException {
        WebDAO webDAO = new WebDAO();
        String results = webDAO.getImageHtmlTerms(page, pageSize, term);

        return results;
    }

}
