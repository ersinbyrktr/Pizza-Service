package rest.pojos;

public class Location {
    private String location;

    public Location(String location) {
        if (location == null)
            this.location = null;
        else
            this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
