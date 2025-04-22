package com.opthema.hcm.java_microservice.application.service;

import com.opthema.hcm.java_microservice.domain.dto.AppUserDTO;
import com.opthema.hcm.java_microservice.domain.model.AppUser;
import com.opthema.hcm.java_microservice.infrastructure.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements  AuthService {
    @Autowired
    private AppUserRepository appUserRepository;  // UserRepository enjekte edilir
    @Autowired
    private JwtService jwtService;


    @Override
    public String authenticate(String username, String password) {
        // Kullanıcı adı ile kullanıcıyı veritabanında arıyoruz
        AppUser appUser = appUserRepository.findByUsername(username);

        // Eğer kullanıcı bulunursa ve şifre doğruysa
        if (appUser != null && appUser.getPassword().equals(password)) {
            // Kullanıcının daha önce oluşturulmuş bir token'ı var mı kontrol et (opsiyonel)

            try {
                return jwtService.getExistingToken(appUser.getId());  // Eğer mevcut token varsa, onu döndür.
            }
            catch (Exception e){
                // Yeni bir JWT token oluştur
                String token = jwtService.generateToken(appUser);

                // Token'ı kaydet (opsiyonel)
                jwtService.saveToken(appUser.getId(), token);
                return  token;
            }
        }
        // Hatalı kullanıcı adı veya şifre
        return ("Invalid username or password");
    }

    @Override
    public ResponseEntity<String> register(AppUserDTO userDTO) {
        // Kullanıcı adı zaten var mı kontrol et
        if (appUserRepository.existsByUsername(userDTO.getUsername())) {
            // Eğer kullanıcı adı mevcutsa hata mesajı döndür
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username already exists.");
        }

        // Yeni bir kullanıcı oluşturuluyor
        AppUser appUser = new AppUser();
        appUser.setUsername(userDTO.getUsername());
        appUser.setPassword(userDTO.getPassword()); // Parola güvenli bir şekilde hashlenmeli (örn. BCrypt)
        appUser.setEmail(userDTO.getEmail());

        try {
            // Veritabanına kaydediliyor
            appUserRepository.save(appUser);
            // Başarılı kayıt sonrası başarılı mesajı döndür
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User registered successfully.");
        } catch (Exception e) {
            // Hata durumunda genel bir hata mesajı döndür
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while registering the user.");
        }
    }
}
