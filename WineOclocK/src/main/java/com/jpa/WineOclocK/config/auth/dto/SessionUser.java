package com.jpa.WineOclocK.config.auth.dto;

import com.jpa.WineOclocK.domain.entity.User;

public class SessionUser {

    private final String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getUsername();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

}
