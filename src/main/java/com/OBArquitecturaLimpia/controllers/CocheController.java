package com.OBArquitecturaLimpia.controllers;


import java.net.URI;
import java.util.ArrayList;


import org.springframework.stereotype.Component;


import com.OBArquitecturaLimpia.entities.Coche.Coche;
import com.OBArquitecturaLimpia.exceptions.Coche.CocheException;
import com.OBArquitecturaLimpia.exceptions.Coche.CocheNoExisteException;
import com.OBArquitecturaLimpia.services.Coche.CocheServices;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Component
@Path("/Coche/")
public class CocheController {
    private final CocheServices<Coche> cocheServices;

    public CocheController() {
        this.cocheServices = new CocheServices<>(Coche.class);
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Coche> listar() { return cocheServices.listar(); }

    @GET
    @Path("/{idCoche}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtener(@PathParam("idCoche") String idCoche) {
        try {
            return Response.ok(cocheServices.obtener(idCoche)).build();
        } catch (CocheNoExisteException e) {
            return Response.status(404).build();
        } catch (CocheException e) {
            return Response.status(400).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Coche coche) {
        try {
            cocheServices.crear(coche);

            return Response.created(
                URI.create("/Bootcamper/" + coche.getIdCoche())
            ).build();
        } catch (CocheException e) {
            return Response.status(400).build();
        }
    }

    @DELETE
    @Path("/{idCoche}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@PathParam("idCoche") String idCoche) {
        try {
            cocheServices.borrar(idCoche);

            return Response.ok().build();
        } catch (CocheException e) {
            return Response.status(400).build();
        }
    }
}
