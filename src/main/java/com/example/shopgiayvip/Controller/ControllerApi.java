package com.example.shopgiayvip.Controller;

import com.example.shopgiayvip.Entity.*;
import com.example.shopgiayvip.Repository.*;
import com.example.shopgiayvip.dto.total;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class ControllerApi {

    @Autowired
    GiayCTRepo giayCTRepo;
    @Autowired
    GioHangRepo gioHangRepo;
    @Autowired
    PhieuGiamGiaRepo phieuGiamGiaRepo;
    @Autowired
    GioHangChiTietRepo gioHangChiTietRepo;
    @Autowired
    totalRepo totalRepo;
    @Autowired
    DiaChiRepo diaChiRepo;
    @Autowired
    PhuongThucThanhToanRepo phuongThucThanhToanRepo;
    @Autowired
    NguoiDungRepo nguoiDungRepo;
    @Autowired
    HoaDonRepo hoaDonRepo;


    @PostMapping ("/checkToAdd")
    public ResponseEntity<String> checkToAdd(Authentication authentication, @RequestParam("idSP") Integer id){
         GiayChiTiet giayChiTiet = giayCTRepo.getReferenceById(id);
        if(authentication != null){
            NguoiDungDetails nguoiDungDetails = (NguoiDungDetails) authentication.getPrincipal();
            NguoiDung nguoiDung = nguoiDungDetails.getUser();
            GioHang gioHangHienTai = gioHangRepo.gioHangHienTai(nguoiDung.getId());
            GioHangChiTiet sanPhamGioHang = gioHangChiTietRepo.sanPhamGioHang(id,gioHangHienTai.getId());
            System.out.println(gioHangHienTai.getId());
            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
            gioHangChiTiet.setGioHang(gioHangHienTai);
            gioHangChiTiet.setGiaBan(giayChiTiet.getGiaBan());
            gioHangChiTiet.setGiayCT(giayChiTiet);
            gioHangChiTiet.setTrangThai("true");
            if(sanPhamGioHang == null){
                gioHangChiTiet.setGiaBan(giayChiTiet.getGiaBan());
                gioHangChiTiet.setSoLuongMua(1);
                gioHangChiTiet.setTongTien(giayChiTiet.getGiaBan() * gioHangChiTiet.getSoLuongMua());
                gioHangChiTietRepo.save(gioHangChiTiet);
            }else {
                gioHangChiTiet.setId(sanPhamGioHang.getId());
                gioHangChiTiet.setSoLuongMua(sanPhamGioHang.getSoLuongMua() + 1);
                gioHangChiTiet.setTongTien(gioHangChiTiet.getGiaBan() * gioHangChiTiet.getSoLuongMua());
                gioHangChiTietRepo.save(gioHangChiTiet);
            }
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.ok("Fail");
    }
    @GetMapping("/cartt")
    public ResponseEntity<List<GioHangChiTiet>> cartPage(Authentication authentication){
        if (authentication != null){
            NguoiDungDetails nguoiDungDetails = (NguoiDungDetails) authentication.getPrincipal();
            NguoiDung nguoiDung = nguoiDungDetails.getUser();
            GioHang gioHangHienTai = gioHangRepo.gioHangHienTai(nguoiDung.getId());
            return ResponseEntity.ok(gioHangChiTietRepo.listGioHangChiTiet(2)); // cần update sau khi xong
        }
        return ResponseEntity.ok(null);
    }
    @GetMapping("/diaChiNguoiDung")
    public ResponseEntity<DiaChi> nguoiDungHienTai(Authentication authentication){
        if(authentication != null){
            NguoiDungDetails nguoiDungDetails = (NguoiDungDetails) authentication.getPrincipal();
            NguoiDung nguoiDung = nguoiDungDetails.getUser();
            return ResponseEntity.ok(diaChiRepo.diaChiNguoiDung(nguoiDung.getId()));
        }
        return ResponseEntity.ok(null);
    }
    @PostMapping("/tangSL")
    public ResponseEntity<GioHangChiTiet> tangSL(@RequestParam("idGH") Integer id,@RequestParam("idSP") Integer idSP,@RequestParam("roll") String roll, Authentication authentication){
        NguoiDungDetails nguoiDungDetails = (NguoiDungDetails) authentication.getPrincipal();
        NguoiDung nguoiDung = nguoiDungDetails.getUser();
        GioHang gioHangHienTai = gioHangRepo.gioHangHienTai(2); //cần update sau khi xong
        GioHangChiTiet sanPhamCanUpdate = gioHangChiTietRepo.sanPhamCanUpdate(idSP,id);
        if(roll.equals("tang") && sanPhamCanUpdate.getSoLuongMua() >= 1){
            sanPhamCanUpdate.setSoLuongMua(sanPhamCanUpdate.getSoLuongMua()+1);
            sanPhamCanUpdate.setTongTien(sanPhamCanUpdate.getGiaBan() * sanPhamCanUpdate.getSoLuongMua());
            gioHangChiTietRepo.save(sanPhamCanUpdate);
        }else {
            if(sanPhamCanUpdate.getSoLuongMua() > 1){
                sanPhamCanUpdate.setSoLuongMua(sanPhamCanUpdate.getSoLuongMua()-1);
                sanPhamCanUpdate.setTongTien(sanPhamCanUpdate.getGiaBan() * sanPhamCanUpdate.getSoLuongMua());
                gioHangChiTietRepo.save(sanPhamCanUpdate);
            }
        }
        return ResponseEntity.ok(sanPhamCanUpdate);
    }
    @GetMapping("/listPGG")
    public ResponseEntity<List<PhieuGiamGia>> listPGG(){
        return ResponseEntity.ok(phieuGiamGiaRepo.findAll());
    }
    @PostMapping("/maGiamGia")
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
    @GetMapping("/danhSachDiaChi")
    public ResponseEntity<List<DiaChi>> danhSachDiaChi(Authentication authentication){
        if(authentication != null){
            NguoiDungDetails nguoiDungDetails = (NguoiDungDetails) authentication.getPrincipal();
            NguoiDung nguoiDung = nguoiDungDetails.getUser();
            return ResponseEntity.ok(diaChiRepo.danhSachDiaChi(nguoiDung.getId()));
        }
        return ResponseEntity.ok(null);
    }
    @PostMapping("/chonDiaChi")
    public ResponseEntity<DiaChi> chonDiaChi(@RequestParam("idDC") Integer idDC){
        DiaChi diaChi = diaChiRepo.getReferenceById(idDC);
        return ResponseEntity.ok(diaChi);
    }
    @GetMapping("/tong-tien")
    public ResponseEntity<Integer> tongTien(Authentication authentication){
        if(authentication != null){
            NguoiDungDetails nguoiDungDetails = (NguoiDungDetails) authentication.getPrincipal();
            NguoiDung nguoiDung = nguoiDungDetails.getUser();
            return ResponseEntity.ok(gioHangChiTietRepo.tongTien(nguoiDung.getId()));
        }
        return ResponseEntity.ok(gioHangChiTietRepo.tongTien(-1)); // cần update sau khi xong
    }
    @PostMapping("/deleteGioHangCT")
    public ResponseEntity<String> deleteSanPhamGioHang(@RequestParam("idGHCT") Integer idGHCT){
        GioHangChiTiet gioHangChiTietCanXoa = gioHangChiTietRepo.getReferenceById(idGHCT);
        gioHangChiTietRepo.delete(gioHangChiTietCanXoa);
        return ResponseEntity.ok("success");
    }
    @PostMapping("/thanhToan")
    public ResponseEntity<?> thanhToan(HoaDon hoaDon,@RequestParam("idPGG") Integer idPGG,
                                            @RequestParam("idDC") Integer idDC,Authentication authentication){
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
    @PostMapping("/chuyenTrangThai")
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
    @GetMapping("/getGioHangChiTiet")
    public ResponseEntity<?> getGioHangChiTiet(@RequestParam("idHD") Integer id){
        HoaDon hoaDon = hoaDonRepo.getReferenceById(id);
        List<GioHangChiTiet> list = gioHangChiTietRepo.findByHoaDon(hoaDon);
        return ResponseEntity.ok(list);
    }
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
    @Autowired
    CountRepo countRepo;
    @GetMapping("/countBrand")
    public ResponseEntity<?> getCount(){
        return ResponseEntity.ok(countRepo.list());
    }
}
