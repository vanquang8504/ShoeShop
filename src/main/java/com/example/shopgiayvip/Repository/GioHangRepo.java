package com.example.shopgiayvip.Repository;

import com.example.shopgiayvip.Entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GioHangRepo extends JpaRepository<GioHang,Integer> {
    @Query(value = "select * from gio_hang where id_nguoi_dung = ?1", nativeQuery = true)
    GioHang gioHangHienTai(Integer idUser);


}
