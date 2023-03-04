package com.jb.couponsys.controller;

import com.jb.couponsys.beans.Category;

import com.jb.couponsys.beans.Coupon;
import com.jb.couponsys.beans.Customer;
import com.jb.couponsys.exceptions.CouponSystemException;
import com.jb.couponsys.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("{customerId}/coupons")
    public List<Coupon> getCustomerCoupons(@PathVariable int customerId) throws CouponSystemException {
        return customerService.getCustomerCoupon(customerId);
    }

    @GetMapping("{customerId}/coupons/category")
    public List<Coupon> getCustomerCouponsByCategory(@PathVariable int customerId, @RequestParam("category") String category) throws CouponSystemException {
        return customerService.getCustomerCouponByCategory(customerId, Category.valueOf(category));
    }


    @GetMapping("{customerId}/coupons/max-price")
    public List<Coupon> getCustomerCouponsByMaxPrice(@PathVariable int customerId, @RequestParam int maxPrice){
        return customerService.getCustomerCouponByMaxPrice(customerId, maxPrice);
    }


    @GetMapping("{customerId}")
    public Customer getCustomerDetails(@PathVariable int customerId) throws CouponSystemException {
        return customerService.getCustomerDetails(customerId);
    }


    @PostMapping("{customerId}/coupons")
    @ResponseStatus(HttpStatus.CREATED)
    public void purchaseCoupon(@PathVariable int customerId, @RequestBody Coupon coupon) throws CouponSystemException {
        customerService.purchaseCoupon(customerId, coupon);
    }

    @GetMapping("{customerId}/{couponId}")
    public Coupon getOneCoupon(@PathVariable int customerId, @PathVariable int couponId) throws CouponSystemException {
        return customerService.getOneCoupon(customerId, couponId);
    }


}
