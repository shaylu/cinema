/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$("#btnShowOrder").click(function (e) {
    var order_id = $(this).data("id");
    var url = "../orders/get/" + movie_id;
    $.ajax({url: url}).fail(function (data) {
        alert(data.responseText);
    }).done(function (data) {
        $("#orderDetails").text(JSON.stringify(data));
    });
});