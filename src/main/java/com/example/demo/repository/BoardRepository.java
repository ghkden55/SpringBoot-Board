package com.example.demo.repository;

import com.example.demo.entity.Board;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{



}
