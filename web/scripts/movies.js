/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    var touch_pos;
    
    $(document).on('touchstart', '.movie', function (e) {
        touch_pos = $(window).scrollTop();
    }).on('click touchend', '.movie', function (e) {
        e.preventDefault();
        if (e.type === 'touchend' && (Math.abs(touch_pos - $(window).scrollTop()) > 3))
            return;
        $changeToDecription($(this));
    });
    
    
    $(document).on('touchstart', '.movie-desc', function (e) {
        touch_pos = $(window).scrollTop();
    }).on('click touchend', '.movie-desc', function (e) {
        e.preventDefault();
        if (e.type === 'touchend' && (Math.abs(touch_pos - $(window).scrollTop()) > 3))
            return;
        $changeToMovieName($(this));
    });
    
    
    $changeToDecription = function(obj) {
        var name = $(this).data("name");
        var desc = $getDescription($(obj));
        $(obj).children("h2").html(desc);
        $(obj).addClass("movie-desc");
    };

    $getMovieHtml = function (json) {
        var name = json.name;
        var poster = json.poster;
        var description = json.plot;
        var id = json.id;
        var res = "<div class=\"movie\" style=\"\" data-description=\"" + description + "\" data-name=\"" + name + "\" data-id=\"" + id + "\">"
                + "     <h2>" + name + "</h2>"
                + "</div>";

        return res;
    };

    $getGoButton = function (id) {
        return "<div class=\"movie-go\" data-id=\"" + id + "\"><img src=\"/cinema_app/images/go.png\" /><div>";
    };

    $getDescription = function (obj) {
        var id = $(obj).data("id");
        var res = $(obj).data("description");
        res += $getGoButton(id);
        return res;
    };

//    $(document).on("click touchstart", ".movie", function (obj, args) {
//        $changeToDecription($(this));
//    });

    $(document).on("click", ".movie-go", function (e) {
        e.stopPropagation();
        var id = $(this).data("id");
        var url = id;
        document.location = url;
    });
    
    $changeToMovieName = function(obj) {
        var name = $(obj).data("name");
        $(obj).removeClass("movie-desc");
        $(obj).children("h2").text(name);
    };

    $(document).on("click", ".movie-desc", function (obj, args) {
        $changeToMovieName($(this));
    });

    $("#search").submit(function (e) {
        e.preventDefault();
        var url = "search";
        var keyword = $("#keyword").val();
        var has_trailer = $("#hasTrailer").prop('checked');
        var last = $("#lastTickets").prop('checked');
        var is_recommended = $("#isRecommanded").prop('checked');
        var cat_id = $("#selCatID").val();
        $.ajax({url: url, data: {'keyword': keyword, 'has_trailer': has_trailer, 'last': last, 'is_recommended': is_recommended, 'cat_id': cat_id}}).fail(function (data) {
            alert(data.responseText);
        }).done(function (data) {
            var res = "";
            $.each(data, function (idx, item) {
                res += $getMovieHtml(item);
            });
            $("#results").html(res);
        });
    });

});
