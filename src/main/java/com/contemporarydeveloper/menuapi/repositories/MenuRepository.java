package com.contemporarydeveloper.menuapi.repositories;

import com.contemporarydeveloper.menuapi.models.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends MongoRepository<Menu, String> {
}
