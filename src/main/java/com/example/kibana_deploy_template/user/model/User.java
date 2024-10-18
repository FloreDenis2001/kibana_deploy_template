package com.example.kibana_deploy_template.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity(name = "user")
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class User  implements Comparable<User> , Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String name;
    private String gender;
    private String email;
    private String password;

    @Override
    public int compareTo(User o) {
        return 0;
    }
}
