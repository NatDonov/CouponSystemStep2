package com.jb.couponsys.service;

import com.jb.couponsys.beans.Category;
import com.jb.couponsys.beans.Company;
import com.jb.couponsys.beans.Coupon;
import com.jb.couponsys.exceptions.CouponSystemException;
import com.jb.couponsys.exceptions.ErrorMsg;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl extends ClientService implements CompanyService {

    @Override
    public boolean login(String email, String password) throws CouponSystemException {
        Company companyFromDb = companyRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new CouponSystemException(ErrorMsg.COMPANY_EMAIL_OR_PASSWORD));
        return true;
    }

    @Override
    public void deleteCoupon(int companyId, int couponId) throws CouponSystemException {
        if (!(couponRepository.existsById(couponId))) {
            throw new CouponSystemException(ErrorMsg.NOT_EXIST_COUPON);
        }
        couponRepository.deletePurchaseCoupon(couponId);
        couponRepository.deleteById(couponId);
    }

    @Override
    public void addCoupon(int companyId, Coupon coupon) throws CouponSystemException {
        if (couponRepository.existsByTitleAndCompanyId(coupon.getTitle(), companyId)) {
            throw new CouponSystemException(ErrorMsg.COUPON_TITLE_EXIST);
        }
        couponRepository.save(coupon);
    }


    @Override
    public void updateCoupon(int companyId, int couponId, Coupon coupon) throws CouponSystemException {
        Company companyFromDB = companyRepository.findById(companyId)
                .orElseThrow(() -> new CouponSystemException(ErrorMsg.NOT_EXIST_COMPANY));
        if (!this.couponRepository.existsById(couponId)) {
            throw new CouponSystemException(ErrorMsg.NOT_EXIST_COUPON);
        }
        if (couponId != coupon.getId()) {
            throw new CouponSystemException(ErrorMsg.COUPON_UPDATE_ID);
        }
        if (couponRepository.existsByTitleAndCompanyId(coupon.getTitle(), companyId)) {
            throw new CouponSystemException(ErrorMsg.INVALID_COUPON_UPDATE);
        }
        coupon.setCompany(companyFromDB);
        coupon.setId(couponId);
        couponRepository.saveAndFlush(coupon);
    }


    @Override
    public List<Coupon> getCompanyCoupons(int companyId) {
        return couponRepository.findByCompanyId(companyId);
    }


    @Override
    public List<Coupon> getAllCompanyCouponsByCategory(int companyId, Category category) {
        return couponRepository.findByCompanyIdAndCategory(companyId, category);
    }

    @Override
    public List<Coupon> getCompanyCouponsByMaxPrice(int companyId, double maxPrice) {
        return couponRepository.findByCompanyIdAndPriceLessThanEqual(companyId, maxPrice);
    }

    @Override
    public Company getCompanyDetails(int companyId) throws CouponSystemException {
        List<Coupon> coupons = couponRepository.findByCompanyId(companyId);
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrorMsg.NOT_EXIST_COMPANY));
        company.setCoupons(coupons);
        return company;
    }

    @Override
    public Coupon getOneCoupon(int companyId, int couponId) throws CouponSystemException {
        return couponRepository.findById(couponId).orElseThrow(() -> new CouponSystemException(ErrorMsg.NOT_EXIST_COUPON));
    }


}
