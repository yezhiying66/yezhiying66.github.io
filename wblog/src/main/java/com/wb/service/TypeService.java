package com.wb.service;

import com.wb.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Banyue on 2022/3/18 0018 14:29
 */
public interface TypeService {

    Type saveType(Type type);

    Type getType(Integer id);

    Type getTypeByName(String name);

    Page<Type> listType(Pageable pageable);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);

    Type updateType(Integer id, Type type);

    void deleteType(Integer id);
}
