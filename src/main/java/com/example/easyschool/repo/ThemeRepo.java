package com.example.easyschool.repo;

import com.example.easyschool.domain.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepo  extends JpaRepository<Theme, Long> {
}
