package antifraud.entity;

import antifraud.constant.UserRoleType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_rol")
@NoArgsConstructor
@Data
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @Enumerated(value = EnumType.STRING)
    private UserRoleType name;

}
