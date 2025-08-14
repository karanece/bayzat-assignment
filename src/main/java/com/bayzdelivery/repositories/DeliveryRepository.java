package com.bayzdelivery.repositories;

import com.bayzdelivery.model.Delivery;
import java.time.Instant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(exported = false)
public interface DeliveryRepository extends CrudRepository<Delivery, Long>,
                                            PagingAndSortingRepository<Delivery, Long>,
                                            JpaRepository<Delivery, Long> {

    @Query(name = "isAgentAlreadyDelivering",
            nativeQuery = true,
            value = "SELECT  d.delivery_man_id " +
                    "  FROM  Delivery d " +
                    " WHERE  d.delivery_man_id = :delivery_man_id " +
                    "   AND  d.start_time <= :start_time " +
                    "   AND  d.end_time IS NULL ")
    Long isAgentAlreadyDelivering(@Param("delivery_man_id") final Long deliveryManId,
                                  @Param("start_time") final Instant startTime);

}
