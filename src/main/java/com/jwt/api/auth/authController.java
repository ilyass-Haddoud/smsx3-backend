package com.jwt.api.auth;

import com.jwt.api.supplier.Supplier;
import com.jwt.api.supplier.SupplierLoginDTO;
import com.jwt.api.supplier.SupplierService;
import com.jwt.api.twoFactorAuthentication.TwoFactorAuthenticationService;
import com.jwt.api.user.User;
import com.jwt.api.user.UserLoginDTO;
import com.jwt.api.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class authController {
    private final UserService userService;
    private final SupplierService supplierService;
    private final TwoFactorAuthenticationService twoFactorAuthenticationService;

    @Autowired
    public authController(UserService userService, SupplierService supplierService, TwoFactorAuthenticationService twoFactorAuthenticationService) {
        this.userService = userService;
        this.supplierService = supplierService;
        this.twoFactorAuthenticationService = twoFactorAuthenticationService;
    }

    @CrossOrigin
    @PostMapping("login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO)
    {
        return this.userService.login(userLoginDTO.getEmail(),userLoginDTO.getPassword());
    }
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody User user)
    {
        User registredUser =  this.userService.register(user);
        return ResponseEntity.status(200).body(registredUser.toString());

    }

    @CrossOrigin
    @PostMapping("supplier/login")
    public ResponseEntity<String> loginSupplier(@Valid @RequestBody SupplierLoginDTO supplierLoginDTO, @RequestParam String otpCode)
    {
        return this.supplierService.login(supplierLoginDTO.getBpsaddeml(),supplierLoginDTO.getBpspasse(),otpCode);
    }

    @CrossOrigin
    @PostMapping("supplier/register")
    public ResponseEntity<String> registerSupplier(@RequestBody Supplier supplier) throws MessagingException {
        System.out.println(supplier.toString());
        Supplier registredSupplier =  this.supplierService.register(supplier);
        return ResponseEntity.ok(this.twoFactorAuthenticationService.generateQrCodeImageUri(registredSupplier.getSecret()));
    }
}
