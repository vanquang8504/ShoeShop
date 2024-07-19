package com.example.shopgiayvip.Repository;

import com.example.shopgiayvip.Entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiaChiRepo extends JpaRepository<DiaChi,Integer> {
    @Query(value = "select * from dia_chi where id_nguoi_dung = ?1",nativeQuery = true)
    List<DiaChi> danhSachDiaChi(Integer idNguoiDung);
    @Query(value = "select top 1 * from dia_chi where id_nguoi_dung = ?1",nativeQuery = true)
    DiaChi diaChiNguoiDung(Integer idNguoiDung);
}
