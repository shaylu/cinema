/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    $("#frmLogin").submit(function (e) {
        e.preventDefault();
        var user = $("#txtUsername").val();
        var pass = $("#txtPassword").val();
        var url = "admin/login";
        $.ajax({url: url, data: {'user': user, 'pass': pass}, method: 'POST'}).fail(function (data) {
            alert(data.responseText);
        }).done(function (data) {
            alert(data);
            document.location = "admin/home";
        });
    });
    
    $("#createDefault").click(function() {
        var url = "admin/users/add_default";
        $.ajax({url: url, method: 'POST'}).fail(function (data) {
            alert(data.responseText);
        }).done(function (data) {
            alert(data);
        });
    });
});
