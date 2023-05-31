import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import Database.WebDAO;

import javax.servlet.http.HttpServletRequest;

@WebServlet("/UpdateLinkCount")
public class UpdateLinkCount extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.valueOf(request.getParameter("id"));
        String url = request.getParameter("url");

        WebDAO webDAO = new WebDAO();
        try {
            webDAO.increaseClickCount(id, url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.getWriter().println("Updated !");
    }
}
