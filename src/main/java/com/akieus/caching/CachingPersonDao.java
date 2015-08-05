package com.akieus.caching;

public class CachingPersonDao implements IPersonDao {

    private Cache<Integer, Person> cache;
    private IPersonDao dao;

    @Override
    public Person findByName(String name) {
        return null; // TODO
    }

    @Override
    public Person findById(int id) {
        Person person = cache.get(id);
        if (person != null) {
            return person;
        }

        person = dao.findById(id);
        if (person != null) {
            cache.put(id, person);
        }

        return person;
    }
}
