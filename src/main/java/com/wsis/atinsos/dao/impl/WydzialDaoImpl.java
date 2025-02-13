package com.wsis.atinsos.dao.impl;

import org.springframework.stereotype.Repository;

import com.wsis.atinsos.dao.WydzialDao;
import com.wsis.atinsos.model.Wydzial;

@Repository
public class WydzialDaoImpl extends GenericDaoImpl<Wydzial, Integer> implements WydzialDao {
    public WydzialDaoImpl() {
        super(Wydzial.class);
    }
}
