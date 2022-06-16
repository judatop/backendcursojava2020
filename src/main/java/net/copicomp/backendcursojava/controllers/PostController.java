package net.copicomp.backendcursojava.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.copicomp.backendcursojava.models.requests.PostCreateRequestModel;
import net.copicomp.backendcursojava.models.responses.OperationStatusModel;
import net.copicomp.backendcursojava.models.responses.PostRest;
import net.copicomp.backendcursojava.services.PostServiceInterface;
import net.copicomp.backendcursojava.services.UserServiceInterface;
import net.copicomp.backendcursojava.shared.dto.PostCreationDto;
import net.copicomp.backendcursojava.shared.dto.PostDto;
import net.copicomp.backendcursojava.shared.dto.UserDto;

@RestController
@RequestMapping("/posts") //localhost:8080/posts
public class PostController {

    @Autowired
    PostServiceInterface postServiceInterface;

    @Autowired
    UserServiceInterface userServiceInterface;

    @Autowired
    ModelMapper mapper;
    

    @PostMapping
    public PostRest createPost(@RequestBody PostCreateRequestModel createRequestModel){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getPrincipal().toString();

        PostCreationDto postCreationDto = mapper.map(createRequestModel, PostCreationDto.class);

        postCreationDto.setUserEmail(email);

        PostDto postDto = postServiceInterface.createPost(postCreationDto);

        PostRest postToReturn = mapper.map(postDto, PostRest.class);

        if (postToReturn.getExpiresAt().compareTo(new Date(System.currentTimeMillis())) < 0){
            postToReturn.setExpired(true);
        }

        return postToReturn;
    }


    @GetMapping(path = "/last") //localhost:8080/posts/last
    public List<PostRest> lastPosts(){


        List<PostDto> posts = postServiceInterface.getLastPosts();

        List<PostRest> postsRests = new ArrayList<>();

        for(PostDto post: posts){
            PostRest postRest = mapper.map(post, PostRest.class);
            postsRests.add(postRest);
        }

        return postsRests;
    }


    @GetMapping(path = "/{id}") //localhost:8080/posts/uuid
    public PostRest getPost(@PathVariable String id){

        PostDto postDto = postServiceInterface.getPost(id);

        PostRest postRest = mapper.map(postDto, PostRest.class);

        if (postRest.getExpiresAt().compareTo(new Date(System.currentTimeMillis())) < 0){
            postRest.setExpired(true);
        }

        // Validamos si el post es privado o si el post ya expiro
        if (postRest.getExposure().getId() == 1 || postRest.getExpired()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserDto user = userServiceInterface.getUser(authentication.getPrincipal().toString());

            if (user.getId() != postDto.getUser().getId()){
                throw new RuntimeException("No tienes permisos para realizar esta acción");
            }

        }

        return postRest;
    }

    @DeleteMapping(path="/{id}")
    public OperationStatusModel deletePost(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDto user = userServiceInterface.getUser(authentication.getPrincipal().toString());

        OperationStatusModel operationStatusModel = new OperationStatusModel();

        operationStatusModel.setName("DELETE");

        postServiceInterface.deletePost(id, user.getId());

        operationStatusModel.setResult("SUCCESS");

        return operationStatusModel;
    }

    @PutMapping(path="/{id}")
    public PostRest updatePost(@RequestBody PostCreateRequestModel postCreateRequestModel, @PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDto user = userServiceInterface.getUser(authentication.getPrincipal().toString());

        PostCreationDto postUpdateDto = mapper.map(postCreateRequestModel, PostCreationDto.class);

        PostDto postDto = postServiceInterface.updatePost(id, user.getId(), postUpdateDto);

        PostRest updatedPost = mapper.map(postDto, PostRest.class);

        return updatedPost;
    }


}
