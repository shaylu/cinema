/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    var refreshMovies = function () {
        var url = "movies/all";
        $.ajax({url: url})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    $("#divAllMovies").text(data);
                });
    };

    $("#btnRefreshAllMovies").click(function (e) {
        refreshMovies();
    });

    $("#addMovie").submit(function (e) {
        e.preventDefault();
        var url = "movies/add";
        var name = $("#addMovie txtMovieName").val();
        var date = $("#addMovie dateReleaseDate").val();
        var movie_length = $("#addMovie numMovieLength").val();
        var cat_id = $("#addMovie selCatID").val();
        var plot = $("#addMovie txtPlot").val();
        var poster = $("#addMovie txtPoster").val();
        var trailer = $("#addMovie txtTrailer").val();
        var is_recommanded = $("#addMovie chkIsRecommanded").val() === "true";

        $.ajax({
            url: url,
            data:
                    {
                        'catName': name
                    },
            method: 'POST'})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    alert("Category Added!");
                    refreshCategories();
                });
    });
});
