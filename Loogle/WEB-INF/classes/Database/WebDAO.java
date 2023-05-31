package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WebDAO {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private Connection getConnection() {
        Connection newConnection = Connect.getInstance().getConnection();
        return newConnection;
    }

    public int countImageTerms(String term) throws SQLException {
        int totalCount = 0;
        try {
            connection = getConnection();
            String countQuery = "SELECT COUNT(*) AS total FROM images WHERE (title LIKE ? OR alt LIKE ?) AND broken=false";

            preparedStatement = connection.prepareStatement(countQuery);
            preparedStatement.setString(1, "%" + term + "%");
            preparedStatement.setString(2, "%" + term + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalCount = resultSet.getInt("total");
                System.out.println("Total count: " + totalCount);

            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return totalCount;

    }

    public int countTerms(String term) throws SQLException {
        int totalCount = 0;
        try {
            connection = getConnection();
            String countQuery = "SELECT COUNT(*) AS total FROM sites WHERE title LIKE ? OR url LIKE ? OR keywords LIKE ? OR description LIKE ?";

            preparedStatement = connection.prepareStatement(countQuery);
            preparedStatement.setString(1, "%" + term + "%");
            preparedStatement.setString(2, "%" + term + "%");
            preparedStatement.setString(3, "%" + term + "%");
            preparedStatement.setString(4, "%" + term + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalCount = resultSet.getInt("total");
                System.out.println("Total count: " + totalCount);

            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return totalCount;

    }

    public void increaseClickCount(int id, String url) throws SQLException {
        try {
            connection = getConnection();
            String updateQuery = "UPDATE sites SET clicks=clicks+1 where id=? AND url=?";

            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, url);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }

    }

    public String getImageHtmlTerms(int page, int pagesize, String term) throws SQLException {
        int totalCount = 0;
        int offset = (page - 1) * pagesize;
        // page 1: (1-1)*20 = 0
        // page 2: (2-1)*20 = 20
        // page 3: (3-1)*20 = 40

        try {
            connection = getConnection();
            String countQuery = "SELECT * FROM images WHERE (title LIKE ? OR alt LIKE ?) AND broken=false ORDER BY clicks DESC LIMIT "
                    + pagesize + " OFFSET " + offset;

            preparedStatement = connection.prepareStatement(countQuery);
            preparedStatement.setString(1, "%" + term + "%");
            preparedStatement.setString(2, "%" + term + "%");
            

            resultSet = preparedStatement.executeQuery();
            String resultsHtml = "<div class='imageResults'>";
            int count=1;
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String imageUrl = resultSet.getString("imageUrl");
                String siteUrl = resultSet.getString("siteUrl");
                String alt = resultSet.getString("alt");
                String title = resultSet.getString("title");

                String displayText = "";
                if (!title.equals("")) {
                    displayText = title;
                } else if (!alt.equals("")) {
                    displayText = alt;
                } else {
                    displayText = imageUrl;
                }

                resultsHtml += "<div class='gridItem image"+count+"'><a class='imageUrl' href=" + "'" + imageUrl
                        + "'>" + "<img src='" + imageUrl + "'><span class='details'>" + displayText + "</span></a>";

                resultsHtml += "<br>";
                resultsHtml += "</div>";
                count++;
            }
           
            return resultsHtml;

        }

        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return "";

    }

    public String getHtmlTerms(int page, int pagesize, String term) throws SQLException {
        int totalCount = 0;
        int offset = (page - 1) * pagesize;
        // page 1: (1-1)*20 = 0
        // page 2: (2-1)*20 = 20
        // page 3: (3-1)*20 = 40

        try {
            connection = getConnection();
            String countQuery = "SELECT * FROM sites WHERE title LIKE ? OR url LIKE ? OR keywords LIKE ? OR description LIKE ? ORDER BY clicks DESC LIMIT "
                    + pagesize + " OFFSET " + offset;

            preparedStatement = connection.prepareStatement(countQuery);
            preparedStatement.setString(1, "%" + term + "%");
            preparedStatement.setString(2, "%" + term + "%");
            preparedStatement.setString(3, "%" + term + "%");
            preparedStatement.setString(4, "%" + term + "%");

            resultSet = preparedStatement.executeQuery();
            String resultsHtml = "<div class='siteResults'>";
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String url = resultSet.getString("url");
                String title = trimField(resultSet.getString("title"), 55);
                String description = trimField(resultSet.getString("description"), 200);

                resultsHtml += "<div class='resultContainer'><h3 class='title'><a class='result' href=" + "'" + url
                        + "' data-linkId='" + id + "'" + ">" + title + "</a></h3>";
                resultsHtml += "<span class='url'>" + url + "</span>";
                resultsHtml += "<span class='description'>" + description + "</span>";
                resultsHtml += "<br>";
            }
            resultsHtml += "</div>";
            return resultsHtml;

        }

        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return "";

    }

    private String trimField(String title, int charLimit) {
        int charLength = title.length();
        String dots = "";
        if (charLength > charLimit) {
            dots = "...";
            return title.substring(0, charLimit) + dots;
        } else {
            return title.substring(0, charLength);
        }

    }

    public void insertImage(String siteUrl, String imageUrl, String alt, String title) throws SQLException {
        String insertQuery = "INSERT INTO images(siteUrl,imageUrl,alt,title) VALUES(?,?,?,?)";

        if (!linkImageExists(imageUrl)) {

            try {
                connection = getConnection();
                preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, siteUrl);
                preparedStatement.setString(2, imageUrl);
                preparedStatement.setString(3, alt);
                preparedStatement.setString(4, title);

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            }
        }
    }

    public void insertSite(String url, String title, String description, String keywords) throws SQLException {
        connection = getConnection();
        String insertQuery = "INSERT INTO sites(url,title,description,keywords) VALUES(?,?,?,?)";

        if (!linkExists(url)) {

            try {
                connection = getConnection();
                preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, url);
                preparedStatement.setString(2, title);
                preparedStatement.setString(3, description);
                preparedStatement.setString(4, keywords);

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            }
        }
    }

    private boolean linkImageExists(String imageUrl) throws SQLException {
        connection = getConnection();
        // System.out.println("from link: "+imageUrl);
        String selectQuery = "SELECT * from images WHERE imageUrl=?";
        preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setString(1, imageUrl);
        resultSet = preparedStatement.executeQuery();
        boolean result = false;

        try {
            if (resultSet.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return result;
    }

    private boolean linkExists(String url) throws SQLException {
        connection = getConnection();
        String selectQuery = "SELECT * from sites WHERE url=?";
        preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setString(1, url);
        resultSet = preparedStatement.executeQuery();
        boolean result = false;

        try {
            if (resultSet.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return result;
    }
}