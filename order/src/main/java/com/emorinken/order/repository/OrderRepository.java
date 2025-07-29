package com.emorinken.order.repository;

import com.emorinken.order.domain.Order;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
