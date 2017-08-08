/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.findwise;

import com.google.gson.Gson;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.entity.NFileEntity;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;

import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.elasticsearch.client.transport.TransportClient;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private FilRepo repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        int i = 0;
        Gson gson = new Gson();
        
        
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "changeme"));

//           RestClient restClient = RestClient.builder(
//                new HttpHost("localhost", 9200, "http"),
//                new HttpHost("localhost", 9201, "http")).build();
        
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200))
        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        })
        .build();


    


//		repository.deleteAll();
//        List<Fil> listFiler = new ArrayList();
		// save a couple of customers
//		repository.save(new Fil("Steffen-1", 5, new Date(), new Date()));
//		repository.save(new Fil("Steffen-2", 10, new Date(), new Date()));

        // fetch all customers
        System.out.println("Filer funder med findAll():");
        System.out.println("-------------------------------");

        for (Fil f : repository.findAll()) {
            
              System.out.println("HER ER F: " + f);
              String objectInJson = gson.toJson(f);
              System.out.println("HER ER FFFF " + objectInJson);
//
              HttpEntity entity = new NStringEntity(objectInJson);
//            
//            
//                    System.out.println("RESPONSE ER HER: " + response.toString());
//            HttpEntity entity = new NStringEntity(
//                    "{\n"
//                    + "    \"filNavn\" : \" " + f.filNavn + "\",\n"
//                    + "    \"size\" : \"" + f.filStr + "\",\n"
//                    + "    \"created\" : \" " + f.created + "\",\n"
//                    + "    \"modified\" : \" " + f.modified + "\",\n"
//                    + "}", ContentType.APPLICATION_JSON);
            
//            HttpEntity entity = new NStringEntity(
//                    "{\n"
//                    + "    \"user\" : \"Steffen3\",\n"
//                    + "    \"post_date\" : \"2009-11-15T14:12:12\",\n"
//                    + "    \"message\" : \"Prøver virkeligt\"\n"
//                    + "}", ContentType.APPLICATION_JSON);
            
            
            
            Response indexResponse = restClient.performRequest(
                    "PUT",
                    "/twitter/tweet/" + i +"",
                    Collections.<String, String>emptyMap(),
                    entity);
            System.out.println("HERE IS INDEXRESPONSE: " + indexResponse.toString());
            
            i++;
        }
        restClient.close();

    }
}
