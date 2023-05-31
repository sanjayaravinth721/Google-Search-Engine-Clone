<%@ page import = "Database.SiteResultsProvider,Database.ImageResultsProvider,java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Google</title>
    <link rel="stylesheet" href="assests/css/style.css" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script src="https://unpkg.com/masonry-layout@4/dist/masonry.pkgd.min.js"></script>


    <script src="./assests/javascript/searchsites.js"></script>
    

    <style>
        
.mainResultsSection .resultsCount{
    font-size: 13px;
    color: #808080;
    margin-left: 150px;
}
.mainResultsSection .siteResults{
    margin-left: 150px;
}

.resultContainer{
    display: flex;
    flex-direction: column;
    margin-bottom: 26px;
}
.mainResultsSection .title{
    margin:0;
}
.resultContainer .title a{
    color:#1a0dab;
    text-decoration: none;
    font-weight: normal;
    font-size: 18px;
}
.resultContainer .title a:hover{
    text-decoration: dotted;
}
.resultContainer .url{
    color: #006621;
    font-size: 14px;
}
.resultContainer .description{
    font-size: 12px;
}
.paginationContainer{
    display: flex;
    justify-content: center;
    margin-bottom: 25px;
}
.pageButtons{
    display: flex;
}
.pageNumberContainer img{
    height: 37px;
}

.pageNumberContainer,.pageNumberContainer a{
    display: flex;
    flex-direction: column;
    align-items: center;
    text-decoration: none;
}
.pageNumber{
    color:#000;
    font-size: 13px;
}
a .pageNumber{
    color: #4285f4;
}
.mainResultsSection .imageResults{
    margin: 20px;
}
.gridItem {
    position: relative;
}
.gridItem img{
    max-width: 200px;
    min-width: 50px;
}
.gridItem .details{
    visibility: hidden;
    position: absolute;
    bottom: 0px;
    left: 0px;
    width: 100%;
    overflow: hidden;
    background-color: rgba(0, 0, 0, 0.8);
    color: #fff;
    font-size: 11px;
    padding: 3px;
    box-sizing: border-box;
    white-space: nowrap;
}
.gridItem:hover .details{
    visibility: visible;
}

    </style>
    
    <link rel="stylesheet" href="assests/css/style.css" type="text/css">

</head>

<body>
    <div class="wrapper">
        <div class="header">
            <div class="headerContent">
                <div class="logoContainer">
                    <a href="index.html">
                    <img src="./assests/images/loogle.png" alt="">
                </a>
                </div>

                <div class="searchContainer">
                    <form action="Search" method="POST">
                        <div class="searchBarContainer">
                            <input class="searchBox" name="term" type="text">
                            <button class="searchButton">
                                <img src="./assests/images/searchIcon (2).png" alt="Sanjay's Logo site" title="Loogle logo">
                            </button>
                        </div>

                    </form>
                </div>
            </div>
            <div class="tabsContainer">
                <ul class="tabList">
                    <li class="">
                        <a href="Search" class="site">Sites</a>
                    </li>
                    <li class="">
                        <a href="Search" class="image">Images</a>
                    </li>
                </ul>
            </div>
        </div>

        <div class="mainResultsSection">
            <%!
            SiteResultsProvider resultsProvider = new SiteResultsProvider();
            ImageResultsProvider imageResultProvider=new ImageResultsProvider();
            int limit = 20;
             %>
           <% 
            String term = request.getParameter("term");
            int count = resultsProvider.getNums(term);

            if(request.getParameter("type").equals("images")){
                count = imageResultProvider.getNums(term);
                limit = 30;
            }
            int offset = 1;

            String type = request.getParameter("type");
           
            if(request.getParameter("page")!=null){
                offset = Integer.valueOf(request.getParameter("page"));
                
            }
           %>

       
        <p class="resultsCount">Results found <%=count%></p>

        <% if (type.equals("images")) { %>
          <%=imageResultProvider.getResultsHtml(offset, limit, term)%>
        <% } else { %>
           <%=resultsProvider.getResultsHtml(offset, limit, term)%>
        <% } %>
        
        <div class="paginationContainer">
            <div class="pageButtons">
            <div class="pageNumberContainer">
                <img src="assests/images/pageStart.png" alt="">
            </div>

            <%
            
            
            int numPages = (int)Math.ceil(count/limit);
            int pagesToShow = 3;
            int pagesLeft = Math.min(pagesToShow,numPages);
            int currentPage = (int)(offset - Math.floor(pagesToShow/2));

            
            
            if(currentPage<1){
                currentPage=1;
            }
            if(currentPage + pagesLeft > numPages + 1){
                currentPage = numPages - pagesLeft +1;
            }

            for (; pagesLeft != 0 && currentPage<=numPages; pagesLeft--) {
                if (currentPage == offset) {
        %>
                <div class="pageNumberContainer">
                    <img src="assests/images/pageSelected.png" alt="">
                    <span class="pageNumber"><%= currentPage %></span>
                </div>
        <%
                } else {
        %>
                <div class="pageNumberContainer">
                    <a href="Search?term=<%= term %>&type=<%= type %>&page=<%= currentPage %>"><img src="assests/images/page.png" alt="">
                    <span class="pageNumber"><%= currentPage %></span>
                   </a>
               </div>
        <%
                
                }
                currentPage++;
            }
        %>

            <div class="pageNumberContainer">
                <img src="assests/images/pageEnd.png" alt="">
            </div>
        </div>
        </div>
    </div>
</body>

</html>