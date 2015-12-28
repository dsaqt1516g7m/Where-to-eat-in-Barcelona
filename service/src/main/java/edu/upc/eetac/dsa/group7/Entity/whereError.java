package edu.upc.eetac.dsa.group7.entity;

/**
 * Created by Alex on 28/12/15.
 */
public class WhereError {
    private int status;
    private String reason;

    public WhereError() {
    }

    public WhereError(int status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
