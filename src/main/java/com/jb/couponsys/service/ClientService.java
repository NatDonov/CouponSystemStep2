package com.jb.couponsys.service;

import com.jb.couponsys.exceptions.CouponSystemException;
import com.jb.couponsys.repos.CompanyRepository;
import com.jb.couponsys.repos.CouponRepository;
import com.jb.couponsys.repos.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class ClientService {

    @Autowired
    protected CompanyRepository companyRepository;
    @Autowired
    protected CouponRepository couponRepository;
    @Autowired
    protected CustomerRepository customerRepository;

    public abstract boolean login(String email, String password) throws CouponSystemException;
}
