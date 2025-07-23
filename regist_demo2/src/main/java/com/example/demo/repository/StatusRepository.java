package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Status;

@Repository
public interface StatusRepository extends CrudRepository<Status, Integer> {
}