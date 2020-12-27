package com.myszor.msscbeerservice.web.mappers;

import com.myszor.msscbeerservice.domain.Beer;
import com.myszor.msscbeerservice.services.inventory.BeerInventoryService;
import com.myszor.msscbeerservice.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;

public class BeerMapperDecorator implements BeerMapper {

    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }

    public BeerDto beerToBeerDto(Beer beer) {
        return mapper.beerToBeerDto(beer);
    }

    @Override
    public BeerDto beerToBeerDtoWithInventory(Beer beer) {
        BeerDto dto = mapper.beerToBeerDto(beer);
        dto.setQuantityOnHand(beerInventoryService.getOnHandInventory(dto.getId()));
        return dto;
    }

    @Override
    public Beer beerDtoToBeer(BeerDto dto) {
        return mapper.beerDtoToBeer(dto);
    }
}
