package in.ac.siesgst.arena.epicentre.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import in.ac.siesgst.arena.epicentre.database.entity.Auth;

/**
 * Created by SIESGSTarena on 02/12/18.
 */
@Dao
public interface AuthDao {

    @Query("SELECT * from auth LIMIT 1")
    LiveData<Auth> get();

    @Query("SELECT * from auth LIMIT 1")
    Auth getData();

    @Insert
    void insert(Auth auth);

    @Query("DELETE from auth")
    void delete();

    @Query("UPDATE auth SET updatedAt = :updatedAt")
    void update(String updatedAt);

    @Query("UPDATE auth SET token = :token, updatedAt = :updatedAt")
    void update(String token, String updatedAt);

}
