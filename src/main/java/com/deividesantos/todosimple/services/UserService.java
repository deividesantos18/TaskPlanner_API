package com.deividesantos.todosimple.services;

import com.deividesantos.todosimple.models.User;
import com.deividesantos.todosimple.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository objUserRepository;

    public User findbyid(Long id){
        Optional<User> objuseroptional=this.objUserRepository.findById(id);
        return objuseroptional.orElseThrow(()-> new RuntimeException("Usuario não encontrado! id:"
        +id+",Tipo"+User.class.getName()
        ));
    }


    @Transactional
    public User createUser(User objuser){
        objuser.setId(null);
        objuser = this.objUserRepository.save(objuser);
        return objuser;

    }


    @Transactional
    public User updateuser(User objuser){
        User newobj= findbyid(objuser.getId());
        newobj.setPassword(objuser.getPassword());
        return this.objUserRepository.save(newobj);


    }


    public void delete(Long id){
        findbyid(id);
        try{
         this.objUserRepository.deleteById(id);
        }catch (Exception e){
           throw new RuntimeException("Não é possivel deletar usuario");
        }


    }



}
