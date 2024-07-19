package com.example.shopgiayvip.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "gio_hang_chi_tiet")
public class GioHangChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_giay_chi_tiet")
    private GiayChiTiet giayCT;
    @ManyToOne
    @JoinColumn(name = "id_gio_hang")
    private GioHang gioHang;
    @ManyToOne
    @JoinColumn(name = "id_hoa_don")
    private HoaDon hoaDon;
    @Column(name = "gia_ban")
    private Integer giaBan;
    @Column(name = "so_luong_mua")
    private Integer soLuongMua;
    @Column(name = "tong_tien")
    private Integer tongTien;
    @Column(name = "trang_thai")
    private String trangThai;
}
