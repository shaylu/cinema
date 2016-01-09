/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import html.LayoutHelper;

/**
 *
 * @author Dell
 */
public class PostOrderView implements ICinemaView {

    int order_id;

    public PostOrderView(int order_id) {
        this.order_id = order_id;
    }

    @Override
    public String getView() {
        StringBuilder res = new StringBuilder();
        res.append(LayoutHelper.getHeader());
        res.append("<h1>Thank You</h1>\n"
                + "<p><button id=\"btnShowOrder\" data-id=\"" + order_id + "\">Show Order Details</button>"
                + "<div id=\"orderDetails\"></div></p>"
        );

        res.append(html.LayoutHelper.addScripts("//code.jquery.com/jquery-1.11.3.min.js", "//code.jquery.com/jquery-migrate-1.2.1.min.js", "../../scripts/post_order.js"));
        res.append(LayoutHelper.getFooter());
        return res.toString();
    }

}
