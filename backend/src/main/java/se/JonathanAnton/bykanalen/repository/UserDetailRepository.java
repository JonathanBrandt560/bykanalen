package se.JonathanAnton.bykanalen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.JonathanAnton.bykanalen.model.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {


}
