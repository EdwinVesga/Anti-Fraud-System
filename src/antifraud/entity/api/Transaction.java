package antifraud.entity.api;

import antifraud.constant.RegionCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@NoArgsConstructor
@Data
@ToString
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "number")
    private String number;

    @Column(name = "ip")
    private String ip;

    @Column(name = "region")
    private String region;

    @Column(name = "date")
    private LocalDateTime date;
}
