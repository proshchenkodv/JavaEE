package ru.geekbrains.rest;

import ru.geekbrains.service.CategoryRepr;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Local
@Path("/categories")
public interface CategoryServiceRs{
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void insert(CategoryRepr categoryRepr);

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void delete(@PathParam("id") long id);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    void update(CategoryRepr categoryRepr);

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    CategoryRepr findByIdRest(@PathParam("id") Long id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<CategoryRepr> findAll();

}
