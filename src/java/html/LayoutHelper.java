/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package html;

/**
 *
 * @author Dell
 */
public class LayoutHelper {

    public static String getHeader(String... cssFiles) {
        StringBuilder str = new StringBuilder();
        str.append("<!doctype html>\n"
                + "<html>\n"
                + "<head>\n"
                + " <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\" />"
                + "<meta charset=\"utf-8\">\n"
                + "<title>++ CINEMA CITY APP ++</title>\n"
                + addJquery()
                + addBootstrap()
                + addCss(cssFiles)
                + "</head>\n"
                + "<body>"
                + addTitleBar()
                + "<div class=\"container\">"
        );
        return str.toString();
    }

    public static String getFooter() {
        StringBuilder str = new StringBuilder();
        str.append("</div></body>\n"
                + "</html>");
        return str.toString();
    }

    public static String addScripts(String... scripts) {
        StringBuilder str = new StringBuilder();
        for (String script : scripts) {
            str.append("<script src=\"" + script + "\"></script>");
        }
        return str.toString();
    }

    public static String addCss(String... files) {
        StringBuilder str = new StringBuilder();
        str.append("<link rel=\"stylesheet\" href=\"/cinema_app/css/default.css\" />");
        for (String file : files) {
            str.append("<link rel=\"stylesheet\" href=\"" + file + "\" />");
        }

        return str.toString();
    }

    public static String getAdminMenu() {
        StringBuilder str = new StringBuilder();
        str.append("<div><a href=\"categories\">Movie Categories</a></div>");
        str.append("<div><a href=\"movies\">Movies</a></div>");
        str.append("<div><a href=\"halls\">Halls</a></div>");
        str.append("<div><a href=\"shows\">Shows</a></div>");
        str.append("<div><a href=\"promotions/categories\">Promotions Categories</a></div>");
        str.append("<div><a href=\"companies\">Promotions Companies</a></div>");
        str.append("<div><a href=\"promotions\">Promotions</a></div>");
        return str.toString();
    }

    private static String addBootstrap() {
        return "<!-- Latest compiled and minified CSS -->\n"
                + "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" integrity=\"sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7\" crossorigin=\"anonymous\">\n"
                + "\n"
                + "<!-- Optional theme -->\n"
                + "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css\" integrity=\"sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r\" crossorigin=\"anonymous\">\n"
                + "\n"
                + "<!-- Latest compiled and minified JavaScript -->\n"
                + "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\" integrity=\"sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS\" crossorigin=\"anonymous\"></script>";
    }

    private static String addJquery() {
        return "<script src=\"http://code.jquery.com/jquery-1.12.0.min.js\"></script>\n"
                + "    <script src=\"http://code.jquery.com/jquery-migrate-1.2.1.min.js\"></script>";
    }

    private static String addTitleBar() {
        return "<div class=\"navbar navbar-inverse navbar-static-top\">\n"
                + "  <div class=\"container\">\n"
                + "    <div class=\"navbar-header\">\n"
                + "      <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-collapse\">\n"
                + "        <span class=\"icon-bar\"></span>\n"
                + "        <span class=\"icon-bar\"></span>\n"
                + "        <span class=\"icon-bar\"></span>\n"
                + "      </button>\n"
                + "      <a class=\"navbar-brand\" href=\"/cinema_app/app/home\"><img src=\"/cinema_app/images/logo.png\" alt=\"\" style=\"max-width:100px; max-height: 34px; margin-top: -7px;\"/></a>\n"
                + "    </div>\n"
                + "    <div class=\"collapse navbar-collapse\">\n"
                + "      <ul class=\"nav navbar-nav\">\n"
                + "        <li class=\"active\"><a href=\"/cinema_app/app/home\">Home</a></li>\n"
                + "        <li><a href=\"/cinema_app/app/movies/search_view\">Movies</a></li>\n"
                + "        <li><a href=\"/cinema_app/app/promos\">Promotions</a></li>\n"
                + "      </ul>\n"
                + "    </div><!--/.nav-collapse -->\n"
                + "  </div>\n"
                + "</div>";
    }
}
