/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    var touch_pos;
    var mov_arr;
    var sorted_by = "name";

    $(document).on('touchstart', '.movie :not(.movie_desc)', function (e) {
        touch_pos = $(window).scrollTop();
    }).on('click touchend', '.movie', function (e) {
        e.preventDefault();
        if (e.type === 'touchend' && (Math.abs(touch_pos - $(window).scrollTop()) > 3))
            return;
        $changeToDecription($(this));
    });


    $(document).on('touchstart', '.movie-desc not:(.movie_go)', function (e) {
        touch_pos = $(window).scrollTop();
    }).on('click touchend', '.movie-desc', function (e) {
        e.preventDefault();
        if (e.type === 'touchend' && (Math.abs(touch_pos - $(window).scrollTop()) > 3))
            return;
        $changeToMovieName($(this));
    });

    $(document).on('touchstart', '.movie-go', function (e) {
        touch_pos = $(window).scrollTop();
    }).on('click touchend', '.movie-go', function (e) {
        e.preventDefault();
        if (e.type === 'touchend' && (Math.abs(touch_pos - $(window).scrollTop()) > 3))
            return;
        e.stopPropagation();
        var id = $(this).data("id");
        var url = id;
        document.location = url;
    });
    
    $(document).on('touchstart', '.sort-by', function (e) {
        touch_pos = $(window).scrollTop();
    }).on('click touchend', '.sort-by', function (e) {
        e.preventDefault();
        if (e.type === 'touchend' && (Math.abs(touch_pos - $(window).scrollTop()) > 3))
            return;
        e.stopPropagation();
        sorted_by = $(this).data("sort-by");
        $(".sort-by").removeClass("active");
        $(this).addClass("active");
        $refresh_movies();
    });


    $changeToDecription = function (obj) {
        var name = $(this).data("name");
        var desc = $getDescription($(obj));
        $(obj).children("h2").html(desc);
        $(obj).addClass("movie-desc");
    };

    $getMovieHtml = function (json) {
        var name = json.name;
        var poster = json.poster;
        var description = json.plot;
        var id = json.id;
        var res = "<div class=\"movie\" style=\"background-image: url('" + poster + "');\" data-description=\"" + description + "\" data-name=\"" + name + "\" data-id=\"" + id + "\">"
                + "     <h2>" + name + "</h2>"
                + "</div>";

        return res;
    };

    $getGoButton = function (id) {
        return "<div class=\"movie-go\" data-id=\"" + id + "\"><img src=\"/cinema_app/images/go.png\" /><div>";
    };

    $getDescription = function (obj) {
        var id = $(obj).data("id");
        var res = $(obj).data("description");
        res += $getGoButton(id);
        return res;
    };

//    $(document).on("click touchstart", ".movie", function (obj, args) {
//        $changeToDecription($(this));
//    });

    $(document).on("click", ".movie-go", function (e) {

    });
    
    $sort = function() {
        if (sorted_by === "name") {
            mov_arr = mov_arr.sortBy(function(n) {
                var x = n.name;
               return x; 
            });
        }
        else if (sorted_by === "num_of_seats_left") {
            mov_arr = mov_arr.sortBy(function(n) {
               return n.show.num_of_seats_left; 
            });
        }
        else if (sorted_by === "rank") {
             mov_arr = mov_arr.sortBy(function(n) {
               return n.rank; 
            });
        }
        else if (sorted_by === "cat_name") {
             mov_arr = mov_arr.sortBy(function(n) {
               return n.category.cat_name; 
            });
        }
    };
    
    
    $refresh_movies = function() {
        $sort();
        var res = "";
        $.each(mov_arr, function (idx, item) {
            res += $getMovieHtml(item);
        });
        $("#results").html(res);
    };

    $changeToMovieName = function (obj) {
        var name = $(obj).data("name");
        $(obj).removeClass("movie-desc");
        $(obj).children("h2").text(name);
    };

    $(document).on("click", ".movie-desc", function (obj, args) {
        $changeToMovieName($(this));
    });

    $("#search").submit(function (e) {
        e.preventDefault();
        var url = "search2";
        var keyword = $("#keyword").val();
        var has_trailer = $("#hasTrailer").prop('checked');
        var last = $("#lastTickets").prop('checked');
        var is_recommended = $("#isRecommanded").prop('checked');
        var cat_id = $("#selCatID").val();
        $.ajax({url: url, data: {'keyword': keyword, 'has_trailer': has_trailer, 'last': last, 'is_recommended': is_recommended, 'cat_id': cat_id}}).fail(function (data) {
            alert(data.responseText);
        }).done(function (data) {
            mov_arr = data;
            $refresh_movies();
        });
    });

    $("#search").submit();
});
