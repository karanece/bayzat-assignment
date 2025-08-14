package com.bayzdelivery.repositories;

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.model.DeliveryPerson;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
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

    @Query(name = "findDelayedDelivery",
            nativeQuery = true,
            value = " SELECT  d.id " +
                    "   FROM  Delivery d " +
                    "  WHERE  EXTRACT(EPOCH FROM CURRENT_TIMESTAMP - d.start_time) / 60 >= :interval " +
                    "    AND  d.end_time IS NULL"
    )
    List<Long> findDelayedDelivery(@Param("interval") final Integer interval);

    @Query(name = "findTopKAgentsWithMostCommission",
            nativeQuery = true,
            value = " SELECT  p.id AS id" +
                    "        ,p.name AS name " +
                    "        ,d.commission AS commission " +
                    "        ,(SELECT  ROUND(AVG(d1.commission), 4) " +
                    "            FROM  Delivery d1 " +
                    "           WHERE  d1.start_time >= :start_time " +
                    "             AND  d1.end_time <= :end_time) AS avg_commission " +
                    "        ,d.start_time AS start_time " +
                    "        ,d.end_time AS end_time " +
                    "   FROM  Person p " +
                    "        ,Delivery d " +
                    "  WHERE  d.delivery_man_id = p.id " +
                    "    AND  d.start_time >= :start_time " +
                    "    AND  d.end_time <= :end_time " +
                    "  ORDER " +
                    "     BY  d.commission DESC " +
                    "  LIMIT  :limit ")
    List<DeliveryPerson> findTopKAgentsWithMostCommission(@Param("limit") final Integer limit,
                                                          @Param("start_time") final Instant startTime,
                                                          @Param("end_time") final Instant endTime);

}
