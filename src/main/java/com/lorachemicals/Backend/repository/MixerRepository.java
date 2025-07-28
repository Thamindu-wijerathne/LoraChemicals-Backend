package com.lorachemicals.Backend.repository;

import com.lorachemicals.Backend.model.Mixer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MixerRepository extends JpaRepository<Mixer, Long> {

}
