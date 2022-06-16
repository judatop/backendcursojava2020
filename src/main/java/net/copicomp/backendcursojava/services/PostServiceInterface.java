package net.copicomp.backendcursojava.services;

import java.util.List;

import net.copicomp.backendcursojava.shared.dto.PostCreationDto;
import net.copicomp.backendcursojava.shared.dto.PostDto;

public interface PostServiceInterface {

    public PostDto createPost(PostCreationDto post);

    public List<PostDto> getLastPosts();

    public PostDto getPost(String id);

    public void deletePost(String postId, long userId);

    public PostDto updatePost(String postId, long userId, PostCreationDto postCreationDto);
    
}
