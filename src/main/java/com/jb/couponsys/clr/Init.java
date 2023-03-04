package com.jb.couponsys.clr;

import com.jb.couponsys.beans.Category;
import com.jb.couponsys.beans.Company;
import com.jb.couponsys.beans.Coupon;
import com.jb.couponsys.beans.Customer;
import com.jb.couponsys.repos.CompanyRepository;
import com.jb.couponsys.repos.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
@Order(1)
public class Init implements CommandLineRunner {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {


        Coupon c1 = Coupon.builder().
                amount(4).
                category(Category.FOOD).
                description("descr1").
                title("title1").
                price(150).
                startDate(Date.valueOf(LocalDate.now().minusMonths(2))).
                endDate(Date.valueOf(LocalDate.now().plusMonths(2))).
                image("c1").
                build();

        Coupon c2 = Coupon.builder().
                amount(4).
                category(Category.ELECTRICITY).
                description("descr2").
                title("title2").
                price(50).
                startDate(Date.valueOf(LocalDate.now())).
                endDate(Date.valueOf(LocalDate.now().plusMonths(4))).
                image("c2").
                build();

        Coupon c3 = Coupon.builder().
                amount(2).
                category(Category.FOOD).
                description("descr3").
                title("title3").
                price(500).
                startDate(Date.valueOf(LocalDate.now().minusMonths(2))).
                endDate(Date.valueOf(LocalDate.now().minusMonths(1))).
                image("c3").
                build();

        Coupon c4 = Coupon.builder().
                amount(6).
                category(Category.COURSES).
                description("descr4").
                title("title4").
                price(350).
                startDate(Date.valueOf(LocalDate.now())).
                endDate(Date.valueOf(LocalDate.now().plusMonths(1))).
                image("c4").
                build();


        Company cm1 = Company.builder().
                name("Company1").
                email("Company1@gmail.com").
                password("1111").
                coupon(c1).build();

        Company cm2 = Company.builder().
                name("Company2").
                email("Company2@gmail.com").
                password("2222").
                coupon(c2).build();

        Company cm3 = Company.builder().
                name("Company3").
                email("Company3@gmail.com").
                password("3333").
                coupon(c3).build();

        Company cm4 = Company.builder().
                name("Company4").
                email("Company4@gmail.com").
                password("4444").
                coupon(c4).build();

        c1.setCompany(cm1);
        c2.setCompany(cm2);
        c3.setCompany(cm3);
        c4.setCompany(cm4);

        Customer cs1 = Customer.builder().
                firstName("Customer1").
                lastName("CustomerCust1").
                email("Customer1@gmail.com").
                password("111").
                coupons(List.of(c1,c3)).
                build();

        Customer cs2 = Customer.builder().
                firstName("Customer2").
                lastName("CustomerCust2").
                email("Customer2@gmail.com").
                password("222").
                build();

        Customer cs3 = Customer.builder().
                firstName("Customer3").
                lastName("CustomerCust3").
                email("Customer3@gmail.com").
                password("333").
                coupons(List.of(c2,c3)).
                build();

        Customer cs4 = Customer.builder().
                firstName("Customer4").
                lastName("CustomerCust4").
                email("Customer4@gmail.com").
                password("444").
                build();


        companyRepository.saveAll(List.of(cm1,cm2,cm3,cm4));

        customerRepository.saveAll(List.of(cs1,cs2,cs3,cs4));


    }
}
