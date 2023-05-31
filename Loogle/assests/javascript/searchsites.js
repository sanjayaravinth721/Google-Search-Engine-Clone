
$(document).ready(function () {


    $(".result").on("click", function (event) {
        console.log("clicked but not opening");
        var url = $(this).attr("href");
        var id = $(this).attr("data-linkId");
        console.log(url);
        console.log(id);

       
        return  incrementClick(id,url);
    });

    var grid = $(".imageResults");
    grid.masonry({
        itemSelector : ".gridItem",
        columnWidth : 200,
        gutter:5,
        isInitLayout: false
    });

    var urlParams = new URLSearchParams(window.location.search);
    var type = urlParams.get('type');

    // Find the corresponding li element and add the active class
    $(".searchBox").attr("value", urlParams.get('term'));


    $('li').removeClass("active");
    if (type === 'sites') {
        $('.tabList li:eq(0)').addClass('active');
    } else if (type === 'images') {
        $('.tabList li:eq(1)').addClass('active');
    }

    $('.site').click(function (event) {
        event.preventDefault();

        if (!urlParams.has('type') || urlParams.get('type') !== 'sites') {
            window.location.href = 'Search.jsp?term=' + urlParams.get('term') + '&type=sites';
        }

    });

    $('.image').click(function (event) {
        event.preventDefault();
        var urlParams = new URLSearchParams(window.location.search);
        if (!urlParams.has('type') || urlParams.get('type') !== 'images') {
            window.location.href = 'Search.jsp?term=' + urlParams.get('term') + '&type=images';
        }

    });

});

// function loadImage(src,classname){
//     var image = $("<img>");
//     image.on("load",function(){
//         $("."+classname+" a").appen
//     });
// }

function incrementClick(id,url){
    var set = false;
    $.ajax({
        url: "UpdateLinkCount",
        data: {"id":id,"url":url},
        success: function (response) {
            if(response!=""){
                // alert(response);
                set = true;
            }

        }
    });
    return set;
}