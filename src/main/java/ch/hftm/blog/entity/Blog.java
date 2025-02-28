package ch.hftm.blog.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@MongoEntity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    @BsonId
    private ObjectId blogid;
    private String title;
    private String content;
    private boolean valid;
}
