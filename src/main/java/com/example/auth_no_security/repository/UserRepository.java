package com.example.auth_no_security.repository;

import com.example.auth_no_security.entity.User;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaAttributeConverter<User,Long> {
}
