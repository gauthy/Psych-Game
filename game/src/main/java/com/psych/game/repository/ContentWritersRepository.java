package com.psych.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psych.game.model.ContentWriter;

@Repository
public interface ContentWritersRepository extends JpaRepository<ContentWriter, Long> {

}
