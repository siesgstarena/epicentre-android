package in.ac.siesgst.arena.epicentre.repository;

import android.arch.lifecycle.LiveData;

import in.ac.siesgst.arena.epicentre.AppExecutors;
import in.ac.siesgst.arena.epicentre.database.dao.HerokuDao;
import in.ac.siesgst.arena.epicentre.database.entity.Heroku;

/**
 * Created by SIESGSTarena on 07/12/18.
 */
public class HerokuRepository {

    private static final String LOG_TAG = HerokuRepository.class.getSimpleName();
    private static final Object LOCK = new Object();

    // For Singleton Instantiation
    private static HerokuRepository sInstance;
    private final HerokuDao mHerokuDao;
    private final AppExecutors mExecutors;

    // Constructor
    private HerokuRepository(HerokuDao herokuDao, AppExecutors appExecutors) {
        mHerokuDao = herokuDao;
        mExecutors = appExecutors;
    }

    // Create Instance
    public synchronized static HerokuRepository getInstance(HerokuDao herokuDao,
                                                            AppExecutors appExecutors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new HerokuRepository(herokuDao, appExecutors);
            }
        }
        return sInstance;
    }

    public LiveData<Heroku> get() {
        return mHerokuDao.get();
    }

    public void insert(Heroku heroku) {
        mExecutors.diskIO().execute(() -> {
            mHerokuDao.insert(heroku);
        });
    }

    public void updateMaintenance(Boolean maintenance) {
        mExecutors.diskIO().execute(() -> {
            mHerokuDao.updateMaintenance(maintenance);
        });
    }

    public void updateState(String state) {
        mExecutors.diskIO().execute(() -> {
            mHerokuDao.updateState(state);
        });
    }

    public void delete() {
        mExecutors.diskIO().execute(() -> {
            mHerokuDao.delete();
        });
    }

}
