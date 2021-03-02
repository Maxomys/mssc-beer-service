package com.myszor.msscbeerservice.events;

import com.myszor.msscbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 2911013620268960448L;

    private final BeerDto beerDto;

}
