/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    $("#frmLogin").submit(function (e) {
        e.preventDefault();
        var user =  $("#txtUsername").val();
        var pass = $("#txtPassword").val();
        var url = "?user=" + user + "&pass=" + pass; 
        $.ajax({url : url}).done(function(data) {
            alert(data);
        });
    });
});
