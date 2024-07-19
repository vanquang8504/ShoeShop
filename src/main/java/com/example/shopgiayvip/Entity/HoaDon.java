package com.example.shopgiayvip.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "hoa_don")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private NguoiDung khachHang;
    @ManyToOne
    @JoinColumn(name = "id_phieu_giam_gia")
    private PhieuGiamGia phieuGiamGia;
    @ManyToOne
    @JoinColumn(name = "id_phuong_thuc_thanh_toan")
    private PhuongThucThanhToan pTTT;
    @Column(name = "ten_khach_hang")
    private String tenKhachHang;
    @Column(name = "sdt_khach_hang")
    private String sDT;
    @Column(name = "dia_chi")
    private String diaChi;
    @Column(name = "tong_tien")
    private Integer tongTien;
    @Column(name = "tien_giam_gia")
    private Integer tienGiamGia;
    @Column(name = "total")
    private Integer conLai;
    @Column(name = "trang_thai")
    private String trangThai;
    @Column(name = "ngay_tao")
    private Date ngayTao;
    @Column(name = "ngay_sua")
    private Date ngaySua;
}

