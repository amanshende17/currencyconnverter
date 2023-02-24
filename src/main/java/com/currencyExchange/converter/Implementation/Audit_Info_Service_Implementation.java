package com.currencyExchange.converter.Implementation;

import com.currencyExchange.converter.dao.Audit_Info_Repository;
import com.currencyExchange.converter.model.Audit_Info;
import com.currencyExchange.converter.service.Audit_Info_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Audit_Info_Service_Implementation implements Audit_Info_Service {

    @Autowired
    private Audit_Info_Repository audit_info_repository;

    @Override
    public Audit_Info createInfo(Audit_Info audit_info) {
        return audit_info_repository.save(audit_info);
    }

    @Override
    public Audit_Info updateInfo(Audit_Info audit_info) {
        Optional<Audit_Info> audit_info1=this.audit_info_repository.findById(audit_info.getRequest_Id());
        if(audit_info1.isPresent()){
            Audit_Info auditUpdate = audit_info1.get();
            auditUpdate.setRequest(audit_info.getRequest());
            auditUpdate.setResponse(audit_info.getResponse());
            auditUpdate.setRequestStatus(audit_info.getRequestStatus() );
            return this.audit_info_repository.save(auditUpdate);
        }
        else{
            throw new RuntimeException("Audit Not Found with given Id");
        }
    }

    @Override
    public List<Audit_Info> getAllInfo() {
        return this.audit_info_repository.findAll();
    }

    @Override
    public Audit_Info getInfoById() {
        return null;
    }

    @Override
    public Audit_Info getInfoById(Integer requestId) {
        {
            Optional<Audit_Info> audit_info_obj = this.audit_info_repository.findById(requestId);
            if(audit_info_obj.isPresent()){
                return audit_info_obj.get();
            }
            else {
                throw new RuntimeException("Audit not found for this id");
            }
        }
    }

    @Override
    public void deleteInfo(Integer requestId) {
        Optional<Audit_Info> audit_info_obj = this.audit_info_repository.findById(requestId);
        if(audit_info_obj.isPresent()){
            this.audit_info_repository.deleteById(requestId);
        }
        else {
            throw new RuntimeException("Audit not found for this id");
        }
    }

    @Override
    public Boolean checkId(Integer requestId) {
        Optional<Audit_Info> audit_info_obj = this.audit_info_repository.findById(requestId);
        if(audit_info_obj.isPresent()){
            return true;
        }
        else {
            return false;
        }
    }
}
