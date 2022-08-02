package org.acme;

import org.acme.model.Episode;
import org.acme.model.TvSerie;
import org.acme.proxy.EpisodeProxy;
import org.acme.proxy.TvSeriesProxy;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/tvseries")
public class TvSeriesResource {

    @RestClient
    TvSeriesProxy proxy;

    @RestClient
    EpisodeProxy episodeProxy;

    private List<TvSerie> tvSeries = new ArrayList();


    // http://localhost:8080/tvseries?title=game%20of%20thrones
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("title") String title){
        TvSerie tvSerie = getTvSeries(title);
        tvSeries.add(tvSerie);
        return  Response.ok(tvSeries).build();
    }

    @Fallback(fallbackMethod = "fallbackGet")
    public TvSerie getTvSeries(String title){
        return proxy.get(title);
    }

    //http://localhost:8080/tvseries/episodes?title=game%20of%20thrones
    @GET
    @Path("/episodes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEpisode(@QueryParam("title") String title){
        TvSerie tvSerie = getTvSeries(title);
        List<Episode> episodes = getEpisodes(tvSerie.getId());
        tvSeries.add(tvSerie);
        return  Response.ok(episodes).build();
    }

    @Fallback(fallbackMethod = "fallbackGetEpisodes")
    public List<Episode> getEpisodes(Long id){
        return episodeProxy.get(id);
    }

    private List<Episode> fallbackGetEpisodes(Long id){
        return new ArrayList<>();
    }

    private TvSerie fallbackGet(String title){
        TvSerie tvSerie = new TvSerie();
        tvSerie.setId(1L);
        return  tvSerie;
    }
}
