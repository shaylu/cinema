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

    public static String getHeader(String ... cssFiles) {
        StringBuilder str = new StringBuilder();
        str.append("<!doctype html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"utf-8\">\n"
                + "<title>++ CINEMA CITY APP ++</title>\n"
                + addBootstrap()
                + addCss(cssFiles)
                + "<style>"
                + "form {\n"
                + "	font-family: arial;\n"
                + "	margin-bottom: 20px;\n"
                + "	border: 1px solid black;\n"
                + "	padding: 20px;\n"
                + "}\n"
                + "form label, input[type=\"submit\"] {\n"
                + "	display: block;\n"
                + "}\n"
                + "form:not(.submitOnly) input[type=\"submit\"] {\n"
                + "	margin-top: 10px;\n"
                + "}\n"
                + "form label:not(:first-child) {\n"
                + "	margin-top: 7px;\n"
                + "}\n"
                + "</style>\n"
                + "</head>\n"
                + "<body>");
        return str.toString();
    }

    public static String getFooter() {
        StringBuilder str = new StringBuilder();
        str.append("</body>\n"
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
    
    public static String addCss(String ... files) {
        StringBuilder str = new StringBuilder();
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
}
