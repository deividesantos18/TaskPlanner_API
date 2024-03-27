package com.deividesantos.todosimple.repositories;

import com.deividesantos.todosimple.models.User;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

}
