package com.myszor.msscbeerservice.services;

import com.myszor.msscbeerservice.domain.Beer;
import com.myszor.msscbeerservice.repositories.BeerRepository;
import com.myszor.msscbeerservice.web.controller.NotFoundException;
import com.myszor.msscbeerservice.web.mappers.BeerMapper;
import com.myszor.msscbeerservice.web.model.BeerDto;
import com.myszor.msscbeerservice.web.model.BeerPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerPagedList listBeers(String beerName, String beerStyle, PageRequest pageRequest) {
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (StringUtils.hasLength(beerName) && StringUtils.hasLength(beerStyle)) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (StringUtils.hasLength(beerName) && !StringUtils.hasLength(beerStyle)) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (!StringUtils.hasLength(beerName) && StringUtils.hasLength(beerStyle)) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        beerPagedList = new BeerPagedList(beerPage
                .getContent()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList()),
                PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
                beerPage.getTotalElements()
        );

        return beerPagedList;
    }

    @Override
    public BeerDto getById(UUID beerId) {
        return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer foundBeer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        foundBeer.setBeerName(beerDto.getBeerName());
        foundBeer.setBeerStyle(beerDto.getBeerStyle().name());
        foundBeer.setUpc(beerDto.getUpc());
        foundBeer.setPrice(beerDto.getPrice());

        return beerMapper.beerToBeerDto(beerRepository.save(foundBeer));
    }

}
