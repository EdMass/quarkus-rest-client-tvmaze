package org.acme.proxy;

import org.acme.model.TvSerie;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.awt.*;

@Path("/singlesearch")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient()
public interface TvSeriesProxy {

    @GET
    @Path("/shows")
    TvSerie get(@QueryParam("q") String title);
}
