package com.forbitbd.fsecure.api.headModel;

import com.forbitbd.fsecure.model.Account;
import com.forbitbd.fsecure.model.Head;

import java.util.List;

public class HeadGetResponse {
    private int count;
    private List<Head> heads;

    public HeadGetResponse() {
    }

    public int getCount() {
        return count;
    }

    public List<Head> getHeads() {
        return heads;
    }
}
