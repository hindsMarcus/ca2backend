package dtos;

import entities.Region;

public class RegionDTO {

    String region;

    public RegionDTO() {
    }

    public RegionDTO(String region) {
        this.region = region;
    }

//    public RegionDTO(Region r) {
//        this.region = r.getRegion();
//    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
