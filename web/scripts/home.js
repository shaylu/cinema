/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    $("#addOrder").submit(function (e) {
        e.preventDefault();
        var url = "../../orders/add";
        var client_id = $("#addOrder #txtClientId").val();
        var fname = $("#addOrder #txtFname").val();
        var lname = $("#addOrder #txtLname").val();
        var email = $("#addOrder #txtEmail").val();
        var phone = $("#addOrder #txtPhone").val();
        var num_of_seats = $("#addOrder #txtNumOfSeats").val();
        var credit_num = $("#addOrder #txtCreditCardNum").val();
        var month = $("#addOrder #selMonth").val();
        var year = $("#addOrder #selYear").val();
        var show_id = $("#addOrder #txtShowId").val();
        $.ajax({url: url, data: {'client_id': client_id, 'fname': fname, 'lname': lname, 'email': email, 'phone': phone, 'num_of_seats': num_of_seats, 'credit_num': credit_num, 'month': month, 'year': year, 'show_id': show_id}, method: 'POST'})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    alert("Order Successfully Added!");
                    refreshShows();
                });
    });


    $("#searchByKeyword").submit(function (e) {
        e.preventDefault();
        var keyword = $("#txtKeyword").val();
        var url = "/cinema_app/app/movies/search_view?keyword=" + keyword;
        document.location = url;
    });



//$(function () {
//    var cat_id = $("#promos").data("catid");
//    var init = function (id) {
//        var url = "/cinema_app/app/promos/get-by-cat/" + id;
//        var promosHtml = "";
//        $.ajax({url: url}).done(function (data) {
//            $.each(data, function (index, item) {
//                promosHtml += $getPromoHTML(item);
//            });
//            $("#promos").html(promosHtml);
//        });
//    };
//    init(cat_id);
//});

///////////////////////////////////////////////////////////////////////////

    $("#searchByCategory").submit(function (e) {
        e.preventDefault();
        var url = "../app/movies/search";
        var cat_id = $("#selCatID").val();
        $.ajax({url: url, data: {'cat_id': cat_id}})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    $("#byCategory").text(JSON.stringify(data));
                });
    });

    $("#btnGetRecomended").click(function () {
        var url = "../app/movies/home_recommended";
        var cat_id = $("#selCatID").val();
        $.ajax({url: url, data: {'cat_id': cat_id}})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    $("#recomendedMovies").text(JSON.stringify(data));
                });
    });
});


