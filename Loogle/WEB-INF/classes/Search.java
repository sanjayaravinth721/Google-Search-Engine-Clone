import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

@WebServlet("/Search")
public class Search extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String term = request.getParameter("term");
        String type = request.getParameter("type");
        String page = request.getParameter("page");

        String url = "Search.jsp?type=sites&term=" + term;


        if (type != null) {
           url = "Search.jsp?type=" + type + "&term=" + term;
        } 
        if(page!=null){
            url += "&page="+page;
        }
        else{
            url += "&page=1";
        }
        response.sendRedirect(url);
    }
}