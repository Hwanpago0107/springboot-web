package me.ceskim493.springbootdeveloper.repository;

import me.ceskim493.springbootdeveloper.domain.ImgFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImgFileRepository extends JpaRepository<ImgFile, Long> {

    ImgFile findImgFileByFilePath(String filePath);
}
