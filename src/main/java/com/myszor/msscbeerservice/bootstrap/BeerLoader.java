package com.myszor.msscbeerservice.bootstrap;

import com.myszor.msscbeerservice.domain.Beer;
import com.myszor.msscbeerservice.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (beerRepository.count() == 0) {
            loadBeerObjects();
        }
    }

    private void loadBeerObjects() {
        beerRepository.save(Beer.builder()
                .beerName("Mango Bobs")
                .beerStyle("IPA")
                .createdDate(Timestamp.from(Instant.now()))
                .lastModifiedDate(Timestamp.from(Instant.now()))
                .quantityToBrew(200)
                .minOnHand(12)
                .upc(BEER_1_UPC)
                .price(new BigDecimal("12.95"))
                .build());

        beerRepository.save(Beer.builder()
                .beerName("Galaxy Cat")
                .beerStyle("PALE_ALE")
                .createdDate(Timestamp.from(Instant.now()))
                .lastModifiedDate(Timestamp.from(Instant.now()))
                .quantityToBrew(200)
                .minOnHand(12)
                .upc(BEER_2_UPC)
                .price(new BigDecimal("11.95"))
                .build());

        beerRepository.save(Beer.builder()
                .beerName("Pinball Porter")
                .beerStyle("PALE_ALE")
                .createdDate(Timestamp.from(Instant.now()))
                .lastModifiedDate(Timestamp.from(Instant.now()))
                .minOnHand(12)
                .quantityToBrew(200)
                .price(new BigDecimal("12.95"))
                .upc(BEER_3_UPC)
                .build());
    }

}
