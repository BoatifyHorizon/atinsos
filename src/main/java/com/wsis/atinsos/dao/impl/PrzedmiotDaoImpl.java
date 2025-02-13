package com.wsis.atinsos.dao.impl;

import org.springframework.stereotype.Repository;

import com.wsis.atinsos.dao.PrzedmiotDao;
import com.wsis.atinsos.model.Przedmiot;

@Repository
public class PrzedmiotDaoImpl extends GenericDaoImpl<Przedmiot, Integer> implements PrzedmiotDao {
    public PrzedmiotDaoImpl() {
        super(Przedmiot.class);
    }
}
