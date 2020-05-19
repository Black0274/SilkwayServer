package ru.losev.silkway.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.losev.silkway.domain.Point;

import java.util.List;

@Repository
public interface PointRepo extends JpaRepository<Point, Long> {

    List<Point> findByUserId(Long userId);
}
