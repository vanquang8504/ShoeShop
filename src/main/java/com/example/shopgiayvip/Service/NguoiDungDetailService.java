package com.example.shopgiayvip.Service;

import com.example.shopgiayvip.Entity.ChucVu;
import com.example.shopgiayvip.Entity.NguoiDung;
import com.example.shopgiayvip.Entity.NguoiDungDetails;
import com.example.shopgiayvip.Repository.NguoiDungRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class NguoiDungDetailService implements UserDetailsService {
    @Autowired
    NguoiDungRepo nguoiDungRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        NguoiDung nguoiDung = nguoiDungRepo.findByEnmail(email);
        if(nguoiDung == null){
            System.out.println("Người dùng không tồn tại: " + email);
            throw new UsernameNotFoundException("Người dùng không tồn tại: " + email);
        }
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(nguoiDung.getChucVu().getCv()));

        return new NguoiDungDetails(nguoiDung,grantedAuthorities);
    }
}
