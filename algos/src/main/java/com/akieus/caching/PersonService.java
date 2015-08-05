package com.akieus.caching;

public class PersonService implements IPersonService {

    private IPersonDao personDao;

    @Override
    public Person findByName(String name) {
        return personDao.findByName(name);
    }

    @Override
    public Person findById(int id) {
        return personDao.findById(id);
    }
}
