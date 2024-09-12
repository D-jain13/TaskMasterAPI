package com.dhairya.taskMaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dhairya.taskMaster.entity.SupportingFile;

public interface SupportingDocumentRepo extends JpaRepository<SupportingFile, Long> {

}
