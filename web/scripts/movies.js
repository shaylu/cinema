/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    $("#search").submit(function (e) {
        e.preventDefault();
        var url = "search";
        var keyword = $("#keyword").val();
        var has_trailer = $("#hasTrailer").prop('checked');
        var last = $("#lastTickets").prop('checked');
        var is_recommended = $("#isRecommanded").prop('checked');
        var cat_id = $("#selCatId").val();
        $.ajax({url: url, data: {'keyword': keyword, 'has_trailer': has_trailer, 'last': last, 'is_recommended': is_recommended, 'cat_id': cat_id}}).fail(function (data) {
            alert(data.responseText);
        }).done(function (data) {
            $("#results").text(JSON.stringify(data));
        });
    });

});
