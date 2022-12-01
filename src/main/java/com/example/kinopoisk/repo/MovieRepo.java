package com.example.kinopoisk.repo;

import com.example.kinopoisk.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepo extends JpaRepository<Movie, Long> {
    @Query(value="select * from movie m where m.name like %:keyword% or m.description like %:keyword%", nativeQuery = true)
    List<Movie> findByKeyword(@Param("keyword") String keyword);
}
