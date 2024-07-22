package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.interfacemethods.AuthInterface;
import com.example.demo.model.Auth;
import com.example.demo.repository.AuthRepository;

import com.example.demo.exception.DuplicateAuthException;

@Service
@Transactional(readOnly = true)
public class AuthService implements AuthInterface{
	
	@Autowired
	private AuthRepository authRepository;

	@Override
	public List<Auth> findAllAuths() {
		return authRepository.findAll();
	}

	@Override
	public Auth findAuthById(Integer id) {
		return authRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Auth not found with ID:" + id));
	}
	
	@Override
	public Auth findAuthByUserId(Integer userId) {
		Auth findAuth = authRepository.findByUserId(userId);
		if (findAuth == null) {
			throw new RuntimeException("Auth not found with User/Employee ID:" + userId);
		}
		return findAuth;
	}

	@Override
	@Transactional(readOnly = false)
	public Auth saveAuth(Auth auth) {
		handleDuplicateAuthException(auth);
        return authRepository.save(auth);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteAuthById(Integer id) {
		try {
			authRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Failed to delete Auth with ID: " + id, e);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public Auth updateAuth(Integer id, Auth newAuth) {
		// first find the one to be updated
		Auth fAuth = findAuthById(id);
		fAuth.setRank(newAuth.getRank());
		fAuth.setMenuViewJason(newAuth.getMenuViewJason());
		return authRepository.save(fAuth);
	}
	
	
	private void handleDuplicateAuthException(Auth auth) {
		if (authRepository.checkDuplicateRankAndMenuViewJason(auth.getRank(),auth.getMenuViewJason())) {
            throw new DuplicateAuthException("Auth already exists: " + "Rank:" +auth.getRank() +"\n"
		+"AuthJason:" + auth.getMenuViewJason());
        }
	}

}
