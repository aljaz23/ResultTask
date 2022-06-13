package com.example.result.resultspringboot.Service;

import com.example.result.resultspringboot.Model.ResultObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.concurrent.*;
@Service
public class ResultService {
    private static final Logger logger = LoggerFactory.getLogger(ResultService.class);
    private final RestTemplate restTemplate;
    private String SiteText;


    public ResultService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }


    //ASYNCHRONOUS METHOD. WE USE JSOUP TO SCRAPE DATA FROM URL. WE CAN GET MULTIPLE THREADS RUNNING.
    //WE RETURN COMPLETABLEFUTURE RESULTOBJECT
    @Async
    public CompletableFuture<ResultObject> getDataFromUrlAsynchronous(String InputUrl, String Xpath) throws IOException, InterruptedException {
        logger.info("Looking up " + InputUrl);
        Document doc = Jsoup.connect(InputUrl).get();
        SiteText = doc.selectXpath(Xpath).text();
        ResultObject resultObject = new ResultObject(SiteText, InputUrl, true);
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(resultObject);

    }
    //WITHOUT @ASYNC WHICH MEANS NotAsynchronous
    public CompletableFuture<ResultObject> getDataFromUrlNotAsynchronous(String InputUrl, String Xpath) throws IOException {
        Document doc = Jsoup.connect(InputUrl).get();
        SiteText = doc.selectXpath(Xpath).text();
        ResultObject resultObject = new ResultObject(SiteText, InputUrl, false);
        return CompletableFuture.completedFuture(resultObject);
    }
}