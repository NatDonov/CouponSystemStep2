package com.jb.couponsys.clients;

import com.jb.couponsys.exceptions.CouponSystemException;
import com.jb.couponsys.exceptions.ErrorMsg;
import com.jb.couponsys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginManager {
    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private CompanyServiceImpl companyService;
    @Autowired
    private CustomerServiceImpl customerService;

    public ClientService login(String email, String password, ClientType clientType) throws CouponSystemException {

        switch (clientType) {
            case ADMINISTRATOR:
                if (adminService.login(email, password)) {
                    return adminService;
                }
                break;
            case COMPANY:
                if (companyService.login(email, password)) {
                    return companyService;
                }
                break;
            case CUSTOMER:
                if (customerService.login(email, password)) {
                    return customerService;
                }
                break;
            default:
                throw new CouponSystemException(ErrorMsg.INVALID_CLIENT_TYPE);
        }
        throw new CouponSystemException(ErrorMsg.LOGIN_FAILED);
    }

}
