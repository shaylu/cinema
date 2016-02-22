/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {
    var cat_id = $("#promos").data("catid");
    var init = function (id) {
        var url = "/cinema_app/app/promos/get-by-cat/" + id;
        var promosHtml = "";
        $.ajax({url: url}).done(function (data) {
            $.each(data, function (index, item) {
                promosHtml += $getPromoHTML(item);
            });
            $("#promos").html(promosHtml);
        });
    };
    init(cat_id);
});