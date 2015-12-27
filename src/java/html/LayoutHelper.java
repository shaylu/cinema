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

    public static String getHeader() {
        StringBuilder str = new StringBuilder();
        str.append("<!doctype html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"utf-8\">\n"
                + "<title>Untitled Document</title>\n"
                + "<style>\n"
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
        str.append("</body>\n" +
        "</html>");
        return str.toString();
    }
    
    public static String addScripts(String ... scripts) {
        StringBuilder str = new StringBuilder();
        for (String script : scripts) {
           str.append("<script src=\""+ script + "\"></script>");
        }
        return str.toString();
    }
}
