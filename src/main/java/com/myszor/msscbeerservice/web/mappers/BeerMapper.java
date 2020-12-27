package com.myszor.msscbeerservice.web.mappers;

import com.myszor.msscbeerservice.domain.Beer;
import com.myszor.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    BeerDto beerToBeerDtoWithInventory(Beer beer);

    Beer beerDtoToBeer(BeerDto dto);

}
