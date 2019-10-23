package in.ac.siesgst.arena.epicentre.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import in.ac.siesgst.arena.epicentre.database.dao.AuthDao;
import in.ac.siesgst.arena.epicentre.database.dao.HerokuDao;
import in.ac.siesgst.arena.epicentre.database.dao.TravisDao;
import in.ac.siesgst.arena.epicentre.database.entity.Auth;
import in.ac.siesgst.arena.epicentre.database.entity.Heroku;
import in.ac.siesgst.arena.epicentre.database.entity.Travis;

/**
 * Created by SIESGSTarena on 02/12/18.
 */
@Database(entities = {Auth.class, Heroku.class, Travis.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "epicentre.db";

    // For Singleton Instantiation
    private static final Object LOCK = new Object();
    private static volatile AppDatabase sInstance;

    /**
     * Initialize database instance if not created
     *
     * @param context Application Context
     * @return AppDatabase Instance
     */
    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, AppDatabase.DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }

    // Getters for DAOs
    public abstract AuthDao authDao();

    public abstract HerokuDao herokuDao();

    public abstract TravisDao travisDao();

}
