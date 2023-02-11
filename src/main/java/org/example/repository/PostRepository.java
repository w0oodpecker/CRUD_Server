package org.example.repository;

import org.example.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


// Stub
public class PostRepository {

    private static PostRepository instance;
    private static Map<Long, Post> map;
    private static AtomicLong counterId; //счетчик постов


    private PostRepository() {
        map = new ConcurrentHashMap<>();
        counterId = new AtomicLong(0);
    }

    public List<Post> all() {
        return map.values().stream().toList();
    }


    public Optional<Post> getById(long id) {
        if(this.map.containsKey(id)){
            return Optional.ofNullable(map.get(id));
        }
        return Optional.empty();
    }


    public Post save(Post post) {
        if(post.getId() == 0){ //если id = 0, то регистрируем новый пост
            counterId.addAndGet(1);
            post.setId(counterId.get());
            map.put(counterId.get(),post);
        }
        else { //если id не равно 0, то проверяем наличие поста с таким номером
            if(this.getById(post.getId()).isPresent()){ //если пост обнаружен, то меняем содержимое содержания
                this.getById(post.getId()).get().setContent(post.getContent());
            }
            else{ //если не существует поста, то пишем как новый
                counterId.addAndGet(1);
                post.setId(counterId.get());
                map.put(counterId.get(), post);
            }
        }
        return post;
    }


    public void removeById(long id) {
        if(this.map.containsKey(id)){
            this.map.remove(id);
        }
    }


    public static PostRepository getInstance() {
        if (instance == null) {
            instance = new PostRepository();
        }
        return instance;
    }
}
