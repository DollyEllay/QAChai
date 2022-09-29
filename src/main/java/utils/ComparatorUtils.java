package utils;

import com.google.common.collect.Ordering;
import models.Post;

public class ComparatorUtils {

    public static Ordering<Post> getPostComparatorById() {
        return new Ordering<Post>() {
            @Override
            public int compare(Post post1, Post post2) {
                return Integer.compare(post1.id, post2.id);
            }
        };
    }
}
