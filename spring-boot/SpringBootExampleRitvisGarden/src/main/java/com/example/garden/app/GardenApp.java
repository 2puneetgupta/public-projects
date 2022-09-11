package com.example.garden.app;
import com.example.garden.app.repository.SeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GardenApp implements CommandLineRunner{
    public static void main(String[] args) {
        SpringApplication.run(GardenApp.class, args);
    }
    @Autowired
    private SeedRepository seedRepository;

    @Override
    public void run(String... args) throws Exception {

         /* Seed seed1 = new Seed("ONION_RED", "KRAFTSEEDS");
          seedRepository.save(seed1);

          Seed seed2 = new Seed("ONION_WHITE", "KRAFTSEEDS");
          seedRepository.save(seed2);

          Seed seed3 = new Seed("LOKI", "OLD2");
          seedRepository.save(seed3);*/

    }
/*
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
*/

}
