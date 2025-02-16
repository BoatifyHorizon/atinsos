package com.wsis.atinsos.dao.impl;

import org.springframework.stereotype.Repository;

import com.wsis.atinsos.dao.PozycjaBibDao;
import com.wsis.atinsos.model.PozycjaBiblioteczna;

@Repository
public class PozycjaBibDaoImpl extends GenericDaoImpl<PozycjaBiblioteczna, Integer> implements PozycjaBibDao {
    public PozycjaBibDaoImpl() {
        super(PozycjaBiblioteczna.class);
    }
}
