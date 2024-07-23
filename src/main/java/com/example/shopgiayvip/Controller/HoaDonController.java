package com.example.shopgiayvip.Controller;

import com.example.shopgiayvip.Entity.*;
import com.example.shopgiayvip.Repository.*;
import com.example.shopgiayvip.dto.total;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class HoaDonController {
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

    @PostMapping("/thanhToan")
    public ResponseEntity<?> thanhToan(HoaDon hoaDon, @RequestParam("idPGG") Integer idPGG,
                                       @RequestParam("idDC") Integer idDC, Authentication authentication){
        PhieuGiamGia phieuGiamGiaDuocApDung;
        total total;

        NguoiDungDetails nguoiDungDetails = (NguoiDungDetails) authentication.getPrincipal();
        NguoiDung nguoiDungHienTai = nguoiDungDetails.getUser();


        NguoiDung nguoiDung = nguoiDungRepo.getReferenceById(nguoiDungHienTai.getId());
        HoaDon hoaDon1 = hoaDonRepo.save(hoaDon);

        GioHang gioHang = gioHangRepo.gioHangHienTai(nguoiDung.getId());
        Boolean phieuGiamGia = phieuGiamGiaRepo.existsById(idPGG);
        PhuongThucThanhToan phuongThucThanhToan = phuongThucThanhToanRepo.getReferenceById(3);
        DiaChi diaChi = diaChiRepo.getReferenceById(idDC);
        Integer tongTien = gioHangChiTietRepo.tongTien(gioHang.getId());
        if(phieuGiamGia){
            phieuGiamGiaDuocApDung = phieuGiamGiaRepo.getReferenceById(idPGG);
            total = totalRepo.tongTienCanTra(gioHang.getId(),phieuGiamGiaDuocApDung.getId());
            hoaDon1.setTienGiamGia(total.getMucGiamGia());
            hoaDon1.setConLai(total.getTongTien());
            phieuGiamGiaDuocApDung.setSoLuong(phieuGiamGiaDuocApDung.getSoLuong()-1);
        }else {
            phieuGiamGiaDuocApDung = null;
            hoaDon1.setTienGiamGia(0);
            hoaDon1.setConLai(tongTien);
        }
        hoaDon1.setKhachHang(nguoiDung);
        hoaDon1.setPhieuGiamGia(phieuGiamGiaDuocApDung);
        hoaDon1.setPTTT(phuongThucThanhToan);
        hoaDon1.setTenKhachHang(nguoiDung.getHoTen());
        hoaDon1.setSDT(nguoiDung.getSoDT());
        hoaDon1.setDiaChi(diaChi.getDc());
        hoaDon1.setTongTien(tongTien);
        hoaDon1.setTrangThai("Chờ xác nhận");
        hoaDon1.setNgayTao(new Date());


        List<GioHangChiTiet> list = gioHangChiTietRepo.listGioHangChiTiet(gioHang.getId());
        for(GioHangChiTiet gh : list){
            GiayChiTiet giayChiTiet = giayCTRepo.getReferenceById(gh.getGiayCT().getId());
            giayChiTiet.setSoLuongTon(giayChiTiet.getSoLuongTon() - gh.getSoLuongMua());
            gh.setTrangThai("false");
            gh.setHoaDon(hoaDon1);
            gioHangChiTietRepo.save(gh);
            giayCTRepo.save(giayChiTiet);
        }
        hoaDonRepo.save(hoaDon1);
        return ResponseEntity.ok("success");
    }
    @GetMapping("/admin/getListTrangThaiHoaDon")
    public ResponseEntity<?> donHangChoXacNhan(@RequestParam(value = "trangThai",defaultValue = "") String trangThai){
        return ResponseEntity.ok(hoaDonRepo.findByTrangThai(trangThai));
    }
    @PutMapping("/chuyenTrangThai")
    public ResponseEntity<?> chuyenTrangThaiHoaDon(@RequestParam("idHD") Integer idHD){
        HoaDon hoaDon = hoaDonRepo.getReferenceById(idHD);
        if(hoaDon.getTrangThai().equals("Chờ xác nhận")){
            hoaDon.setTrangThai("Đã xác nhận");
        } else if (hoaDon.getTrangThai().equals("Đã xác nhận")) {
            hoaDon.setTrangThai("Chờ giao hàng");
        }else if (hoaDon.getTrangThai().equals("Chờ giao hàng")) {
            hoaDon.setTrangThai("Đang giao hàng");
        }
        else if (hoaDon.getTrangThai().equals("Đang giao hàng")) {
            hoaDon.setTrangThai("Đã giao hàng");
        }
        hoaDonRepo.save(hoaDon);
        return ResponseEntity.ok("success");
    }
}
