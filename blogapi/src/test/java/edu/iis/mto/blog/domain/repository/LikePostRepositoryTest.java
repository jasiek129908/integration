package edu.iis.mto.blog.domain.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
class LikePostRepositoryTest {

    @Autowired
    private LikePostRepository likePostRepository;

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private User author;
    private LikePost likePost;
    private BlogPost blogPost;

    @BeforeEach
    void setUp() {
        author = new User();
        author.setFirstName("Jan");
        author.setLastName("Kowalski");
        author.setEmail("john@domain.com");
        author.setAccountStatus(AccountStatus.NEW);

        user = new User();
        user.setFirstName("Adam");
        user.setLastName("Nawiedzony");
        user.setEmail("kryptonim@domain.com");
        user.setAccountStatus(AccountStatus.NEW);

        userRepository.save(user);
        userRepository.save(author);

        blogPost = new BlogPost();
        blogPost.setUser(author);
        blogPost.setEntry("content");

        LikePost likePost = new LikePost();
        likePost.setPost(blogPost);
        likePost.setUser(user);

        blogPost.setLikes(new ArrayList<LikePost>() {{
            add(likePost);
        }});

        blogPostRepository.save(blogPost);
        likePostRepository.save(likePost);
    }

    @Test
    void shouldFindLikePost() {
        Optional<LikePost> likePostOptional = likePostRepository.findByUserAndPost(user, blogPost);
        assertThat(likePostOptional.isPresent(), equalTo(true));
    }

    @Test
    void blogPostShouldReturnOneLikesCount() {
        List<BlogPost> blogPosts = blogPostRepository.findByUser(author);
        assertThat(blogPosts.get(0).getLikesCount(), equalTo(1));
    }

    @Test
    void blogPostShouldBeLikedBeExactUser() {
        List<BlogPost> blogPosts = blogPostRepository.findByUser(author);
        assertThat(blogPosts.get(0).getLikes().get(0).getUser(), equalTo(user));
    }

    @Test
    void blogPostShouldShouldHaveSetProperEntry() {
        List<BlogPost> blogPosts = blogPostRepository.findByUser(author);
        assertThat(blogPosts.get(0).getEntry(), equalTo("content"));
    }

    @Test
    void blogPostShouldBeLikedByTwoUsers(){
        User user2 = new User();
        user2.setFirstName("Ala");
        user2.setLastName("Budzynska");
        user2.setEmail("ala@domain.com");
        user2.setAccountStatus(AccountStatus.NEW);

        userRepository.save(user2);

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setUser(author);
        blogPost2.setEntry("content");

        LikePost likePost2 = new LikePost();
        likePost2.setPost(blogPost2);
        likePost2.setUser(user2);

        LikePost likePost3 = new LikePost();
        likePost3.setPost(blogPost2);
        likePost3.setUser(user);

        blogPost2.setLikes(new ArrayList<LikePost>() {{
            add(likePost2);
            add(likePost3);
        }});

        blogPostRepository.save(blogPost2);
        likePostRepository.save(likePost2);
        likePostRepository.save(likePost3);

        List<BlogPost> blogPosts = blogPostRepository.findByUser(author);
        assertThat(blogPosts.get(1).getLikesCount(), equalTo(2));
    }
}