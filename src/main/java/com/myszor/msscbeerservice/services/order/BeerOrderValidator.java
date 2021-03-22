package com.myszor.msscbeerservice.services.order;

import com.myszor.brewery.model.BeerOrderDto;
import com.myszor.msscbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidator {

    private final BeerRepository beerRepository;

    public boolean validateOrder(BeerOrderDto beerOrder) {

        return beerOrder.getBeerOrderLines().stream()
                .anyMatch(orderLine -> beerRepository.findByUpc(orderLine.getUpc()).isEmpty());
    }

}
