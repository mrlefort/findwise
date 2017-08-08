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
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private FilRepo repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Information inf = new Information();
        int i = 0;
        Gson gson = new Gson();

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "changeme"));


        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                })
                .build();

		//Gem "filer" til mongoDB.
//		repository.save(new Fil("Steffen-1", 5, new Date(), new Date()));
//		repository.save(new Fil("Steffen-2", 10, new Date(), new Date()));
        

//        System.out.println("Filer fundet med findAll():");
//        System.out.println("-------------------------------");

        
        //Henter documents fra mongoDB og  indekserer dem i Elasticsearch
        for (Fil f : repository.findAll()) {
//            System.out.println("HER ER F: " + f);
            
            String objectInJson = gson.toJson(f);
//            System.out.println("HER ER FFFF " + objectInJson);

            HttpEntity entity = new NStringEntity(objectInJson);

            Response indexResponse = restClient.performRequest(
                    "PUT",
                    "/filer/fil/" + i + "",
                    Collections.<String, String>emptyMap(),
                    entity);
            System.out.println("HERE IS INDEXRESPONSE: " + indexResponse.toString());

            i++;

            //Tager response Status og sætter den ind i Information
            inf.setInfo(indexResponse.getStatusLine().toString());

        }
        restClient.close();

    }
}
