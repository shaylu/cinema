/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    var refreshCompanies = function () {
        var url = "companies/all";
        $.ajax({url: url})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    $("#divAllCompanies").text(JSON.stringify(data));
                });
    };

    $("#btnRefreshCompanies").click(function (e) {
        refreshCompanies();
    });

    $("#addCompany").submit(function (e) {
        e.preventDefault();
        var url = "companies/add";
        var name = $("#addCompany #txtCompanyName").val();
        var address = $("#addCompany #txtAddress").val();
        var about = $("#addCompany #txtAddress").val();

        $.ajax({url: url, data: {'name': name, 'address': address, 'about': about}, method: 'POST'})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    alert("Company Added!");
                    refreshCompanies();
                });
    });

    $("#btnAddDefualtCompanies").click(function () {
        var url = "companies/add_default";
        $.ajax({url: url, method: 'POST'})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    alert("companies added successfully.");
                    refreshCompanies();
                });
    });
});
