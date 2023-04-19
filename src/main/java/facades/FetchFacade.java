package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ChuckDTO;
import dtos.CityDTO;
import dtos.DadDTO;
import dtos.RegionDTO;
import entities.Region;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class FetchFacade {

    private static FetchFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private FetchFacade() {
    }

//test
    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static FetchFacade getFetchFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FetchFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    public String fetchData(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json"); // Add this line
        try {
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());
                return response.toString();

            }
        } catch (Exception e) {
            System.out.println("Error in fetchData");
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
        return null;
    }

    public RegionDTO addRegion(RegionDTO regionDTO) {
        EntityManager em = emf.createEntityManager();
        Region r = new Region(regionDTO.getRegion());
        try {
            em.getTransaction().begin();
            em.persist(r);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new RegionDTO(r.getRegion());
    }

    public RegionDTO getRegion(String region) throws IOException {
        String json = fetchData("https://restcountries.eu/rest/v2/region/" + region);
        return createRegionDTO(json);
    }

    //This method creates a CityDTO object from a JSON string
    public CityDTO createCityDTO(String json) {
        return GSON.fromJson(json, CityDTO.class);
    }

    //This method creates a RegionDTO object from a JSON string
    public RegionDTO createRegionDTO(String json) {
        return GSON.fromJson(json, RegionDTO.class);
    }

}
