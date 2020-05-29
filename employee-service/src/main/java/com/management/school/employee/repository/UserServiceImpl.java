package com.management.school.employee.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.library.core.exception.RecordNotFoundException;
import com.management.school.employee.bean.UserDto;
import com.management.school.employee.bean.UserEntity;
import com.management.school.employee.bean.UserReqBean;
import com.management.school.employee.bean.enums.EmpType;

@Service
public class UserServiceImpl implements UserService {
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private EmpRepository empRepository;

	@Autowired
	public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, EmpRepository empRepository) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.empRepository = empRepository;
	}

	@Override
	public UserDto createUser(UserReqBean reqBean) {
		UserEntity userEntity = new UserEntity();
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		userEntity = modelMapper.map(reqBean, UserEntity.class);
		userEntity.setUserId(UUID.randomUUID().toString());
		userEntity.setPassword(bCryptPasswordEncoder.encode(reqBean.getPassword()));
		userEntity.setJoiningDate(new Date(System.currentTimeMillis()));
		empRepository.save(userEntity);

		UserDto userDto = modelMapper.map(userEntity, UserDto.class);
		return userDto;
	}

	@Override
	public UserDto getUserDetailByEmail(String email) {
		UserEntity userEntity = empRepository.findByEmail(email);
		if (userEntity == null)
			throw new RecordNotFoundException("User with this email not found");
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = modelMapper.map(userEntity, UserDto.class);
		return userDto;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = empRepository.findByEmail(username);
		if (userEntity == null)
			throw new RecordNotFoundException("User with this email not found");

		return new User(userEntity.getEmail(), userEntity.getPassword(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public List<UserDto> getUserList(EmpType empType, int page) {
		List<UserEntity> list = new ArrayList<UserEntity>();
		List<UserDto> returnList = new ArrayList<UserDto>();
		if (page <= 0)
			page = 1;
//		int from = ((page - 1) * 5);
//		int to = (page * 5) - 1;
		int size = 10;
		Pageable pageable = PageRequest.of(page-1, size);
		System.out.println("Page> " + (page-1) + " To " + size);
		if (empType != null) {
			list = empRepository.findByEmpType(empType, pageable);
		} else {
			Iterable<UserEntity> userIterable = empRepository.findAll(pageable);
			list = StreamSupport.stream(userIterable.spliterator(), false).collect(Collectors.toList());
		}
		// Map UserEntity to UserDao model class using stream API
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		returnList = list.stream().map(element -> modelMapper.map(element, UserDto.class)).collect(Collectors.toList());
		for (UserDto a : returnList) {
			System.out.println(">> " + a.getFirstName());
		}
		return returnList;
	}

	@Override
	public UserDto getEmpById(String userId) {
		UserEntity userEntity = empRepository.findByUserId(userId);
		if (userEntity == null) {
			throw new RecordNotFoundException("UserId " + userId + " not found");
		}
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = modelMapper.map(userEntity, UserDto.class);
		return userDto;
	}

	@Override
	public void deleteEmpById(String empId) {
		empRepository.deleteByUserId(empId);
//		empRepository.deleteById((long) 15);
		System.out.println("User deleted");
	}

}
