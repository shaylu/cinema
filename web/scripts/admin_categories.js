/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    var refreshCategories = function () {
        var url = "categories/all";
        $.ajax({url: url})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    $("#divAllCategories").text(JSON.stringify(data));
                });
    };

    $("#btnRefreshCategories").click(function (e) {
        refreshCategories();
    });

    $("#addCategory").submit(function (e) {
        e.preventDefault();
        var url = "categories/add";
        var name = $("#addCategory #txtCatName").val();
        $.ajax({url: url, data: {'name': name}, method: 'POST'})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    alert("Category Added!");
                    refreshCategories();
                });
    });

    $("#btnAddDefualtCategories").click(function () {
        var url = "categories/add_default";
        $.ajax({url: url, method: 'POST'})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    alert("categories added successfully.");
                    refreshCategories();
                });
    });
});
