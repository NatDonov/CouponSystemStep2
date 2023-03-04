package com.jb.couponsys.service;

import com.jb.couponsys.beans.Category;
import com.jb.couponsys.beans.Company;
import com.jb.couponsys.beans.Coupon;
import com.jb.couponsys.exceptions.CouponSystemException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {

    void deleteCoupon(int companyId, int couponId) throws CouponSystemException;

    void addCoupon(int companyId, Coupon coupon) throws CouponSystemException;

    void updateCoupon(int companyId, int couponId, Coupon coupon) throws CouponSystemException;

    List<Coupon> getCompanyCoupons(int companyId) throws CouponSystemException;

    List<Coupon> getAllCompanyCouponsByCategory(int companyId, Category category);

    List<Coupon> getCompanyCouponsByMaxPrice(int companyId, double price);

    Company getCompanyDetails(int companyId) throws CouponSystemException;

    Coupon getOneCoupon(int companyId, int couponId) throws CouponSystemException;



}
