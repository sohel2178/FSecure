package com.forbitbd.fsecure.api.headModel;

import com.forbitbd.fsecure.model.Head;

public class HeadPostResponse {

    private String message;
    private Head head;

    public HeadPostResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }
}
