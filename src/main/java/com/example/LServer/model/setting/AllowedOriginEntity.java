package com.example.LServer.model.setting;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "AllowedOriginEntity")
@Table(name = "allowed_origin", schema = "LCore")
public class AllowedOriginEntity {

    private Long id;
    private String origin;
    private Boolean use;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "origin")
    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Column(name = "use")
    public Boolean getUse() {
        return use;
    }
    public void setUse(Boolean use) {
        use = use;
    }
}
