package com.wsis.atinsos.dao.impl;

import com.wsis.atinsos.dao.WydzialDao;
import com.wsis.atinsos.model.Wydzial;
import org.springframework.stereotype.Repository;

@Repository
public class WydzialDaoImpl extends GenericDaoImpl<Wydzial, Integer> implements WydzialDao {
    public WydzialDaoImpl() {
        super(Wydzial.class);
    }
}
