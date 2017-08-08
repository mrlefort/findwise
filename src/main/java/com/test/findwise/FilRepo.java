/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.findwise;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Steffen
 */


public interface FilRepo extends MongoRepository<Fil, String> {
    public Fil findByFilNavn(String filNavn);
}
