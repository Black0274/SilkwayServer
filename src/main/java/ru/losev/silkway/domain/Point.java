package ru.losev.silkway.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hilo_sequence_generator")
//    @GenericGenerator(
//            name = "hilo_sequence_generator",
//            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
//            parameters = {
//                    @Parameter(name = "sequence_name", value = "hilo_seqeunce"),
//                    @Parameter(name = "initial_value", value = "1"),
//                    @Parameter(name = "increment_size", value = "1"),
//                    @Parameter(name = "optimizer", value = "hilo")
//            })
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Double lat;
    private Double lng;

    private Boolean marker;

    public Point(User user, Double lat, Double lng) {
        this.user = user;
        this.lat = lat;
        this.lng = lng;
    }
}
