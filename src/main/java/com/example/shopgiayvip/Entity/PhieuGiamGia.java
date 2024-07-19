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
@Table(name = "phieu_giam_gia")
public class PhieuGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_loai_phieu_giam_gia")
    private LoaiPGG loaiPhieu;
    @ManyToOne
    @JoinColumn(name = "id_phuong_thuc_thanh_toan")
    private PhuongThucThanhToan pTTT;
    @ManyToOne
    @JoinColumn(name = "id_nguoi_dung")
    private NguoiDung nguoiDung;
    @Column(name = "ten_loai_phieu")
    private String tenLoaiPhieu;
    @Column(name = "muc_giam_gia")
    private Integer mucGiamGia;
    @Column(name = "muc_gia_toi_thieu")
    private Integer mucGiaToiThieu;
    @Column(name = "so_luong")
    private Integer soLuong;
    @Column(name = "ngay_bat_dau")
    private String ngayBatDau;
    @Column(name = "ngay_ket_thuc")
    private String ngayKetThuc;
    @Column(name = "ngay_tao")
    private Date ngayTao;
    @Column(name = "trang_thai")
    private String trangThai;
}

