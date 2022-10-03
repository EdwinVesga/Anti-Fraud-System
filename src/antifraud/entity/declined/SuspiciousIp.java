package antifraud.entity.declined;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "suspicious_ip")
@NoArgsConstructor
@Data
public class SuspiciousIp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "ip")
    private String ipAddress;
}
