package com.currencyExchange.converter.dao;

import com.currencyExchange.converter.model.Audit_Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Audit_Info_Repository extends JpaRepository<Audit_Info, Integer> {

}
