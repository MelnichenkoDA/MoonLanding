package com.example.demo.repository;



import com.example.demo.GameStats;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.StyledEditorKit;
import java.util.List;


public interface GameRepository extends JpaRepository<GameStats, Integer> {
    List<GameStats> findByFinishedOrderByFuel(int finished);
}
