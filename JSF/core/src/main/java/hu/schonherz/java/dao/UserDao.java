package hu.schonherz.java.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.schonherz.java.entities.User;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

	User findByUsername(String username);

	Long countByUsername(String username);

	List<User> findAll(Sort sort);

	void removeById(Long id);
}
