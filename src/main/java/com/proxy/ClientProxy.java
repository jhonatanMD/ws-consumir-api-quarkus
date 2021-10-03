package com.proxy;


import com.model.UserDto;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/user")
@RegisterRestClient(configKey = "client-proxy")
public interface ClientProxy {

    @GET
    @Path("/findById/{id}")
    Uni<UserDto> getById(@PathParam String id);

    @POST
    @Path("/save")
    UserDto save(UserDto user);



}
