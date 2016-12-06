package com.company.Service;

import com.company.Domain.Post;

import com.company.Domain.Validator;
import com.company.Repository.CrudRepository;
import com.sun.istack.internal.NotNull;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by AlexandruD on 10/8/2016.
 */
public class PostService extends ObservableCrudService<Post> {

    public PostService(CrudRepository<Post> repo, Validator<Post> validator) {
        super(repo, validator);
    }

    /**
     * Gets all {@link Post} whose name contain the given substring
     * @param substring The substring
     * @return The {@link Post} of which match
     */
    public Iterable<Post> getPositionsBySubstring(String substring) {
        return get((post) -> (post.getName().contains(substring)));
    }

    /**
     * Gets all {@link Post} of the given {@link Post.Type}
     * @param type The type
     * @return The posts of the given {@link Post.Type}
     */
    public Iterable<Post> getPositionsByType(Post.Type type) {
        return get((post) -> (post.getType().equals(type)));
    }

    /**
     * Gets the {@link Post} sorted by their id
     * @return The sorted list
     */
    public Iterable<Post> getSortedByID() {
        return getSorted((a, b) -> (a.getId() - b.getId()));
    }

    /**
     * Used as a starting point for composite filtering. <br><br>
     * Example: <br>fin(filterType(Post.Type.FULLTIME)) <br>
     *     fin(filterSubstring('123',filterType(Post.Type.FULLTIME)))
     * @param type The type
     * @return A stream of the filtered elements
     */
    private Stream<Post> filterType(Optional<Post.Type> type) {

        Stream<Post> stream = StreamSupport.stream(getAll().spliterator(), false);

        if(!type.isPresent())
            return stream;

        return stream.filter((elem) -> elem.getType().equals(type.get()));
    }

    /**
     * Used as a starting point for composite filtering. <br><br>
     * Example: <br>fin(filterSubstring('123')) <br>
     *     fin(filterType(Post.Type.FULLTIME, filterSubstring('123')))
     * @param substring The substring
     * @return A stream of the filtered elements
     */
    private Stream<Post> filterSubstring(Optional<String> substring) {

        Stream<Post> stream = StreamSupport.stream(getAll().spliterator(), false);

        if(!substring.isPresent())
            return stream;

        return stream.filter((elem) -> elem.getName().contains(substring.get()));
    }

    /**
     * Used for composite filtering. Please use as argument of a fin call or another part of the composite filter.<br><br>
     * Example: fin(filterSubstring('123',filterType(Post.Type.FULLTIME)))
     * @param type The type
     * @param stream The intermediary stream
     * @return
     */
    private Stream<Post> filterType(Optional<Post.Type> type, @NotNull Stream<Post> stream) {

        if(!type.isPresent())
            return stream;

        return stream.filter((elem) -> (
                elem.getType().equals(type.get())
        ));
    }

    /**
     * Used in composite filtering. Please use as argument a fin call
     * @param substring The substring
     * @param stream The intermediary stream
     * @return
     */
    private Stream<Post> filterSubstring(Optional<String> substring, @NotNull  Stream<Post> stream) {

        if(!substring.isPresent())
            return stream;

        return stream.filter((elem) -> (
                elem.getName().contains(substring.get())
        ));
    }

    private Iterable<Post> fin(Stream<Post> stream) {
        return stream.collect(Collectors.toList());
    }

    public Iterable<Post> filter(Optional<Post.Type> type, Optional<String> substring) {
        return fin(filterSubstring(substring, filterType(type)));
    }

}
