package com.wsis.atinsos.dao.impl;

import org.springframework.stereotype.Repository;

import com.wsis.atinsos.dao.PlatnoscDao;
import com.wsis.atinsos.model.Platnosc;

@Repository
public class PlatnoscDaoImpl extends GenericDaoImpl<Platnosc, Integer> implements PlatnoscDao {
    public PlatnoscDaoImpl() {
        super(Platnosc.class);
    }
}
