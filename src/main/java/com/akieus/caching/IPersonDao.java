package com.akieus.caching;

public interface IPersonDao {
    Person findByName(String name);
    Person findById(int id);
}
