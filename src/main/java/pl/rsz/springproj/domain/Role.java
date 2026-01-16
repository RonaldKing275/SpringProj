package pl.rsz.springproj.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // np. "ROLE_USER", "ROLE_ADMIN"

    public Role(String name) { this.name = name; }
}