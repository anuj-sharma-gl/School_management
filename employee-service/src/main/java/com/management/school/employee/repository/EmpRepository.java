package com.management.school.employee.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.management.school.employee.bean.UserEntity;
import com.management.school.employee.bean.enums.EmpType;

@Repository
@Transactional
public interface EmpRepository extends PagingAndSortingRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);

	List<UserEntity> findByEmpType(EmpType empType, Pageable pageable);
	
	UserEntity findByUserId(String userId);
	
	void deleteByUserId(String userId);
	
}
