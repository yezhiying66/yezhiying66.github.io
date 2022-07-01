package com.wb.dao;



import com.wb.pojo.Yyds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YydsRepository extends JpaRepository<Yyds,Integer> {
    Yyds findByName(String email);
}
