package model;

import model.dao.DAOProvider;
import model.entities.EntityFactory;

public class Model {
    private static Model instance = new Model();

    private DAOProvider daoProvider;
    private EntityFactory factory;

    public static Model getInstance() {
        return instance;
    }

    private Model() {
        daoProvider = DAOProvider.getInstance();
        factory = new EntityFactory();
    }

    public DAOProvider getDAOProvider() {
        return daoProvider;
    }

    public EntityFactory getFactory() {
        return factory;
    }
}
