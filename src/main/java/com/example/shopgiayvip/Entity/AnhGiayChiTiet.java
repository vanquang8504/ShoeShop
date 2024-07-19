package com.example.shopgiayvip.Entity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "anh_giay_chi_tiet")
public class AnhGiayChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_giay_chi_tiet")
    private GiayChiTiet giayChiTiet;
    @Column(name = "hinh_anh")
    private Integer hinhAnh;
}
