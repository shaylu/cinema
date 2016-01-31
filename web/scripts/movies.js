/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {
    var mov_arr;
    var sorted_by = "name";

    $(document).on("tap click", ".movie", function (e) {
        $changeToDecription($(this));
        console.log(e.type);
        return false;
    });

    $(document).on("tap click", ".movie-desc", function (e) {
        e.stopPropagation();
        $changeToMovieName($(this));
    });

    $(document).on("tap click", ".movie-go", function (e) {
        e.stopPropagation();
        $goToMoviePage($(this));
    });

    $goToMoviePage = function (obj) {
        var id = $(obj).data("id");
        var url = id;
        document.location = url;
    };

    $(document).on("tap click", ".sort-by", function (e) {
        e.preventDefault();
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
        var cat = json.category.cat_name;
        var poster = json.poster;
        var description = json.plot;
        var id = json.id;
        var res = "<div class=\"movie\" style=\"background-image: url('" + poster + "');\" data-description=\"" + description + "\" data-name=\"" + name + "\" data-id=\"" + id + "\">"
                + "     <h2>"
                + "         " + $getMovieNameHtml(id)
                + "     </h2>"
                + "</div>";

        return res;
    };

    $getMovieById = function (id) {
        var movie = $.grep(mov_arr, function (obj) {
            return obj.id === id;
        })[0];
        return movie;
    };

    $getMovieNameHtml = function (id) {
        var movie = $getMovieById(id);
        $getMovieRank(id);
        return  "<span class=\"movie_name\">" + movie.name + "</span><span class=\"movie_desc\">" + movie.category.cat_name + "</span><span class=\"movie-rank\" data-id=\"" + movie.id + "\"></span>";
    };

    $getMovieRank = function (id) {
        var url = "/cinema_app/app/movies/get-rank/" + id;
        $.ajax({url: url}).fail(function (data) {
            // nothing
        }).done(function (data) {
            $setRankImage(id, data);
        });
    };

    $setRankImage = function (id, rank) {
        $(".movie-rank[data-id=\"" + id + "\"]").html("<img src=\"/cinema_app/images/stars/" + rank + ".png\" />");
    };

    $getGoButton = function (id) {
        return "<div class=\"movie-go\" data-id=\"" + id + "\"><img src=\"/cinema_app/images/go.png\" /><div>";
    };

    $getDescription = function (obj) {
        var id = $(obj).data("id");
        var res = "<div class=\"desc\">" + $(obj).data("description") + "</div>";
        res += $getGoButton(id);
        return res;
    };


    $sort = function () {
        if (sorted_by === "name") {
            mov_arr = mov_arr.sortBy(function (n) {
                var x = n.name;
                return x;
            });
        } else if (sorted_by === "num_of_seats_left") {
            mov_arr = mov_arr.sortBy(function (n) {
                return n.show.num_of_seats_left;
            });
        } else if (sorted_by === "rank") {
            mov_arr = mov_arr.sortBy(function (n) {
                return n.rank;
            }, true);
        } else if (sorted_by === "cat_name") {
            mov_arr = mov_arr.sortBy(function (n) {
                return n.category.cat_name;
            });
        }
    };


    $refresh_movies = function () {
        $sort();
        var res = "";
        $.each(mov_arr, function (idx, item) {
            res += $getMovieHtml(item);
        });
        $("#results").html(res);
        $.each(mov_arr, function (index, obj) {
            var movie_id = $(obj).data("id");
            $getMovieRank(movie_id);
        });
    };

    $changeToMovieName = function (obj) {
        var id = $(obj).data("id");
        $(obj).removeClass("movie-desc");
        $(obj).children("h2").html($getMovieNameHtml(id));
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
