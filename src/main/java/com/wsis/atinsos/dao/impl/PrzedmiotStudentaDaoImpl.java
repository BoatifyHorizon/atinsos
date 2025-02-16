package com.wsis.atinsos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wsis.atinsos.dao.PrzedmiotStudentaDao;
import com.wsis.atinsos.model.PrzedmiotStudenta;

import jakarta.persistence.TypedQuery;

@Repository
public class PrzedmiotStudentaDaoImpl extends GenericDaoImpl<PrzedmiotStudenta, Integer>
        implements PrzedmiotStudentaDao {
            
    public PrzedmiotStudentaDaoImpl() {
        super(PrzedmiotStudenta.class);
    }

    @Override
    public PrzedmiotStudenta znajdzPrzedmiotPoStudentId(int studentId) {
        try {
            TypedQuery<PrzedmiotStudenta> query = entityManager
                    .createQuery("SELECT ps FROM PrzedmiotStudenta ps WHERE ps.student.id = :studentId",
                            PrzedmiotStudenta.class)
                    .setParameter("studentId", studentId);

            List<PrzedmiotStudenta> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            throw new Error("Blad przy pobieraniu przedmiotStudenta");
        }
    }
}
