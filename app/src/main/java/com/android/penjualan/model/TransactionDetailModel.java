package com.android.penjualan.model;

public class TransactionDetailModel {
    int trxdet_id, det_price, det_subtotal, det_qty;
    String det_document_code, det_document_number, det_product_code, det_currency, det_unit, det_product_name;

    public int getTrxdet_id() {
        return trxdet_id;
    }

    public void setTrxdet_id(int trxdet_id) {
        this.trxdet_id = trxdet_id;
    }

    public int getDet_price() {
        return det_price;
    }

    public void setDet_price(int det_price) {
        this.det_price = det_price;
    }

    public int getDet_subtotal() {
        return det_subtotal;
    }

    public void setDet_subtotal(int det_subtotal) {
        this.det_subtotal = det_subtotal;
    }

    public int getDet_qty() {
        return det_qty;
    }

    public void setDet_qty(int det_qty) {
        this.det_qty = det_qty;
    }

    public String getDet_document_code() {
        return det_document_code;
    }

    public void setDet_document_code(String det_document_code) {
        this.det_document_code = det_document_code;
    }

    public String getDet_document_number() {
        return det_document_number;
    }

    public void setDet_document_number(String det_document_number) {
        this.det_document_number = det_document_number;
    }

    public String getDet_product_code() {
        return det_product_code;
    }

    public void setDet_product_code(String det_product_code) {
        this.det_product_code = det_product_code;
    }

    public String getDet_currency() {
        return det_currency;
    }

    public void setDet_currency(String det_currency) {
        this.det_currency = det_currency;
    }

    public String getDet_unit() {
        return det_unit;
    }

    public void setDet_unit(String det_unit) {
        this.det_unit = det_unit;
    }

    public String getDet_product_name() {
        return det_product_name;
    }

    public void setDet_product_name(String det_product_name) {
        this.det_product_name = det_product_name;
    }
}
