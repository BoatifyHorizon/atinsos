package com.wsis.atinsos.dao.impl;

import org.springframework.stereotype.Repository;

import com.wsis.atinsos.dao.RezerwacjaDao;
import com.wsis.atinsos.model.Rezerwacja;

@Repository
public class RezerwacjaDaoImpl extends GenericDaoImpl<Rezerwacja, Integer> implements RezerwacjaDao {
    public RezerwacjaDaoImpl() {
        super(Rezerwacja.class);
    }
}
