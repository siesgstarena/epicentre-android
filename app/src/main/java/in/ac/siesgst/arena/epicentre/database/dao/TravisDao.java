package in.ac.siesgst.arena.epicentre.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import in.ac.siesgst.arena.epicentre.database.entity.Travis;

/**
 * Created by SIESGSTarena on 07/12/18.
 */
@Dao
public interface TravisDao {

    @Query("SELECT * from travis")
    LiveData<List<Travis>> get();

    @Query("SELECT * from travis")
    List<Travis> getData();

    @Query("SELECT * from travis LIMIT 1")
    LiveData<Travis> getSingle();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Travis> travisList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Travis travis);

    @Query("DELETE from travis")
    void delete();

}
