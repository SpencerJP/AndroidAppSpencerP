package rmit.s3539519.madassignment1.model;


public class FoodTruck implements Trackable {
    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String photo;

    public FoodTruck(String id, String name, String description, String url, String category, String photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return this.id + "|" + this.name + "|" + this.description + "|" + this.url + "|" + this.category;
    }
}
