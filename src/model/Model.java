package model;

import model.dao.*;

public class Model {
    private static Model instance = new Model();

    private DAOProvider daoProvider;

    public static Model getInstance() {
        return instance;
    }

    private Model() {
    }

    public DAOProvider getDAOProvider() {
        return daoProvider;
    }
}
