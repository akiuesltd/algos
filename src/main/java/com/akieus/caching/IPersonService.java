package com.akieus.caching;


public interface IPersonService {
    Person findByName(String name);
    Person findById(int id);
}
