package com.leviis.realworldexample.infrastructure;

import com.leviis.realworldexample.user.domain.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserContext implements UserDetails {
    private Long id;
    private String email;
    private String username;
    private String bio;
    private String image;
    private String password;
    private List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("USER"));

    public static UserContext from(final User user) {
        return UserContext.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .password(user.getPassword())
                .build();
    }
}
