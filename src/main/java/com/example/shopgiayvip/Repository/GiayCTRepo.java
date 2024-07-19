package com.example.shopgiayvip.Repository;

import com.example.shopgiayvip.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GiayCTRepo extends JpaRepository<GiayChiTiet,Integer> {
    List<GiayChiTiet> findByTrangThai(String trangThai);
    GiayChiTiet findByTrangThaiAndHangGiayAndSizeGiayAndMauSacGiayAndTenGiay
                (String trangThai, HangGiay hangGiay, SizeGiay sizeGiay, MauSac mauSac, String tenGiay);
}
