package com.example.reachme.Model;

public class UserStories {
    public UserStories() {
    }

    public UserStories(String image, long storyAt) {
        this.image = image;
        this.storyAt = storyAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getStoryAt() {
        return storyAt;
    }

    public void setStoryAt(long storyAt) {
        this.storyAt = storyAt;
    }

    private String image;
    private long storyAt;
}
