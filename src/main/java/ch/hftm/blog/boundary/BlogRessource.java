package ch.hftm.blog.boundary;

import ch.hftm.blog.control.BlogService;
import ch.hftm.blog.control.dto.BlogDTO;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.messaging.ValidationRequest;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Produces("application/json")
@Consumes("application/json")
@Path("blogs")
public class BlogRessource {
    @Inject
    BlogService blogService;

    @GET
    public List<Blog> getBlogs(@QueryParam("searchStr") Optional<String> searchStr) {
        return blogService.getBlogs(searchStr);
    }

    @Path("{id}")
    @GET
    public Response getBlog(@PathParam("id") String id) {
        return Optional.ofNullable(blogService.getBlog(id))
                .map(blog -> Response.ok(blog)
                        .build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .build());
    }


    @POST
    public void addBlog(@Valid BlogDTO blog) {
        blogService.addBlog(blog);
    }


    @Path("{id}")
    @PUT
    public Response updateBlog(@PathParam("id") String id, @Valid BlogDTO blog) {
        if (blogService.updateBlog(id, blog)) {
            return Response.ok()
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @Path("{id}")
    @DELETE
    public Response deleteBlog(@PathParam("id") String id) {
        if (blogService.deleteBlog(id)) {
            return Response.ok()
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }
}
