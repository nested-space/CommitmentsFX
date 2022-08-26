package com.edencoding.dao;

import com.edencoding.models.Actor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActorDAO {

    private static final String tableName = "Actors";
    private static final String idColumn = "id";
    private static final String actorColumn = "actor";

    private static final ObservableList<Actor> actors;

    static {
        actors = FXCollections.observableArrayList();
        updateActorsFromDB();
    }

    public static ObservableList<Actor> getActors() {
        return FXCollections.unmodifiableObservableList(actors);
    }

    private static void updateActorsFromDB() {

        String query = "SELECT * FROM " + tableName;

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            actors.clear();
            while (rs.next()) {
                actors.add(new Actor(
                        rs.getInt(idColumn),
                        rs.getString(actorColumn)
                ));
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Actors from database ");
            System.out.println(e.getMessage());
            actors.clear();
        }
    }


    public static void update(Actor actor) {
        //update database
        long rows = CRUDHelper.update(
                tableName,
                new String[]{actorColumn},
                new Object[]{actor.getActor()},
                new int[]{Types.VARCHAR},
                idColumn,
                Types.INTEGER,
                actor.getId()
        );

        if (rows == 0)
            throw new IllegalStateException("Actor to be updated with id " + actor.getId() + " didn't exist in database");

        //update cache
        Optional<Actor> optionalActor = getActor(actor.getId());
        optionalActor.ifPresentOrElse((oldActor) -> {
            actors.remove(oldActor);
            actors.add(actor);
        }, () -> {
            throw new IllegalStateException("Actor to be updated with id " + actor.getId() + " didn't exist in database");
        });
    }

    public static void insertActor(String actor) {
        //update database
        int id = (int) CRUDHelper.create(
                tableName,
                new String[]{actorColumn},
                new Object[]{actor},
                new int[]{Types.VARCHAR});

        //update cache
        actors.add(new Actor(id, actor));
    }

    public static void delete(int id) {
        //update database
        CRUDHelper.delete(tableName, id);

        //update cache
        Optional<Actor> actor = getActor(id);
        actor.ifPresent(actors::remove);

    }

    public static Optional<Actor> getActor(int id) {
        for (Actor actor : actors) {
            if (actor.getId() == id) return Optional.of(actor);
        }
        return Optional.empty();
    }
}
