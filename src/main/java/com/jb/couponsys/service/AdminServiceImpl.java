package com.jb.couponsys.service;


import com.jb.couponsys.beans.Company;
import com.jb.couponsys.beans.Coupon;
import com.jb.couponsys.beans.Customer;
import com.jb.couponsys.exceptions.CouponSystemException;
import com.jb.couponsys.exceptions.ErrorMsg;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl extends ClientService implements AdminService {

    @Override
    public boolean login(String email, String password) throws CouponSystemException {
        if (!(email.equals("admin@admin.com") && password.equals("admin"))) {
            throw new CouponSystemException(ErrorMsg.ADMIN_LOGIN_ERROR);
        }
        return true;
    }

    @Override
    public void addCompany(Company company) throws CouponSystemException {
        if (companyRepository.existsById(company.getId())) {
            throw new CouponSystemException(ErrorMsg.COMPANY_EXIST);
        }
        if (companyRepository.existsByName(company.getName())) {
            throw new CouponSystemException(ErrorMsg.COMPANY_NAME_EXIST);
        }
        if (companyRepository.existsByEmail(company.getEmail())) {
            throw new CouponSystemException(ErrorMsg.COMPANY_EMAIL_EXIST);
        }
        companyRepository.save(company);
    }

    @Override
    public void updateCompany(int companyId, Company company) throws CouponSystemException {
        Company companyFromDb = companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrorMsg.COMPANY_EXIST));

        if (companyId != company.getId()) {
            throw new CouponSystemException(ErrorMsg.COMPANY_UPDATE_ID);
        }
        if (!(companyFromDb.getName().equals(company.getName()))) {
            throw new CouponSystemException(ErrorMsg.COMPANY_UPDATE_NAME);
        }
        if (companyRepository.existsByEmailAndIdNot(company.getEmail(), companyId)) {
            throw new CouponSystemException(ErrorMsg.COMPANY_EMAIL_EXIST);
        }

        companyRepository.saveAndFlush(company);
    }

    @Override
    public void deleteCompany(int companyId) throws CouponSystemException {
        if (!companyRepository.existsById(companyId)) {
            throw new CouponSystemException(ErrorMsg.NOT_EXIST_COMPANY);
        }
        for (Coupon coupon : couponRepository.findByCompanyId(companyId)) {
            couponRepository.deletePurchaseCoupon(coupon.getId());
        }
        couponRepository.deletePurchaseCouponByCompanyId(companyId);
        companyRepository.deleteById(companyId);
    }


    @Override
    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    @Override
    public Company getSingleCompany(int companyId) throws CouponSystemException {
        return companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrorMsg.NOT_EXIST_COMPANY));
    }

    @Override
    public void addCustomer(Customer customer) throws CouponSystemException {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new CouponSystemException(ErrorMsg.CUSTOMER_EMAIL_EXIST);
        }
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(int customerId, Customer customer) throws CouponSystemException {
        Customer customerFromBd = customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrorMsg.NOT_EXIST_CUSTOMER));
        if (customerId != customer.getId()) {
            throw new CouponSystemException(ErrorMsg.CUSTOMER_UPDATE_ID);
        }
        if (customerRepository.existsByEmailAndIdNot(customer.getEmail(), customerId)) {
            throw new CouponSystemException(ErrorMsg.CUSTOMER_EXIST);
        }

        customerRepository.saveAndFlush(customer);

    }

    @Override
    public void deleteCustomer(int customerId) throws CouponSystemException {
        if (!customerRepository.existsById(customerId)) {
            throw new CouponSystemException(ErrorMsg.NOT_EXIST_CUSTOMER);
        }
        couponRepository.deletePurchaseCouponByCustomerId(customerId);
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getSingleCustomer(int customerId) throws CouponSystemException {
        return customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrorMsg.NOT_EXIST_CUSTOMER));
    }

}
