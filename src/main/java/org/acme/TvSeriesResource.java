package org.acme;

import org.acme.model.Episode;
import org.acme.model.TvSerie;
import org.acme.proxy.EpisodeProxy;
import org.acme.proxy.TvSeriesProxy;
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

    /*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("title") String title){
        TvSerie tvSerie = proxy.get(title);
        tvSeries.add(tvSerie);
        return  Response.ok(tvSeries).build();
    }
     */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("title") String title){
        TvSerie tvSerie = proxy.get(title);
        List<Episode> episodes = episodeProxy.get(tvSerie.getId());
        return  Response.ok(episodes).build();
    }
}
