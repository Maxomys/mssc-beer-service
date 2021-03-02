package com.myszor.msscbeerservice.services;

import com.myszor.msscbeerservice.config.JmsConfig;
import com.myszor.msscbeerservice.domain.Beer;
import com.myszor.msscbeerservice.events.BrewBeerEvent;
import com.myszor.msscbeerservice.repositories.BeerRepository;
import com.myszor.msscbeerservice.services.inventory.BeerInventoryService;
import com.myszor.msscbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer invQOH = beerInventoryService.getOnHandInventory(beer.getId());

            log.debug("Min on hand is: " + beer.getMinOnHand());
            log.debug("Inventory is: "  + invQOH);

            if (beer.getMinOnHand() >= invQOH) {
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }
        });
    }

}
