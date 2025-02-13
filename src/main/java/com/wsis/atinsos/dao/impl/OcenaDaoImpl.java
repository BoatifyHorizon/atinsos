package com.wsis.atinsos.dao.impl;

import org.springframework.stereotype.Repository;

import com.wsis.atinsos.dao.OcenaDao;
import com.wsis.atinsos.model.Ocena;

@Repository
public class OcenaDaoImpl extends GenericDaoImpl<Ocena, Integer> implements OcenaDao {
    public OcenaDaoImpl() {
        super(Ocena.class);
    }
}
