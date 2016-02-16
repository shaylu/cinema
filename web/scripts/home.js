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


    $("#searchByCategory").submit(function (e) {
        e.preventDefault();
        var cat_id = $("#selCatID").val();
        var url = "/cinema_app/app/movies/search_view?cat_id=" + cat_id;
        document.location = url;
    });

    $("#btnGetRecomended").click(function () {
        var url = "../app/movies/home_recommended";
        var cat_id = $("#selCatID").val();
        $.ajax({url: url, data: {'cat_id': cat_id}})
                .fail(function (data) {
                    alert(data.responseText);
                })
                .done(function (data) {
                    mov_arr = data;
                    var res = "";
                    $.each(mov_arr, function (idx, item) {
                        res += $getRecomendedMovieHTML(item);
                    });

                    $("#recomendedMovies").html(res);
                });
    });


    $getRecomendedMovieHTML = function (movie) {
        return "<div class=\"movie-recomended\" style=\"margin-bottom: 10px; padding-bottom: 10px;\" data-id=\"" + movie.id + "\"><div style=\"min-height: 200px;\">"
                + "     <div class=\"movie_poster\"style=\"background-image: url(" + movie.poster + ");\"></div>"
                + "     <div class=\"movie_body grey_text\">"
                + "         <h3>" + movie.name + "</h3>"
                + "         <p><span>" + movie.cat_name + "</span> <span>(" + movie.release_date + ")</span></p>"
                + "         <div class=\"content-box\">" + movie.plot + "</div>"
                + "     </div>"
                + "</div></div>";
    };


    $(document).on("tap click", ".movie-recomended", function (e) {
        e.stopPropagation();
        $goToMoviePage($(this));
    });
    
    $goToMoviePage = function (obj) {
        var id = $(obj).data("id");
        var url = "/cinema_app/app/movies/"+ id;
        document.location = url;
    };
});


