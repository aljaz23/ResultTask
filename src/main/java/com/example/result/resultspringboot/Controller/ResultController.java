package com.example.result.resultspringboot.Controller;


import com.example.result.resultspringboot.Model.ResultObject;
import com.example.result.resultspringboot.Service.ResultService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("v1/Call")
    public ArrayList<ResultObject> getData(@RequestParam(value = "apiCall") int call) throws IOException, ExecutionException, InterruptedException {
        String[] endPoints = {"projekti/", "o-nas/", "kariera/", "blog/"};

        ArrayList<ResultObject> resultObjectsList = new ArrayList<>();
        ArrayList<CompletableFuture<ResultObject>> completableFutures = new ArrayList<>();

        CompletableFuture<ResultObject> futureObject;

        //WE CHECK IF apiCall EQUALS TO 1,2,3 or 4.
        //EXAMPLE IF apiCall is 2 we make 2 Asynchronous calls and 2 notAsynchronous calls
        //WE THEN ADD OBJECT TO THE ARRAYLIST OF COMPLETABLEFUTURES.
        for (int i = 0; i < 4; i++) {
            if (call == 1) {
                futureObject = fillArray(false, endPoints[i]);
                completableFutures.add(futureObject);
            }
            if (call == 2) {
                futureObject = fillArray(true, endPoints[i]);
                completableFutures.add(futureObject);
                if (i > 1) {
                    futureObject = fillArray(false, endPoints[i]);
                    completableFutures.set(i, futureObject);
                }
            }
            if (call == 3) {
                futureObject = fillArray(true, endPoints[i]);
                completableFutures.add(futureObject);
                if (i > 2) {
                    futureObject = fillArray(false, endPoints[i]);
                    completableFutures.set(i, futureObject);
                }
            }
            if (call == 4) {
                futureObject = fillArray(true, endPoints[i]);
                completableFutures.add(futureObject);
            }
        }

        //WE ITERATE TROUGH COMPLETABLEFUTURES ARRAYLIST AND WE CHECK IF CALL WAS DONE SUCCESSFUL AND SET VALUES
        for (CompletableFuture<ResultObject> future : completableFutures) {
            if (future.isDone()) {
                future.get().setSuccesfullcalls(1);
            } else {
                future.get().setNotSuccesfullcalls((1));
            }
        }

        //WE THEN TRANSFORM COMPLETABLEFUTURES INTO ARRAYLIST<ResultObject> AND RETURN IT
        for (CompletableFuture<ResultObject> completableFuture : completableFutures) {
            resultObjectsList.add(completableFuture.get());
        }
        return resultObjectsList;
    }

    //BASED ON INPUT AsynchronousCall (TRUE OR FALSE)
    // WE THEN CALL DIFFERENT METHOD WHICH RETURNS DIFFERENT ResultObject CONSTRUCTOR.
    public CompletableFuture<ResultObject> fillArray(boolean AsynchronousCall, String endpoint) throws IOException, InterruptedException {
        CompletableFuture<ResultObject> site;
        String baseURL = "https://www.result.si/";
        String xpath = "//*[@id=\"finance_kontroling\"]/section/div[1]/div/div/h2";
        if (AsynchronousCall) {
            site = resultService.getDataFromUrlAsynchronous(baseURL + endpoint, xpath);
        } else {
            site = resultService.getDataFromUrlNotAsynchronous(baseURL + endpoint, xpath);

        }
        return site;
    }
}


