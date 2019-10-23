package in.ac.siesgst.arena.epicentre.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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
