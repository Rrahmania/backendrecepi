package com.rasanusantara.ourrecipe.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String region;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String category;
    private String difficulty;
    private String timeToCook;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @ElementCollection
    private List<String> ingredients;

    @ElementCollection
    @Column(columnDefinition = "TEXT")
    private List<String> steps;

    // Relasi: Banyak Resep bisa dibuat oleh 1 Pengguna
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private RegularUser author;

    // Getter dan Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getTimeToCook() { return timeToCook; }
    public void setTimeToCook(String timeToCook) { this.timeToCook = timeToCook; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<String> getIngredients() { return ingredients; }
    public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }

    public List<String> getSteps() { return steps; }
    public void setSteps(List<String> steps) { this.steps = steps; }

    public RegularUser getAuthor() { return author; }
    public void setAuthor(RegularUser author) { this.author = author; }
}