/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.findwise;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Steffen
 */
@Document(collection = "filer")
public class Fil {
    public String filNavn;
    public int filStr;
    public Date created;
    public Date modified;
    
    public Fil(){}
    
    public Fil(String filNavn, int filStr, Date created, Date modified){
        this.filNavn = filNavn;
        this.filStr = filStr;
        this.created = created;
        this.modified = modified;
    }
    
       @Override
    public String toString() {
        return String.format(
                "fil[filNavn='%s', filStr='%s', created='%s', modified='%s']",
                filNavn, filStr, created, modified);
    }
}
