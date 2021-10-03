package com;

import com.model.UserDto;
import com.proxy.ClientProxy;
import io.smallrye.mutiny.Uni;
import io.vertx.core.net.impl.pool.Executor;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Path("/ws")
public class ws {



    @Inject
    @RestClient
    private ClientProxy proxy;


    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<UserDto> user(@PathParam("id") String id) {

        ExecutorService service = Executors.newWorkStealingPool();

        return proxy.getById(id)
                .onItem()
                .invoke(d -> service.submit(() -> run(d)))
                .emitOn(service)
                .invoke(() -> System.out.println("POR AQUI ENTRO ALGO MIJO 2"))
                .onFailure(r -> {
                    System.out.println(r.getMessage());
                    return true;
                })
                .recoverWithNull();
    }

    void run(UserDto user)  {
        try {

            Thread.sleep(5000);
            System.out.println("USER : "+user.getId());
        }catch (Exception e ){
            e.printStackTrace();
        }
    }


    @POST
    @Path("/save")
    public UserDto save(UserDto userDto){

        try {
            return proxy.save(userDto);
        }catch (Exception e){

            System.out.println(e.getMessage());
        }

        return null;
    }
}