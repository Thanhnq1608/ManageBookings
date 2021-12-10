package com.fpoly.managebookings.models.firebase;

import java.util.List;

public class ResponseSendMessage {

    private double multicastID;
    private int success;
    private int failure;
    private int canonicalIDS;
    private Result[] results;

    public double getMulticastID() {
        return multicastID;
    }

    public long getSuccess() {
        return success;
    }

    public long getFailure() {
        return failure;
    }

    public long getCanonicalIDS() {
        return canonicalIDS;
    }

    public Result[] getResults() {
        return results;
    }
}
