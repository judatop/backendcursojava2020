package net.copicomp.backendcursojava.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import net.copicomp.backendcursojava.shared.dto.PostDto;
import net.copicomp.backendcursojava.shared.dto.UserDto;

public interface UserServiceInterface extends UserDetailsService{
    

    public UserDto createUser(UserDto user);

    public UserDto getUser(String email);

    public List<PostDto> getUserPosts(String email);

}
