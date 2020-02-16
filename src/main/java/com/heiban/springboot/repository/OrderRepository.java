package com.heiban.springboot.repository;

import com.heiban.springboot.bean.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

//继承JpaRepository来完成对数据库的操作
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    //通过商户订单查询
    @Query(value = "select * from order_form where out_trade_no=?", nativeQuery = true)
    public Order findByOuttradeno(String out_trade_no);

}
