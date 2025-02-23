package com.project.audiobook.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class FileStorageService {
    private final Path rootLocation = Paths.get("uploads").toAbsolutePath().normalize();
    private final Path avatarLocation = rootLocation.resolve("avatars");

    public FileStorageService() {
        try {
            Files.createDirectories(rootLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the root upload directory.", ex);
        }
    }

    // Chuẩn hóa title để tránh lỗi khi tạo thư mục
    private String normalizeTitle(String title) {
        String temp = Normalizer.normalize(title, Normalizer.Form.NFD);
        return Pattern.compile("[^\\w\\s-]").matcher(temp).replaceAll("").replaceAll("\\s+", "_");
    }

    public String storeFile(MultipartFile file, String title, boolean isImage) {
        // Chuẩn hóa tên thư mục audiobook
        String safeTitle = normalizeTitle(title);

        // Xác định thư mục lưu file trong thư mục uploads
        Path targetDir = rootLocation.resolve("audiobooks").resolve(safeTitle).resolve(isImage ? "image" : "audio");

        try {
            Files.createDirectories(targetDir); // Tạo thư mục nếu chưa có
        } catch (IOException ex) {
            throw new RuntimeException("Không thể tạo thư mục cho audiobook: " + safeTitle, ex);
        }

        // Tạo tên file duy nhất
        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID() + fileExtension;

        Path targetLocation = targetDir.resolve(fileName);

        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return "audiobooks/" + safeTitle + "/" + (isImage ? "image" : "audio") + "/" + fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Không thể lưu file " + fileName + ". Vui lòng thử lại!", ex);
        }
    }

    public String storeAvatar(MultipartFile file) throws IOException {
        Files.createDirectories(avatarLocation);
        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = UUID.randomUUID() + fileExtension; // Đặt tên file duy nhất
        Path targetLocation = avatarLocation.resolve(fileName);

        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName; // Trả về tên file
        } catch (IOException ex) {
            throw new RuntimeException("Không thể lưu avatar. Vui lòng thử lại!", ex);
        }
    }

}
