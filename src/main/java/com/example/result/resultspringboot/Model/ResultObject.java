package com.example.result.resultspringboot.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ResultObject {

    private String text;
    private String url;
    private int succesfullcalls;
    private int notSuccesfullcalls;

    private boolean AsynchronousCall;

    public ResultObject(String text, String url, boolean AsynchronousCall){
        this.text = text;
        this.url = url;
        this.AsynchronousCall = AsynchronousCall;
    }

    public void setSuccesfullcalls(int succesfullcalls) {
        this.succesfullcalls = succesfullcalls;
    }

    public void setNotSuccesfullcalls(int notSuccesfullcalls) {
        this.notSuccesfullcalls = notSuccesfullcalls;
    }

    public ResultObject(int succesfullcalls, int notSuccesfullcalls){
       this.succesfullcalls = succesfullcalls;
       this.notSuccesfullcalls = notSuccesfullcalls;

    }
    public String getText() {
        return text;
    }

    public int getSuccesfullcalls() {
        return succesfullcalls;
    }

    public String getUrl() {
        return url;
    }

    public int getNotSuccesfullcalls() {
        return notSuccesfullcalls;
    }

    public void setText(String text) {
        this.text = text;
    }
}
