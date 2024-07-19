package com.example.shopgiayvip.Repository;

import com.example.shopgiayvip.Entity.GioHang;
import com.example.shopgiayvip.Entity.GioHangChiTiet;
import com.example.shopgiayvip.Entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GioHangChiTietRepo extends JpaRepository<GioHangChiTiet, Integer> {
    @Query(value = "select * from gio_hang_chi_tiet where id_gio_hang = ?2 and id_giay_chi_tiet = ?1 and trang_thai like '%true%'",nativeQuery = true)
    GioHangChiTiet sanPhamGioHang(Integer idSP, Integer id);

    @Query(value = "select * from gio_hang_chi_tiet where id = ?2 and id_giay_chi_tiet = ?1 and trang_thai like '%true%'",nativeQuery = true)
    GioHangChiTiet sanPhamCanUpdate(Integer idSP, Integer id);

    @Query(value = "select sum(tong_tien) from gio_hang_chi_tiet where id_gio_hang = ?1 and trang_thai like '%true%'",nativeQuery = true)
    Integer tongTien(Integer idGioHang);

    @Query(value = "select * from gio_hang_chi_tiet where id_gio_hang = ?1 and trang_thai like '%true%'",nativeQuery = true)
    List<GioHangChiTiet> listGioHangChiTiet(Integer idGioHang);

    List<GioHangChiTiet> findByHoaDon(HoaDon hoaDon);
}
