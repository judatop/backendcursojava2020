package net.copicomp.backendcursojava.controllers;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.copicomp.backendcursojava.models.requests.UserDetailRequestModel;
import net.copicomp.backendcursojava.models.responses.PostRest;
import net.copicomp.backendcursojava.models.responses.UserRest;
import net.copicomp.backendcursojava.services.UserServiceInterface;
import net.copicomp.backendcursojava.shared.dto.PostDto;
import net.copicomp.backendcursojava.shared.dto.UserDto;

@RestController
@RequestMapping("/users") //localhost:8080/users
public class UserController {

    @Autowired
    UserServiceInterface userService;

    @Autowired
    ModelMapper mapper;

    //@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getPrincipal().toString();

        UserDto userDto = userService.getUser(email);

        UserRest userToReturn = mapper.map(userDto, UserRest.class);

        return userToReturn;

    }

    @PostMapping
    public UserRest createUser(@RequestBody @Valid UserDetailRequestModel userDetails){

        UserRest userToReturn = new UserRest();

        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);

        BeanUtils.copyProperties(createdUser, userToReturn);

        return userToReturn;
        
    }

    @GetMapping(path = "/posts") //localhost:8080/users/posts
    public List<PostRest> getPosts(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getPrincipal().toString();

        List<PostDto> posts = userService.getUserPosts(email);

        List<PostRest> postsRests = new ArrayList<>();

        for(PostDto post: posts){
            PostRest postRest = mapper.map(post, PostRest.class);
            if (postRest.getExpiresAt().compareTo(new Date(System.currentTimeMillis())) < 0){
                postRest.setExpired(true);
            }
            postsRests.add(postRest);
        }

        return postsRests;

    }

    
}
