package com.rasanusantara.ourrecipe.dto;

public class CommentResponse {
    private Long id;
    private Long recipeId;
    private String userId;
    private String userName;
    private String text;
    private int rating;
    private String date;

    public CommentResponse() {}

    public CommentResponse(Long id, Long recipeId, String userId, String userName, String text, int rating, String date) {
        this.id = id;
        this.recipeId = recipeId;
        this.userId = userId;
        this.userName = userName;
        this.text = text;
        this.rating = rating;
        this.date = date;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRecipeId() { return recipeId; }
    public void setRecipeId(Long recipeId) { this.recipeId = recipeId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
