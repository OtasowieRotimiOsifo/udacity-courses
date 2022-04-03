package com.example.demo.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrdersRequest {
    @JsonProperty
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
