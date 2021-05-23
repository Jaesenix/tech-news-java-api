package com.technews.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    com.technews.repository.UserRepository repository;

    @Autowired
    com.technews.repository.VoteRepository voteRepository;

    @GetMapping("/api/users")
    public List<com.technews.model.User> getAllUsers() {
        List<com.technews.model.User> userList = repository.findAll();
        for (com.technews.model.User u : userList) {
            List<com.technews.model.Post> postList = u.getPosts();
            for (com.technews.model.Post p : postList) {
                p.setVoteCount(voteRepository.countVotesByPostId(p.getId()));
            }
        }
        return userList;
    }

    @GetMapping("/api/users/{id}")
    public com.technews.model.User getUserById(@PathVariable Integer id) {
        com.technews.model.User returnUser = repository.getOne(id);
        List<com.technews.model.Post> postList = returnUser.getPosts();
        for (com.technews.model.Post p : postList) {
            p.setVoteCount(voteRepository.countVotesByPostId(p.getId()));
        }

        return returnUser;
    }

    @PostMapping("/api/users")
    public com.technews.model.User addUser(@RequestBody com.technews.model.User user) {
        // Encrypt password
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        repository.save(user);
        return user;
    }

    @PutMapping("/api/users/{id}")
    public com.technews.model.User updateUser(@PathVariable int id, @RequestBody com.technews.model.User user) {
        com.technews.model.User tempUser = repository.getOne(id);

        if (!tempUser.equals(null)) {
            user.setId(tempUser.getId());
            repository.save(user);
        }
        return user;
    }

    @DeleteMapping("/api/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }
}
