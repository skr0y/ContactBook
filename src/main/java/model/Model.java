package model;

import model.dao.DAOFactory;
import model.entities.EntityFactory;

public class Model {
    private static Model instance = new Model();

    private DAOFactory daoFactory;
    private EntityFactory entityFactory;

    public static Model getInstance() {
        return instance;
    }

    private Model() {
        daoFactory = new DAOFactory();
        entityFactory = new EntityFactory();
    }

    public DAOFactory getDaoFactory() {
        return daoFactory;
    }

    public EntityFactory getEntityFactory() {
        return entityFactory;
    }
}
