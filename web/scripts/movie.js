/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {
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
});
