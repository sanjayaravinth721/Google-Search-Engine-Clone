package Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SiteResultsProvider {

    public int getNums(String term) throws SQLException {
        WebDAO webDAO = new WebDAO();
        int count = webDAO.countTerms(term);
        return count;
    }

    public String getResultsHtml(int page, int pageSize, String term) throws SQLException {
        WebDAO webDAO = new WebDAO();
        String results = webDAO.getHtmlTerms(page,pageSize,term);

        return results;
    }

}
