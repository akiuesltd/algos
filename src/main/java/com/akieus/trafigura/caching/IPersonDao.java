package com.akieus.trafigura.caching;

public interface IPersonDao {
    Person findByName(String name);
    Person findById(int id);
}
