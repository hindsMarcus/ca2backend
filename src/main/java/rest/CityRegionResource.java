package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.RegionDTO;
import entities.City;
import entities.Region;
import facades.FetchFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("city")
public class CityRegionResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final FetchFacade FACADE =  FetchFacade.getFetchFacade(EMF);


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cities")
    public String allCities() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<City> query = em.createQuery("select c from City c", City.class);
            List<City> cities = query.getResultList();
            return "[" + cities.size() + "]";
        } finally {
            em.close();
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("regions")
    public String allRegions() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Region> query = em.createQuery ("select r from Region r", Region.class);
            List<Region> regions = query.getResultList();
            return "[" + regions.size() + "]";
        } finally {
            em.close();
        }
    }


    @GET
    @Path("name/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRegion(@PathParam("name") String name) throws Exception {
        RegionDTO regionDTO = FACADE.createRegionDTO(FACADE.fetchData("https://restcountries.com/v3.1/capital/"+name+"?fields=region").replace("[","").replace("]",""));
        FACADE.addRegion(regionDTO);
        return Response.ok().entity(regionDTO).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("add")
    public Response createRegion(String input){
        RegionDTO rdto = GSON.fromJson(input, RegionDTO.class);
        RegionDTO rdtoNew = FACADE.addRegion(rdto);
        return Response.ok().entity(rdtoNew).build();
    }
}
