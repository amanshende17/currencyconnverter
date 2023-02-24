package com.currencyExchange.converter.service;

import com.currencyExchange.converter.model.Audit_Info;

import java.util.List;

public interface Audit_Info_Service {

    Audit_Info createInfo(Audit_Info audit_info);
    Audit_Info updateInfo(Audit_Info audit_info);
    List<Audit_Info> getAllInfo();
    Audit_Info getInfoById();

    Audit_Info getInfoById(Integer requestId);

    void deleteInfo(Integer requestId);
    Boolean checkId(Integer requestId);
}
