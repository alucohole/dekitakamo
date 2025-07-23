package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.EmployeeInfo;

@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeInfo, Integer> {
    // 従業員IDで存在を確認するメソッド (CrudRepository.existsById()でも代替可能)
    // boolean existsByEmployeeId(Integer employeeId);
}