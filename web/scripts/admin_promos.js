/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    var refreshPromos = function () {
        var url = "promotions/all";
        $.ajax({url: url})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    $("#divAllPromos").text(JSON.stringify(data));
                });
    };

    $("#btnRefreshPromos").click(function (e) {
        refreshPromos();
    });

    $("#addPromos").submit(function (e) {
        e.preventDefault();
        var url = "promotions/add";
        var cat_id = $("#addPromos #selCatID").val();
        var comp_id = $("#addPromos #selCompID").val();
        var description = $("#addPromos #txtDescription").val();
        var exp_date = $("#addPromos #txtExpDate").val();
        var promo_code = $("#addPromos #txtPromoCode").val();
        var image = $("#addPromos #txtImage").val();
        

        $.ajax({
            url: url,
            data:
                    {
                        'cat_id': cat_id,
                        'comp_id': comp_id,
                        'description': description,
                        'exp_date': exp_date,
                        'promo_code': promo_code,
                        'image': image
                    },
            method: 'POST'})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    refreshPromos();
                });
    });
});
