package com.psych.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psych.game.model.Admin;

public interface AdminRepository  extends JpaRepository<Admin, Long>{

}
