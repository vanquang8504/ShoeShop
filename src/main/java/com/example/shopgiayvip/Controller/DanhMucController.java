package com.example.shopgiayvip.Controller;

import com.example.shopgiayvip.Entity.GioHang;
import com.example.shopgiayvip.Entity.NguoiDung;
import com.example.shopgiayvip.Entity.NguoiDungDetails;
import com.example.shopgiayvip.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DanhMucController {
    @Autowired
    GiayCTRepo giayCTRepo;
    @Autowired
    GioHangRepo gioHangRepo;
    @Autowired
    PhieuGiamGiaRepo phieuGiamGiaRepo;
    @Autowired
    GioHangChiTietRepo gioHangChiTietRepo;
    @Autowired
    com.example.shopgiayvip.Repository.totalRepo totalRepo;
    @Autowired
    DiaChiRepo diaChiRepo;
    @Autowired
    PhuongThucThanhToanRepo phuongThucThanhToanRepo;
    @Autowired
    NguoiDungRepo nguoiDungRepo;
    @Autowired
    HoaDonRepo hoaDonRepo;

    @GetMapping("/countCategory")
    public ResponseEntity<?> count(Authentication authentication){
        if (authentication != null){
            NguoiDungDetails nguoiDungDetails = (NguoiDungDetails) authentication.getPrincipal();
            NguoiDung nguoiDung = nguoiDungDetails.getUser();
            GioHang gioHangHienTai = gioHangRepo.gioHangHienTai(nguoiDung.getId());

            Integer size = gioHangChiTietRepo.findByGioHangAndTrangThai(gioHangHienTai,"true").size();
            return ResponseEntity.ok(size);
        }
        return ResponseEntity.ok(0);
    }
}
