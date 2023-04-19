package dtos;

public class CityDTO {

    private String name;
    private String country;
    private int population;
    private double latitude;
    private double longitude;


    public CityDTO() {
    }

    public CityDTO(String name) {
        this.name = name;
    }

    public CityDTO(String name, String country, int population, double latitude, double longitude) {
        this.name = name;
        this.country = country;
        this.population = population;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}


