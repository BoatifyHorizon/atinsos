package com.wsis.atinsos.dao.impl;

import org.springframework.stereotype.Repository;

import com.wsis.atinsos.dao.AdresDao;
import com.wsis.atinsos.model.Adres;

@Repository
public class AdresDaoImpl extends GenericDaoImpl<Adres, Integer> implements AdresDao {
    public AdresDaoImpl() {
        super(Adres.class);
    }
}
