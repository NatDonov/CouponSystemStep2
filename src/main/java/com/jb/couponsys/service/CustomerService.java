package com.jb.couponsys.service;

import com.jb.couponsys.beans.Category;
import com.jb.couponsys.beans.Coupon;
import com.jb.couponsys.beans.Customer;
import com.jb.couponsys.exceptions.CouponSystemException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    void purchaseCoupon(int customerId, Coupon coupon) throws CouponSystemException;

    List<Coupon> getCustomerCoupon(int customerId) throws CouponSystemException;

    List<Coupon> getCustomerCouponByCategory(int customerId, Category category) throws CouponSystemException;

    List<Coupon> getCustomerCouponByMaxPrice(int customerId, double maxPrice);

    Customer getCustomerDetails(int customerId) throws CouponSystemException;

    Coupon getOneCoupon(int customerId, int couponId) throws CouponSystemException;
}
