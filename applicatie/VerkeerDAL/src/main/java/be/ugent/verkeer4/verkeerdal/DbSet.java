package be.ugent.verkeer4.verkeerdal;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.sql2o.Query;
import org.sql2o.Sql2o;

public class DbSet<T> {

    private final Sql2o sql2o;
    private final Class<T> type;

    private final String insertQuery;
    private final String updateQuery;

    public DbSet(Class<T> type, Sql2o sql2o) {
        this.type = type;
        this.sql2o = sql2o;

        this.insertQuery = buildInsertQuery();
        this.updateQuery = buildUpdateQuery();
    }

    private String buildInsertQuery() {
        StringBuilder names = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (Field field : this.type.getDeclaredFields()) {
            if (!field.getName().equalsIgnoreCase(getPrimaryKey())) {
                names.append(field.getName())
                        .append(",");
                values.append(":")
                        .append(field.getName())
                        .append(",");
            }
        }
        return "INSERT INTO " + getTableName() + " (" + names.toString().substring(0, names.length() - 1) + ")"
                + " VALUES (" + values.toString().substring(0, values.length() - 1) + ")";
    }

    private String buildUpdateQuery() {

        StringBuilder values = new StringBuilder();
        for (Field field : this.type.getDeclaredFields()) {
            if (!field.getName().equalsIgnoreCase(getPrimaryKey())) {

                values.append(field.getName())
                        .append("=")
                        .append(":").append(field.getName())
                        .append(",");
            }
        }
        return "UPDATE " + getTableName() + " SET " + values.toString().substring(0, values.length() - 1)
                + " WHERE " + getPrimaryKey() + "=:" + getPrimaryKey();
    }

    public List<T> getItems() {
        try (org.sql2o.Connection con = sql2o.open()) {
            List<T> lst = con.createQuery("SELECT * from " + getTableName())
                    .executeAndFetch(this.type);
            return lst;
        }
    }

    public T getItem(int key) {
        try (org.sql2o.Connection con = sql2o.open()) {
            Query q = con.createQuery("SELECT * from " + getTableName() + " WHERE " + getPrimaryKey() + "= :" + getPrimaryKey())
                    .addParameter(getPrimaryKey(), key);

            T obj = q.executeAndFetchFirst(this.type);
            return obj;
        }
    }

    public T getItem(String condition, Map<String, Object> parameters) {
        try (org.sql2o.Connection con = sql2o.open()) {
            Query q = con.createQuery("SELECT * from " + getTableName() + " WHERE " + condition);

            for (Entry<String, Object> parameter : parameters.entrySet()) {
                q.addParameter(parameter.getKey(), parameter.getValue());
            }
            T obj = q.executeAndFetchFirst(this.type);
            return obj;
        }
    }

    public int insert(T object) {
        try (org.sql2o.Connection con = sql2o.open()) {

            Query q = con.createQuery(insertQuery)
                    .bind(object);
            Object key = q.executeUpdate().getKey();
            return (int) (long) key;
        }
    }

    public void update(T object) throws Exception {
        try (org.sql2o.Connection con = sql2o.open()) {

            Query q = con.createQuery(updateQuery)
                    .bind(object);

            if (getPrimaryKey().equalsIgnoreCase("id")) { // sql2o bind methode voegt geen 'id' toe
                try {
                    Field f = this.type.getDeclaredField(getPrimaryKey());
                    f.setAccessible(true);
                    q.addParameter(getPrimaryKey(), f.get(object));
                } catch (NoSuchFieldException | SecurityException ex) {
                    throw new Exception("Primary key field is niet gevonden op het object");
                }
            }

            q.executeUpdate();
        }
    }

    public void delete(int key) {
           try (org.sql2o.Connection con = sql2o.open()) {

            Query q = con.createQuery("DELETE FROM " + getTableName() + " WHERE " + getPrimaryKey() + "=:" + getPrimaryKey())
                    .addParameter(getPrimaryKey(), key);

            q.executeUpdate();
        }
    }
    
    protected String getPrimaryKey() {
        return "id";
    }

    protected String getTableName() {
        return this.type.getSimpleName();
    }
}
