package com.jb.couponsys.controller;


import com.jb.couponsys.beans.Category;
import com.jb.couponsys.beans.Company;
import com.jb.couponsys.beans.Coupon;
import com.jb.couponsys.exceptions.CouponSystemException;
import com.jb.couponsys.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/company")
//@RequiredArgsConstructor
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("{companyId}/coupons")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCoupon(@PathVariable int companyId, @RequestBody Coupon coupon) throws CouponSystemException {
        companyService.addCoupon(companyId, coupon);
    }

    @PutMapping("{companyId}/coupons/{couponId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCoupon(@PathVariable int companyId, @PathVariable int couponId, @RequestBody Coupon coupon) throws CouponSystemException {
        companyService.updateCoupon(companyId, couponId, coupon);
    }

    @DeleteMapping("{companyId}/coupons/{couponId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoupon(@PathVariable int companyId, @PathVariable int couponId) throws CouponSystemException {
        companyService.deleteCoupon(companyId, couponId);
    }


    @GetMapping("{companyId}/coupons")
    public List<Coupon> getCompanyCoupons(@PathVariable int companyId) throws CouponSystemException {
        return companyService.getCompanyCoupons(companyId);
    }


    @GetMapping("{companyId}/coupons/category")
    public List<Coupon> getAllCompaniesByCategory(@PathVariable int companyId, @RequestParam("category") String category) {
        return companyService.getAllCompanyCouponsByCategory(companyId, Category.valueOf(category));
    }

    @GetMapping("{companyId}/coupons/max-price")
    public List<Coupon> getCompanyCouponsByMaxPrice(@PathVariable int companyId, @RequestParam int maxPrice) {
        return companyService.getCompanyCouponsByMaxPrice(companyId, maxPrice);
    }


    @GetMapping("{companyId}")
    public Company getCompanyDetails(@PathVariable int companyId) throws CouponSystemException {
        return companyService.getCompanyDetails(companyId);
    }


    @GetMapping("{companyId}/{couponId}")
    public Coupon getOneCoupon(@PathVariable int companyId, @PathVariable int couponId) throws CouponSystemException {
        return companyService.getOneCoupon(companyId, couponId);
    }


}
