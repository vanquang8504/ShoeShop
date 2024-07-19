package com.example.shopgiayvip.Repository;

import com.example.shopgiayvip.Entity.GioHangChiTiet;
import com.example.shopgiayvip.Entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoaDonRepo extends JpaRepository<HoaDon, Integer> {
    List<HoaDon> findByTrangThai(String trangThai);
}
