package rmit.s3539519.madassignment1.model;

public abstract class AbstractTrackable implements Trackable {
    protected String id;
    protected String name;
    protected String description;
    protected String url;
    protected String category;
    protected String photo;

    public AbstractTrackable(String id, String name, String description, String url, String category, String photo) {
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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getPhoto() {
        return photo;
    }
}
