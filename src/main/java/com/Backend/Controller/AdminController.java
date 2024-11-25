package com.Backend.Controller;

import com.Backend.Dto.AdminDto;
import com.Backend.Dto.LoginRequest;
import com.Backend.Entities.Admin;
import com.Backend.Service.AdminService;
import com.Backend.Utils.JwtUtil;
import com.Backend.Utils.PasswordChecker;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminController extends BaseController {

    @Autowired
    private final AdminService adminService;
    private final JwtUtil jwtUtil;
    @Autowired
    private final PasswordChecker passwordChecker;

    public AdminController(AdminService adminService, JwtUtil jwtUtil, PasswordChecker passwordChecker) {
        this.adminService = adminService;
        this.jwtUtil = jwtUtil;
        this.passwordChecker = passwordChecker;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        Optional<Admin> adminOptional = adminService.getAdminByUsername(loginRequest.getUsername());
        if (adminOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "Admin not found."));
        }

        Admin admin = adminOptional.get();

        if (!passwordChecker.checkPassword(loginRequest.getPassword(), admin.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("error", "Invalid Credentials"));
        }

        final UserDetails userDetails = new User(admin.getUsername(), admin.getPassword(), Collections.emptyList());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername()).toString();

        Cookie jwtCookie = new Cookie("jwtToken", jwt);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        Map<String, String> map = new HashMap<String, String>();
        map.put("jwtToken", jwt);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable Long id) {
        AdminDto admin = adminService.getAdminById(id);
        return ResponseEntity.ok(admin);
    }

    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.createAdmin(admin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin updatedAdmin) {
        Optional<Admin> updated = Optional.ofNullable(adminService.updateAdmin(id, updatedAdmin));
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        if (adminService.getAdminById(id) != null) {
            adminService.deleteAdmin(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
