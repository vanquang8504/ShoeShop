package com.example.shopgiayvip.Repository;

import com.example.shopgiayvip.Entity.PhieuGiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhieuGiamGiaRepo extends JpaRepository<PhieuGiamGia,Integer> {
    @Query(value = "select * from phieu_giam_gia where muc_gia_toi_thieu < (select sum(tong_tien) from gio_hang where id_nguoi_dung = 2)",nativeQuery = true)
    List<PhieuGiamGia> listPGG(Integer idUser);
}
