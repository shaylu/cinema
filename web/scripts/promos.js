/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {
    $("#btnShowPromos").click(function (e) {
        var url = "promos/all";
        $.ajax({url: url}).fail(function (data) {
            alert(data.responseText);
        }).done(function (data) {
            $("#promos").text(JSON.stringify(data));
        });
    });
});
