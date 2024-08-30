package unsw.piazza;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * A thread in the Piazza forum.
 */
public class Thread {
    private String title;
    private List<String> post;
    private List<String> tags;

    /**
     * Creates a new thread with a title and an initial first post.
     * @param title
     * @param firstPost
     */
    public Thread(String title, String firstPost) {
        this.title = title;
        this.post = new ArrayList<>();
        this.post.add(firstPost);
    }

    /**
     * @return The title of the thread
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @return A SORTED list of tags
     */
    public List<String> getTags() {
        return this.tags;
    }


    /**
     * @return A list of posts in this thread, in the order that they were published
     */
    public List<String> getPosts() {
        return this.post;
    }

    /**
     * Adds the given post object into the list of posts in the thread.
     * @param post
     */
    public void publishPost(String post) {
        this.post.add(post);
    }

    /**
     * Allows the given user to replace the thread tags (list of strings)
     * @param tags
     */
    public void setTags(String[] tags) {
        for (String tag : tags) {
            this.tags.add(tag);
        }
    }
}
