package io.javabrains.moviecatalogservice.resource;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        // get all rated movie ids- for each movie id, call info service and get details- put them all together
        RestTemplate restTemplate = new RestTemplate();
        List<Rating> ratings= Arrays.asList(
               new Rating("123",9),
               new Rating("345",8)
       );
       return ratings.stream().map(
               rating -> {
                   Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);

                   return new CatalogItem(movie.getName(), movie.getMovieId(), rating.getRating());
               })
        .collect(Collectors.toList());

    }
}
