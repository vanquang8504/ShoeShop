package com.example.shopgiayvip.Controller;

import com.example.shopgiayvip.Entity.GioHang;
import com.example.shopgiayvip.Entity.NguoiDung;
import com.example.shopgiayvip.Entity.NguoiDungDetails;
import com.example.shopgiayvip.Entity.PhieuGiamGia;
import com.example.shopgiayvip.Repository.*;
import com.example.shopgiayvip.dto.total;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhieuGiamGiaController {
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

    @GetMapping("/listPGG")
    public ResponseEntity<List<PhieuGiamGia>> listPGG(){
        return ResponseEntity.ok(phieuGiamGiaRepo.findAll());
    }
    @GetMapping("/maGiamGia")
    public ResponseEntity<total> tienGiamGia(@RequestParam("idPGG") Integer idPgg, Authentication authentication){
        PhieuGiamGia phieuGiamGia = phieuGiamGiaRepo.getReferenceById(idPgg);
        NguoiDungDetails nguoiDungDetails = (NguoiDungDetails) authentication.getPrincipal();
        NguoiDung nguoiDung = nguoiDungDetails.getUser();
        GioHang gioHangHienTai = gioHangRepo.gioHangHienTai(2); //cần update sau khi xong
        if(phieuGiamGia.getMucGiaToiThieu() < gioHangChiTietRepo.tongTien(gioHangHienTai.getId())){
            return ResponseEntity.ok(totalRepo.tongTienCanTra(gioHangHienTai.getId(),idPgg));
        }else {
            return ResponseEntity.ok(new total());
        }
    }
}
