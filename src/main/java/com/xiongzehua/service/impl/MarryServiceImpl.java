package com.xiongzehua.service.impl;

import com.xiongzehua.pojo.Person;
import com.xiongzehua.service.MarryService;
import org.springframework.stereotype.Component;

/**
 * Created by 31339 on 2018/5/20.
 */
@Component
public class MarryServiceImpl implements MarryService {
    public void marry(Person p1, Person p2) {
        int num = p1.getCars().size() + p2.getCars().size();
        System.out.print(p1.getName() + " and " + p2.getName() + " are married.");
        System.out.println(" They have " + num + " cars!");
    }
}
