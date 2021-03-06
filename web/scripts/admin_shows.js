/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function () {
    var refreshShows = function () {
        var url = "shows/all";
        $.ajax({url: url})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    $("#divAllShows").text(JSON.stringify(data));
                });
    };

    $("#btnRefreshShows").click(function (e) {
        refreshShows();
    });

    $("#addShow").submit(function (e) {
        e.preventDefault();
        var url = "shows/add";
        var movie_id = $("#addShow #selMovieId").val();
        var hall_id = $("#addShow #selHallId").val();
        var price = $("#addShow #numPricePerSeat").val();
        var date = $("#addShow #txtDate").val();
        var time = $("#addShow #txtTime").val();
        $.ajax({url: url, data: {'movie_id': movie_id, 'hall_id': hall_id, 'price': price, 'date': date, 'time': time}, method: 'POST'})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    alert("Show Added!");
                    refreshShows();
                });
    });

    $("#btnAddDefualtShows").click(function () {
        var url = "shows/add_default";
        $.ajax({url: url, method: 'POST'})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    alert("Shows added successfully.");
                    refreshShows();
                });
    });
});


