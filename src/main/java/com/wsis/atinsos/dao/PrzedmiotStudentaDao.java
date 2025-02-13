package com.wsis.atinsos.dao;

import com.wsis.atinsos.model.PrzedmiotStudenta;

public interface PrzedmiotStudentaDao extends GenericDao<PrzedmiotStudenta, Integer>{
    PrzedmiotStudenta znajdzPrzedmiotPoStudentId(int studentId);
}
