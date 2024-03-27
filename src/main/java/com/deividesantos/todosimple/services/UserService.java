package com.deividesantos.todosimple.services;

import com.deividesantos.todosimple.models.DTO.UserCreateDTO;
import com.deividesantos.todosimple.models.DTO.UserUpdateDTO;
import com.deividesantos.todosimple.models.User;
import com.deividesantos.todosimple.repositories.UserRepository;
import com.deividesantos.todosimple.services.exception.DataBindingViolationException;
import com.deividesantos.todosimple.services.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository objUserRepository;

    public User findbyid(Long id){
        Optional<User> objuseroptional=this.objUserRepository.findById(id);
        return objuseroptional.orElseThrow(()-> new ObjectNotFoundException("Usuario não encontrado! id:"
        +id+",Tipo"+User.class.getName()
        ));
    }

    public List<User> findByAll(){
        return objUserRepository.findAll();
    }

    @Transactional
    public User createUser(User objuser){
        objuser.setId(null);
        objuser = this.objUserRepository.save(objuser);
        objuser.setPassword(this.passwordEncoder.encode(objuser.getPassword()));
        return objuser;

    }


    @Transactional
    public User updateuser(User objuser){
        User newobj= findbyid(objuser.getId());
        newobj.setPassword(this.passwordEncoder.encode(objuser.getPassword()));
        newobj.setPassword(objuser.getPassword());
        return this.objUserRepository.save(newobj);


    }


    public void delete(Long id){
        findbyid(id);
        try{
         this.objUserRepository.deleteById(id);
        }catch (Exception e){
           throw new DataBindingViolationException("Não é possivel deletar usuario");
        }


    }

    public User fromDto(@Valid UserCreateDTO objdto){

        User user= new User();
        user.setUsername(objdto.getUsername());
        user.setPassword(objdto.getPassword());
        user.setRole(objdto.getRole());

        return user;
    }


    public User fromDto(@Valid UserUpdateDTO objdto){
        User user= new User();
        user.setId(objdto.getId());
        user.setPassword(objdto.getPassword());
        return user;
    }


}
