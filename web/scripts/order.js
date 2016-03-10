/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    $(document).on("change", function(e) {
       var price = $("#totalPrice").data("price");
       var num = $("#numSeats").val();
       $("#totalPrice").text("$" + price * num);
    });
    
    $("#addOrder").submit(function (e) {
        e.preventDefault();
        var url = "/cinema_app/app/orders/add";
        var client_id = $("#addOrder #txtClientId").val();
        var fname = $("#addOrder #txtFname").val();
        var lname = $("#addOrder #txtLname").val();
        var email = $("#addOrder #txtEmail").val();
        var phone = $("#addOrder #txtPhone").val();
        var num_of_seats = $("#addOrder #numSeats").val();
        var credit_num = $("#addOrder #txtCreditCardNum").val();
        var month = $("#addOrder #selMonth").val();
        var year = $("#addOrder #selYear").val();
        var show_id = $("#addOrder #txtShowId").val();
        var seats_left = $("#addOrder #numSeats").attr("max");
        
        if (num_of_seats < 1 || num_of_seats > seats_left)
        {
            alert("Number of seats has to be between 1 to " + seats_left);
            return;
        }
        
        if (fname === '' || lname === '') {
            alert("Name is missing");
            return;
        }
        
        if (client_id === '') {
            alert("Client ID is missing.");
            return;
        }
        
        if (phone === '') {
            alert("Phone is missing.");
            return;
        }
        
        if (credit_num === '' || credit_num.length < 4) {
            alert("Please Enter a valid credit card number.");
            return;
        }
        
        $.ajax({url: url, data: {'client_id': client_id, 'fname': fname, 'lname': lname, 'email': email, 'phone': phone, 'num_of_seats': num_of_seats, 'credit_num': credit_num, 'month': month, 'year': year, 'show_id': show_id}, method: 'POST'})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    var order = {};
                    order.id = data;
                    order.client_id = client_id;
                    order.fname = fname;
                    order.lname = lname;
                    order.email = email;
                    order.phone = phone;
                    order.num_of_seats = num_of_seats;
                    $orderSucess(order);
                });
    });
    
    $loadMovieDetails = function () {
        var movie_id = $(".movie-details").data("id");
        $getMovieDescriptionHTML(movie_id, $setMovieDetails);
    };
    
    $setMovieDetails = function(html) {
      $(".movie-details").html(html); 
    };
    
    $orderSucess = function(order) {
        $("#addOrder").html("<div class=\"thank-you\"><span class=\"green\">Thank you for buying tickets for this show.</span><p>Order Number: " + order.id + "<br>Name: " + order.fname + " " + order.lname + "<br>Num Of Seats: " + order.num_of_seats + "</p><span class=\"light-grey\">To thank you we would like to offer you a special offer and only for a limited time!</span></div><div id=\"promo\"></div>");
        $getRandomPromo(function(html) {
            $("#promo").html(html);
        }); 
    };
    
    $loadMovieDetails();
    
});

