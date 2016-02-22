/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {

    var $getSliderMovieHTML = function (movieJson) {
        return "<div class='movie' data-id='" + movieJson.id + "' style='background-image: url(" + movieJson.poster + "); background-size: cover;'><h2>" + movieJson.name + "</h2></div>";
    };

    var updatePromo = function (html) {
        $("#promoDiv").html(html);
    };
    
    $(document).on("click", ".owl-item div.movie", function(e) {
        var id = $(this).data("id");
        var url = "/cinema_app/app/movies/" + id;
        document.location = url;
    });

    $(document).on("click", ".movie-recomended", function (e) {
        e.preventDefault();
        var movie_id = $(this).data("id");
        var url = "/cinema_app/app/movies/" + movie_id;
        document.location = url;
    });
    

    $("#searchByKeyword").submit(function (e) {
        e.preventDefault();
        var keyword = $("#txtKeyword").val();
        var url = "/cinema_app/app/movies/search_view?keyword=" + keyword;
        document.location = url;
    });


    $("#searchByCategory").submit(function (e) {
        e.preventDefault();
        var cat_id = $("#selCatID").val();
        var url = "/cinema_app/app/movies/search_view?cat_id=" + cat_id;
        document.location = url;
    });
    
    $(document).on("click", ".more-movies", function() {
        document.location = "/cinema_app/app/movies/search_view";
    });

    var getRecommandedMovies = function () {
        var url = "../app/movies/home_recommended";
        var cat_id = $("#selCatID").val();
        $.ajax({url: url, data: {'cat_id': cat_id}})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    mov_arr = data;
                    var res = "";
                    $.each(mov_arr, function (idx, item) {
                        res += $getRecomendedMovieHTML(item);
                    });

                    $("#recomendedMovies").html(res);
                });
    };
    
    var $getRandomMovies = function() {
      var url = "/cinema_app/app/movies/random10";
      var html = "";
      $.ajax({url: url}).done(function(data) {
          $.each(data, function(index, item) {
              html += $getSliderMovieHTML(item);
          });
          
          $("#owl-slider").html(html);
                 $("#owl-slider").owlCarousel();
          
      });
  };


    $getRecomendedMovieHTML = function (movie) {
        return "<div class=\"movie-recomended\" style=\"margin-bottom: 10px; padding-bottom: 10px;\" data-id=\"" + movie.id + "\"><div style=\"min-height: 200px;\">"
                + "     <div class=\"movie_poster\"style=\"background-image: url(" + movie.poster + ");\"></div>"
                + "     <div class=\"movie_body grey_text\">"
                + "         <h3>" + movie.name + "</h3>"
                + "         <p><span>" + movie.cat_name + "</span> <span>(" + movie.release_date + ")</span></p>"
                + "         <div class=\"content-box\">" + movie.plot + "</div>"
                + "     </div>"
                + "</div></div>";
    };


    $(document).on("tap click", ".movie-recomended", function (e) {
        e.stopPropagation();
        $goToMoviePage($(this));
    });

    $goToMoviePage = function (obj) {
        var id = $(obj).data("id");
        var url = "/cinema_app/app/movies/" + id;
        document.location = url;
    };

    $getRandomPromo(updatePromo);
    getRecommandedMovies();
    
    $getRandomMovies();
    
});


