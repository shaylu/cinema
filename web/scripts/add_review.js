$("#addReview").submit(function (e) {
    e.preventDefault();
    var order_id = $("#addReview").data("id");
    var last_digits = $("#addReview").data("last-digits");
    var rank = $("#selRank").val();
    var text = $("#txtReview").val();
    var url = "/cinema_app/app/reviews/add";
    $.ajax({url: url, data: {'last_digits': last_digits, 'order_id': order_id, 'rank': rank, 'text': text}, method: 'POST'}).fail(function (data) {
        alert(data.responseText);
    }).done(function (data) {
        $getRandomPromo($("#addReview .content-box"));
    });
});

$(document).ready(function () {
    $("#validateOrderCode").submit(function (e) {
        e.preventDefault();
        var form = $("#validateOrderCode");
        var url = "/cinema_app/app/orders/validate-order-code";
        var order_code = $("#txtOrderCode").val();
        var last_digits = $("#txtLastDigits").val();
        $.ajax({url: url, data: {order_id: order_code, last_digits: last_digits}, method: 'POST'}).fail(function () {
            alert("Invalid order code or last credit card digits.")
        }).done(function () {
            document.location = "/cinema_app/app/reviews/add-review/" + order_code + "/" + last_digits;
        });
    });
});