package in.ac.siesgst.arena.epicentre.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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
