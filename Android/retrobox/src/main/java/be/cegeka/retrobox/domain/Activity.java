package be.cegeka.retrobox.domain;

public class Activity {
    private String name;
    private String description;
    private String materials;
    private int durationMinutes;

    public Activity(String name, String description, String materials, int durationMinutes) {
        this.name = name;
        this.description = description;
        this.materials = materials;
        this.durationMinutes = durationMinutes;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getMaterials() {
        return materials;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }
}
