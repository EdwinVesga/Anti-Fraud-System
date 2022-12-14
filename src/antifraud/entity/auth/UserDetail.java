package antifraud.entity.auth;

import antifraud.constant.UserAccountStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="user_detail")
@NoArgsConstructor
@Data
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_name", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name="status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserAccountStatus status;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "role_id")
    private UserRole role;
}
