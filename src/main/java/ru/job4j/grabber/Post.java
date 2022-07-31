package ru.job4j.grabber;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {

    private int id;

    private String link;

    private String title;

    private String description;

    private LocalDateTime created;

    public Post(String link, String title, String description, LocalDateTime created) {
        this.link = link;
        this.title = title;
        this.description = description;
        this.created = created;
    }

    public Post(int id, String link, String title, String description, LocalDateTime created) {
        this.id = id;
        this.link = link;
        this.title = title;
        this.description = description;
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        Post post = (Post) o;
        return getId() == post.getId() && Objects.equals(getLink(), post.getLink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLink());
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", link='" + link + '\''
                + ", title='" + title + '\''
                + ", description='" + description + '\''
                + ", created=" + created
                + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
