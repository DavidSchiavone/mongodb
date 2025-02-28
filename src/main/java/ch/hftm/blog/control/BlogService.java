package ch.hftm.blog.control;

import ch.hftm.blog.control.dto.BlogDTO;
import ch.hftm.blog.control.mapper.BlogMapper;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.repository.BlogRepository;
import io.quarkus.logging.Log;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

@Dependent
public class BlogService {
    @Inject
    BlogRepository blogRepository;

    public List<Blog> getBlogs(Optional<String> searchStr) {
        PanacheQuery<Blog> blogQuery = searchStr
                .map(str -> blogRepository.find("title like ?1 or content like ?1", "%" + str + "%"))
                .orElse(blogRepository.findAll());
        List<Blog> blogs = blogQuery.list();
        Log.info("Returning " + blogs.size() + " blogs");
        return blogs;
    }

    public Blog getBlog(String id) {
        return blogRepository.findById(new ObjectId(id));
    }

    public Blog addBlog(BlogDTO blogDto) {
        Log.info("Adding blog " + blogDto.getTitle());
        Blog blog = BlogMapper.INSTANCE.map(blogDto);
        blogRepository.persist(blog);

        return blog;
    }

    public boolean updateBlog(String id, BlogDTO blog) {
        return Optional.ofNullable(getBlog(id))
                .map(blogToUpdate -> {
                    blogToUpdate.setTitle(blog.getTitle());
                    blogToUpdate.setContent(blog.getContent());
                    blogToUpdate.setValid(blog.isValid());

                    blogRepository.update(blogToUpdate);

                    return true;
                })
                .orElse(false);
    }

    public boolean deleteBlog(String id) {
        return blogRepository.deleteById(new ObjectId(id));
    }

    public List<Blog> findByTitle(String title) {
        return blogRepository.find("title", title)
                .stream()
                .toList();
    }
}
