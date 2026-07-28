package sn.naavetane.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sn.naavetane.backend.entities.ActualiteEntity;
import sn.naavetane.backend.entities.SliderEntity;
import sn.naavetane.backend.entities.SocialNetworkEntity;
import sn.naavetane.backend.repositories.ActualiteRepository;
import sn.naavetane.backend.repositories.SliderRepository;
import sn.naavetane.backend.repositories.SocialNetworkRepository;

@Configuration
public class PortailSeeder {

    @Bean
    public CommandLineRunner initPortailData(SliderRepository sliderRepository,
                                             SocialNetworkRepository socialNetworkRepository,
                                             ActualiteRepository actualiteRepository) {
        return args -> {
            // Seed Sliders (Fatick context)
            if (sliderRepository.count() == 0) {
                SliderEntity s1 = new SliderEntity();
                s1.setTitre("Bienvenue au Championnat National Populaire (Navétane)");
                s1.setDescription("Saison des pluies, saison de la passion ! L'ODCAV de Fatick lance les grandes affiches.");
                s1.setImagePath("https://images.unsplash.com/photo-1518605368461-1ee7c532066d?q=80&w=1200&auto=format&fit=crop"); // Stadium crowd
                s1.setOrdre(1);
                sliderRepository.save(s1);

                SliderEntity s2 = new SliderEntity();
                s2.setTitre("Le Stade Massène Sène prêt à vibrer !");
                s2.setDescription("Les équipes locales se préparent pour une saison inoubliable.");
                s2.setImagePath("https://images.unsplash.com/photo-1510566337590-2fc1f21d0faa?q=80&w=1200&auto=format&fit=crop"); // Soccer ball on grass
                s2.setOrdre(2);
                sliderRepository.save(s2);
            }

            // Seed Social Networks
            if (socialNetworkRepository.count() == 0) {
                SocialNetworkEntity fb = new SocialNetworkEntity();
                fb.setPlateforme("Facebook");
                fb.setIcone("fab fa-facebook-f");
                fb.setLien("https://facebook.com/odcavfatick");
                socialNetworkRepository.save(fb);

                SocialNetworkEntity yt = new SocialNetworkEntity();
                yt.setPlateforme("YouTube");
                yt.setIcone("fab fa-youtube");
                yt.setLien("https://youtube.com");
                socialNetworkRepository.save(yt);
            }

            // Seed Actualites
            if (actualiteRepository.count() == 0) {
                ActualiteEntity a1 = new ActualiteEntity();
                a1.setTitre("Ouverture de la Saison Navétane à Fatick");
                a1.setContenu("<p>C'est reparti pour un tour ! Les stades de Fatick vibreront à nouveau au rythme des Navétanes cet été.</p>");
                a1.setImageCouverture("https://images.unsplash.com/photo-1522778119026-d647f0596c20?q=80&w=800&auto=format&fit=crop");
                a1.setType("ACTUALITE");
                actualiteRepository.save(a1);

                ActualiteEntity a2 = new ActualiteEntity();
                a2.setTitre("Réunion de Concertation CQRP");
                a2.setContenu("<p>La Commission de Qualification et des Règlements s'est réunie pour statuer sur les nouvelles directives de cette année.</p>");
                a2.setType("COMMUNIQUE");
                actualiteRepository.save(a2);
            }
        };
    }
}
