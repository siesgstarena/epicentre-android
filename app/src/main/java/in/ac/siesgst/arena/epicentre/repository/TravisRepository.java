package in.ac.siesgst.arena.epicentre.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import in.ac.siesgst.arena.epicentre.AppExecutors;
import in.ac.siesgst.arena.epicentre.database.dao.TravisDao;
import in.ac.siesgst.arena.epicentre.database.entity.Travis;

/**
 * Created by SIESGSTarena on 07/12/18.
 */
public class TravisRepository {

    private static final String LOG_TAG = TravisRepository.class.getSimpleName();
    private static final Object LOCK = new Object();

    // For Singleton Instantiation
    private static TravisRepository sInstance;
    private final TravisDao mTravisDao;
    private final AppExecutors mExecutors;

    // Constructor
    private TravisRepository(TravisDao travisDao, AppExecutors appExecutors) {
        mTravisDao = travisDao;
        mExecutors = appExecutors;
    }

    // Create Instance
    public synchronized static TravisRepository getInstance(TravisDao travisDao,
                                                            AppExecutors appExecutors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new TravisRepository(travisDao, appExecutors);
            }
        }
        return sInstance;
    }

    public LiveData<List<Travis>> get() {
        return mTravisDao.get();
    }

    public LiveData<Travis> getSingle() {
        return mTravisDao.getSingle();
    }

    public void insert(Travis travis) {
        mExecutors.diskIO().execute(() -> {
            mTravisDao.insert(travis);
        });
    }

    public void insert(List<Travis> travisList) {
        mExecutors.diskIO().execute(() -> {
            mTravisDao.delete();
            mTravisDao.insert(travisList);
        });
    }

    public void delete() {
        mExecutors.diskIO().execute(() -> {
            mTravisDao.delete();
        });
    }

}
