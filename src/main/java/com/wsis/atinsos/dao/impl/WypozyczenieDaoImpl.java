package com.wsis.atinsos.dao.impl;

import org.springframework.stereotype.Repository;

import com.wsis.atinsos.dao.WypozyczenieDao;
import com.wsis.atinsos.model.Wypozyczenie;

@Repository
public class WypozyczenieDaoImpl extends GenericDaoImpl<Wypozyczenie, Integer> implements WypozyczenieDao {
    public WypozyczenieDaoImpl() {
        super(Wypozyczenie.class);
    }
}
