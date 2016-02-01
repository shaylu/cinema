/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {
    var id = $(".rank").data("id");

    $("#btnMovieDetails").click(function (e) {
        var movie_id = $(this).data("id");
        var url = "../movies/get/" + movie_id;
        $.ajax({url: url}).fail(function (data) {
            alert(data.responseText);
        }).done(function (data) {
            $("#movieDetails").text(JSON.stringify(data));
        });
    });

    $("#btnMovieShows").click(function (e) {
        var movie_id = $(this).data("id");
        var url = "../shows/bymovie/" + movie_id;
        $.ajax({url: url}).fail(function (data) {
            alert(data.responseText);
        }).done(function (data) {
            $("#movieShows").text(JSON.stringify(data));
        });
    });

    $("#btnMovieReviews").click(function (e) {
        var movie_id = $(this).data("id");
        var url = "../reviews/bymovie/" + movie_id;
        $.ajax({url: url}).fail(function (data) {
            alert(data.responseText);
        }).done(function (data) {
            $("#movieReviews").text(JSON.stringify(data));
        });
    });

    $getMovieRank = function (id) {
        var url = "/cinema_app/app/movies/get-rank/" + id;
        $.ajax({url: url}).fail(function (data) {
            // nothing
        }).done(function (data) {
            $setRankImage(data);
        });
    };

    $setRankImage = function (rank) {
        $(".rank").html("<img src=\"/cinema_app/images/stars/" + rank + ".png\" />");
    };

    $getMovieRank(id);

    $getShowHTML = function (show) {
        return "<div class=\"show\" data-id=\"" + show.id + "\">"
                + "<span class=\"show_date\">" + show.date + "</span>"
                + "<span class=\"show_time\">" + show.time + "</span>"
                + "<span class=\"seats_left\">" + show.num_of_seats_left + "</span>"
                + "<span class=\"buy_seats\"><button class=\"buy-tickets\" data-id=\"" + show.id + "\">Buy</button></span>"
                + "</div>";
    };

    $getReviewHTML = function (review) {
        return "<div class=\"review\" data-id=\"" + review.rev_id + "\">"
                + "<div class=\"review_date\">" + review.review_date + "</div>"
                + "<div class=\"rank\"><img src=\"/cinema_app/images/stars/" + review.rank + ".png\" /></div>"
                + "<div class=\"review_body\">" + review.review_text + "</div>"
                + "</div>";
    };

    $refreshReviews = function () {
        var movie_id = id;
        var url = "../reviews/bymovie/" + movie_id;
        $.ajax({url: url}).fail(function (data) {
            // nothing
        }).done(function (data) {
            var arr = data;
            var html = "";
            if ($(data).length == 0)
                html = "No reviews yet.";
            else
            {
                $.each(data, function (index, obj) {
                    html += $getReviewHTML(obj);
                });
            }
            $("#reviews").html(html);
        });
    };
    
    $(document).on("click", ".buy-tickets", function() {
        var show_id = $(this).parents(".show").data("id");
        var seats_left = $(this).parents(".show").children(".seats_left").text();
        if (seats_left !== '' && seats_left > 0)
        {
            document.location = "/cinema_app/app/shows/" + show_id + "/order";
        }
        else {
            alert("No tickets left for this show.");
        }
    });

    $refreshShows = function () {
        var movie_id = id;
        var url = "../shows/bymovie/" + movie_id;
        $.ajax({url: url}).fail(function (data) {
            // nothing
        }).done(function (data) {
            var arr = data;
            var html = "";
            if ($(data).length == 0)
                html = "No shows exist.";
            else
            {
                html += "<div class=\"show header\">"
                        + "<span class=\"show_date\">Date</span>"
                        + "<span class=\"show_time\">Time</span>"
                        + "<span class=\"seats_left\">Seats Left</span>"
                        + "<span class=\"buy_seats\"></span>"
                        + "</div>";
                $.each(data, function (index, obj) {
                    html += $getShowHTML(obj);
                });
            }
            $("#shows").html(html);
        });
    };

    $refreshShows();
    $refreshReviews();
});
