package in.ac.siesgst.arena.epicentre.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import in.ac.siesgst.arena.epicentre.database.entity.Heroku;

/**
 * Created by SIESGSTarena on 07/12/18.
 */
@Dao
public interface HerokuDao {

    @Query("SELECT * from heroku LIMIT 1")
    LiveData<Heroku> get();

    @Query("SELECT * from heroku LIMIT 1")
    Heroku getData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Heroku heroku);

    @Query("DELETE from heroku")
    void delete();

    @Query("UPDATE heroku SET maintenance = :maintenance")
    void updateMaintenance(Boolean maintenance);

    @Query("UPDATE heroku SET state = :state")
    void updateState(String state);

}
