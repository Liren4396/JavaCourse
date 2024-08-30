package unsw.piazza;

import java.util.ArrayList;
import java.util.List;

/**
 * A Piazza Forum
 *
 * @author Your Name
 */
public class PiazzaForum {
    List<Thread> a;
    private String className;

    /**
     * Initialises the new PiazzaForum with the given group name
     */
    public PiazzaForum(String className) {
        this.className = className;
    }

    /**
     * @return The name of the forum
     */
    public String getName() {
        return this.className;
    }

    /**
     * Sets the name of the group of the Forum
     * @param name
     */
    public void setName(String name) {
        this.className = name;
    }

    /**
     * Returns a list of Threads in the Forum, in the order that they were published
     */
    public List<Thread> getThreads() {
        return this.a;
    }

    /**
     * Creates a new thread with the given title and adds it to the Forum.
     * The content is provided to allow you to create the first Post.
     * Threads are stored in the order that they are published.
     * Returns the new Thread object
     * @param title
     * @param content
     */
    public Thread publish(String title, String content) {
        return new Thread(title, content);
    }

    /**
     * Searches all forum Threads for any that contain the given tag.
     * Returns a list of all matching Thread objects in the order that they were published.
     * @param tag
     * @return
     */
    public List<Thread> searchByTag(String tag) {
        List<Thread> ret = new ArrayList<>();
        for (Thread thread1 : a) {
            List<String> tags = thread1.getTags();
            for (String tmp : tags) {
                if (tmp == tag) {
                    ret.add(thread1);
                    break;
                }
            }
        }
        return ret;
    }

}
