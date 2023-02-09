package org.example.repository;

import org.example.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

// Stub
public class PostRepository {

    private static PostRepository instance;
    private static List<Post> list;

    private PostRepository() {
        list = new CopyOnWriteArrayList<Post>();
    }

    public List<Post> all() {
        return this.list;
    }


    public Optional<Post> getById(long id) {
        for(int i = 0; i < list.size(); i++){
            if(this.list.get(i).getId() == id){
                return Optional.ofNullable(list.get(i));
            }
        }
        return Optional.empty();
    }


    public Post save(Post post) {
        this.list.add(post);
        return post;
    }


    public void removeById(long id) {
        Optional<Post> optionalPost = this.getById(id);
        if(optionalPost.isPresent()){
            list.remove(optionalPost.get());
        }
    }


    public static PostRepository getInstance() {
        if (instance == null) {
            instance = new PostRepository();
        }
        return instance;
    }
}
