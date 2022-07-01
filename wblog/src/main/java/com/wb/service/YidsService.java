package com.wb.service;

import com.wb.dao.YydsRepository;
import com.wb.pojo.Yyds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class YidsService {

    @Autowired
    private YydsRepository yydsRepository;

    @Transactional // 事务
    public Yyds savers(Yyds yyds) {
        return yydsRepository.save(yyds);
    }
}
