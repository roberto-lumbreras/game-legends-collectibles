package org.factoriaf5.game_legends_collectibles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.factoriaf5.game_legends_collectibles.model.Product;
import org.factoriaf5.game_legends_collectibles.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Product> products = new ArrayList<>();

        products.add(new Product(
                null,
                "2B Figure",
                "This 2B figure perfectly captures her iconic style and elegance. Designed in a dynamic pose holding her Virtuous Contract katana, it features her detailed gothic black outfit with lace accents, gloves, and high boots.",
                "/default-images/2b.jpg",
                new BigDecimal(90.0),
                10));

        products.add(new Product(
                null,
                "Aloy Figure",
                "This Aloy figure from Horizon Zero Dawn features her iconic Nora Brave armor and her bow. The dynamic pose reflects her readiness for combat, standing atop a base inspired by the game's post-apocalyptic landscapes.",
                "/default-images/aloy.jpg",
                new BigDecimal(85.0),
                15));

        products.add(new Product(
                null,
                "Spider-Man Figure",
                "A detailed Spider-Man figure in his iconic red and blue suit, posed mid-swing with web effects. The base represents a New York City rooftop, making it a must-have for Marvel fans.",
                "/default-images/spiderman.jpg",
                new BigDecimal(75.0),
                20));

        products.add(new Product(
                null,
                "Kratos Figure",
                "This Kratos figure showcases the God of War in his rugged armor, wielding the Leviathan Axe. Standing on a base inspired by Norse mythology, it captures his fierce and determined persona.",
                "/default-images/kratos.jpg",
                new BigDecimal(95.0),
                12));

        products.add(new Product(
                null,
                "Commander Shepard Figure",
                "A stunning Commander Shepard figure in N7 armor from Mass Effect. The detailed sculpt includes a rifle and Omni-tool, with a base resembling a Reaper invasion scene.",
                "/default-images/shepard.jpg",
                new BigDecimal(100.0),
                8));

        products.add(new Product(
                null,
                "Goku Super Saiyan 3 Figure",
                "This Goku Super Saiyan 3 figure captures his immense power with flowing golden hair and a dynamic pose. The energy aura base adds a dramatic effect for Dragon Ball fans.",
                "/default-images/goku.jpg",
                new BigDecimal(70.0),
                25));

        products.add(new Product(
                null,
                "Ciri Figure",
                "A Ciri figure from The Witcher 3, showcasing her battle-ready stance with the Zireael sword. The detailed outfit reflects her ashen hair and intricate armor design.",
                "/default-images/ciri.jpg",
                new BigDecimal(80.0),
                10));

        products.add(new Product(
                null,
                "Lara Croft Figure",
                "A detailed Lara Croft figure from Tomb Raider, showcasing her dual pistols and iconic explorer outfit. The base includes jungle ruins and relics, capturing her adventurous spirit.",
                "/default-images/lara-croft.jpg",
                new BigDecimal(80.0),
                18));
        productRepository.saveAll(products);
    }

}
