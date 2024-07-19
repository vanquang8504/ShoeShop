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
@Table(name = "nguoi_dung")
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_chuc_vu")
    private ChucVu chucVu;
    @Column(name = "ho_ten")
    private String hoTen;
    @Column(name = "email")
    private String enmail;
    @Column(name = "mat_khau")
    private String matKhau;
    @Column(name = "so_dien_thoai")
    private String soDT;
    @Column(name = "anh")
    private String anh;
    @Column(name = "trang_thai")
    private Boolean trangThai;
    @Column(name = "ngay_tao")
    private Date ngayTao;
    @Column(name = "ngay_sua")
    private Date ngaySua;

}

