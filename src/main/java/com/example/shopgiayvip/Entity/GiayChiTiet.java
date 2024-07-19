package com.example.shopgiayvip.Entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "chi_tiet_giay")
public class GiayChiTiet {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_giay")
    private Giay giay;
    @ManyToOne
    @JoinColumn(name = "id_size_giay")
    private SizeGiay sizeGiay;
    @ManyToOne
    @JoinColumn(name = "id_de_giay")
    private DeGiay deGiay;
    @ManyToOne
    @JoinColumn(name = "id_mau_sac_giay")
    private MauSac mauSacGiay;
    @ManyToOne
    @JoinColumn(name = "id_hang_giay")
    private HangGiay hangGiay;
    @Column(name = "ten_giay")
    private String tenGiay;
    @Column(name = "gia_ban")
    private Integer giaBan;
    @Column(name = "gia_giam")
    private Integer giaGiam;
    @Column(name = "gia_moi")
    private Integer giaMoi;
    @Column(name = "so_luong_ton")
    private Integer soLuongTon;
    @Column(name = "anh")
    private String anh;
    @Column(name = "trang_thai")
    private String trangThai;
    @Column(name = "ngay_tao")
    private Date ngayTao;
    @Column(name = "ngay_sua")
    private Date ngaySua;
}

