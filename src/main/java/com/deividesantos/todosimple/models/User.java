package com.deividesantos.todosimple.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table(name=User.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public static final String TABLE_NAME="user";
    public interface CreatUser{};
    public interface  UpdateUser{};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",unique = true)
    private Long id;

    @Column(name = "username",length = 100,nullable = false,unique = true)
    @NotNull(groups = CreatUser.class)
    @NotEmpty(groups = CreatUser.class)
    @Size(groups = CreatUser.class,min=2,max=100)
    private String username;

    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    @Column(name="password",length = 60,nullable = false)
    @NotNull(groups = {CreatUser.class,UpdateUser.class})
    @NotEmpty(groups={CreatUser.class,UpdateUser.class})
    @Size(groups = {CreatUser.class,UpdateUser.class},min=8,max=60)
    private String password;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if(obj==null)
            return false;

        if (!(obj instanceof User user))
            return false;

        User other= (User) obj;
        if(this.id==null)
            if(other.id!=null)
                return false;

                else if(!this.id.equals(other.id))
                    return false;
                return Objects.equals(this.id,other.id)&&
                        Objects.equals(this.username,other.username) &&
                        Objects.equals(this.password,other.password);

    }

    @Override
    public int hashCode() {
        final int prime =31;
        int result =1;
        result = prime* result +((this.id==null)? 0:this.id.hashCode());
        return result;
    }

//private List<Task> tasks=new ArrayList<Task>();




}
