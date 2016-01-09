$("#addReview").submit(function (e) {
        e.preventDefault();
        var order_id = $("#addReview").data("id");
        var rank = $("#txtRank").val();
        var text = $("#txtReview").val();
        var url = "../add";
        $.ajax({url: url, data: {'order_id': order_id, 'rank': rank, 'text': text}, method: 'POST'}).fail(function (data) {
            alert(data.responseText);
        }).done(function (data) {
            alert(data);
        });
    });