package ch.hftm.blog.repository;

import ch.hftm.blog.entity.Blog;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BlogRepository implements PanacheMongoRepository<Blog> {
}
