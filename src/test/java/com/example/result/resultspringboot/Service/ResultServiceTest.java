package com.example.result.resultspringboot.Service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class ResultServiceTest {

    @Test
    void getTextFromUrl() throws IOException, InterruptedException, ExecutionException {
        String Xpath = "//*[@id=\"finance_kontroling\"]/section/div[1]/div/div/h2";
        ResultService resultService = new ResultService(new RestTemplateBuilder());
        assertEquals("Izzivi in rešitve", resultService.getDataFromUrlAsynchronous("https://www.result.si/projekti/",Xpath).get().getText());
        assertEquals("Partner za vašo rast", resultService.getDataFromUrlAsynchronous("https://www.result.si/o-nas/",Xpath).get().getText());
        assertEquals("Pridruži se revoluciji", resultService.getDataFromUrlAsynchronous("https://www.result.si/kariera/",Xpath).get().getText());
        assertEquals("Blog & novice", resultService.getDataFromUrlAsynchronous("https://www.result.si/blog/",Xpath).get().getText());

    }
}