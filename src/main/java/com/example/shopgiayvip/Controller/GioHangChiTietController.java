package com.example.shopgiayvip.Controller;

import com.example.shopgiayvip.Entity.GioHangChiTiet;
import com.example.shopgiayvip.Entity.HoaDon;
import com.example.shopgiayvip.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class GioHangChiTietController {
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

    @GetMapping("/getGioHangChiTiet")
    public ResponseEntity<?> getGioHangChiTiet(@RequestParam("idHD") Integer id){
        HoaDon hoaDon = hoaDonRepo.getReferenceById(id);
        List<GioHangChiTiet> list = gioHangChiTietRepo.findByHoaDon(hoaDon);
        return ResponseEntity.ok(list);
    }
}
