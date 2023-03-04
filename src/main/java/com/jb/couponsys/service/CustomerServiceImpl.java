package com.jb.couponsys.service;

import com.jb.couponsys.beans.Category;
import com.jb.couponsys.beans.Coupon;
import com.jb.couponsys.beans.Customer;
import com.jb.couponsys.exceptions.CouponSystemException;
import com.jb.couponsys.exceptions.ErrorMsg;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Service
public class CustomerServiceImpl extends ClientService implements CustomerService {

    @Override
    public boolean login(String email, String password) throws CouponSystemException {
        Customer customerFromDb = customerRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new CouponSystemException(ErrorMsg.CUSTOMER_EMAIL_OR_PASSWORD));
        return true;
    }

    @Override
    public void purchaseCoupon(int customerId, Coupon coupon) throws CouponSystemException {

        if (!(couponRepository.existsById(coupon.getId()))) {
            throw new CouponSystemException(ErrorMsg.NOT_EXIST_COUPON);
        }
        if (coupon.getAmount() <= 0) {
            throw new CouponSystemException(ErrorMsg.COUPON_OUT_OF_STOCK);
        }
        if ((coupon.getEndDate().before(Date.valueOf(LocalDate.now())))) {
            throw new CouponSystemException(ErrorMsg.COUPON_EXPIRED);
        }
        if (couponRepository.existsByCustomerIdAndCouponId(customerId, coupon.getId()) == 1) {
            throw new CouponSystemException(ErrorMsg.COUPON_PURCHASED);
        }
        coupon.setAmount(coupon.getAmount() - 1);
        couponRepository.addPurchasedCoupon(customerId, coupon.getId());
        couponRepository.saveAndFlush(coupon);
    }


    @Override
    public List<Coupon> getCustomerCoupon(int customerId) {
        return couponRepository.findCustomerPurchaseCoupons(customerId);
    }

    @Override
    public List<Coupon> getCustomerCouponByCategory(int customerId, Category category) {
        return couponRepository.findCustomerCouponsByCategory(category.name(), customerId);
    }

    @Override
    public List<Coupon> getCustomerCouponByMaxPrice(int customerId, double maxPrice) {
        return couponRepository.findCustomerCouponsPriceLessThan(customerId, maxPrice);
    }

    @Override
    public Customer getCustomerDetails(int customerId) throws CouponSystemException {
        List<Coupon> coupons = couponRepository.findCustomerPurchaseCoupons(customerId);
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrorMsg.NOT_EXIST_CUSTOMER));
        customer.setCoupons(coupons);
        return customer;
    }

    @Override
    public Coupon getOneCoupon(int customerId, int couponId) throws CouponSystemException {
        return couponRepository.findById(couponId).orElseThrow(() -> new CouponSystemException(ErrorMsg.NOT_EXIST_COUPON));
    }
}



