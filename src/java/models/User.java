/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.ResultSet;
import org.w3c.dom.Entity;

/**
 *
 * @author Liraz
 */
public class User {

    public int fldUserId;
    protected String fldPassword;
    public String fldUserName;
    public String fldFname;
    public String fldLname;

    public User(int fldUserId, String fldPassword, String fldUserName, String fldFname, String fldLname) {
        this.fldUserId = fldUserId;
        this.fldPassword = fldPassword;
        this.fldUserName = fldUserName;
        this.fldFname = fldFname;
        this.fldLname = fldLname;
    }

    public int getFldUserId() {
        return fldUserId;
    }

    public String getFldPassword() {
        return fldPassword;
    }

    public String getFldUserName() {
        return fldUserName;
    }

    public String getFldFname() {
        return fldFname;
    }

    public String getFldLname() {
        return fldLname;
    }

    public void setFldUserId(int fldUserId) {
        this.fldUserId = fldUserId;
    }

    public void setFldPassword(String fldPassword) {
        this.fldPassword = fldPassword;
    }

    public void setFldUserName(String fldUserName) {
        this.fldUserName = fldUserName;
    }

    public void setFldFname(String fldFname) {
        this.fldFname = fldFname;
    }

    public void setFldLname(String fldLname) {
        this.fldLname = fldLname;
    }
}
