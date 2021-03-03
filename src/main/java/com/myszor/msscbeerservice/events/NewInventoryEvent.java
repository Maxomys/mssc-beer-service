package com.myszor.msscbeerservice.events;

import com.myszor.msscbeerservice.web.model.BeerDto;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {

    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }

}
