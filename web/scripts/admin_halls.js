/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function () {
    var refreshHalls = function () {
        var url = "halls/all";
        $.ajax({url: url})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    $("#divAllHalls").text(JSON.stringify(data));
                });
    };

    $("#btnRefreshHalls").click(function (e) {
        refreshHalls();
    });

    $("#addHall").submit(function (e) {
        e.preventDefault();
        var url = "halls/add";
        var hall_id = $("#addHall #txtHallId").val();
        var num_of_seats = $("#addHall #txtNumOfSeats").val();
        $.ajax({url: url, data: {'hall_id': hall_id, 'num_of_seats': num_of_seats}, method: 'POST'})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    alert("Hall Added!");
                    refreshHalls();
                });
    });

    $("#btnAddDefualtHalls").click(function () {
        var url = "halls/add_default";
        $.ajax({url: url, method: 'POST'})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    alert("Halls added successfully.");
                    refreshHalls();
                });
    });
});


