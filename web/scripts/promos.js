/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$getRandomPromo = function (callback) {
    url = "/cinema_app/app/promos/rand";
    $.ajax({url: url}).done(function (data) {
        var html = $getPromoHTML(data);
        callback(html);
    });
};

$getPromoHTML = function (promo) {
    return "<div class=\"promo\" data-id=\"" + promo.id + "\">"
            + "<div class=\"promo_description\">"
            + " <div class=\"promo_img\" style=\"background-image: url(/cinema_app/images/promos/" + promo.image + ");\"></div>"
            + " <div class=\"promo_text\">" + promo.description + "</div>"
            + " <div class=\"promo_address\">" + promo.company.name + ", " + promo.company.address + "</div>"
            + "</div>"
            + "<div class=\"promo_code\">USE THIS CODE: " + promo.promoCode + "</div>"
            + "</div>";
};

$(function () {
    $("#btnShowPromos").click(function (e) {
        var url = "promos/all";
        $.ajax({url: url}).fail(function (data) {
            alert(data.responseText);
        }).done(function (data) {
            return data;
        });
    });
});


$(function () {
    $(".promo").click(function (e) {
        var id = $(this).data("id");
    
        var url = "promos/category/" + id;
        $.ajax({url: url}).fail(function (data) {
            alert(data.responseText);
        }).done(function (data) {
            return data;
        })
    });
});