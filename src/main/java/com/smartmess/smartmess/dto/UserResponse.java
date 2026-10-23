package com.smartmess.smartmess.dto;

import com.smartmess.smartmess.model.User;

public class UserResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String hostelRoom;
    private final String role;
    private final boolean approved;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.hostelRoom = user.getHostelRoom();
        this.role = user.getRole().name();
        this.approved = user.isApproved();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getHostelRoom() { return hostelRoom; }
    public String getRole() { return role; }
    public boolean isApproved() { return approved; }
}