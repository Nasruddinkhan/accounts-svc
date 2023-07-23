package com.sa.tawuniya.assingment.account.repository;

import com.sa.tawuniya.assingment.account.model.entity.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {
}
