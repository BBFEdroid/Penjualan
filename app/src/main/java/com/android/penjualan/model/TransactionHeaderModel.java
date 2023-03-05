package com.android.penjualan.model;

public class TransactionHeaderModel {
    int trxheader_id, total;
    String doc_code, doc_number, doc_user, doc_date;

    public int getTrxheader_id() {
        return trxheader_id;
    }

    public void setTrxheader_id(int trxheader_id) {
        this.trxheader_id = trxheader_id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDoc_code() {
        return doc_code;
    }

    public void setDoc_code(String doc_code) {
        this.doc_code = doc_code;
    }

    public String getDoc_number() {
        return doc_number;
    }

    public void setDoc_number(String doc_number) {
        this.doc_number = doc_number;
    }

    public String getDoc_user() {
        return doc_user;
    }

    public void setDoc_user(String doc_user) {
        this.doc_user = doc_user;
    }

    public String getDoc_date() {
        return doc_date;
    }

    public void setDoc_date(String doc_date) {
        this.doc_date = doc_date;
    }
}
