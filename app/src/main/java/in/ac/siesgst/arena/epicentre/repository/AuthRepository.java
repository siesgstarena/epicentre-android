package in.ac.siesgst.arena.epicentre.repository;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Date;

import in.ac.siesgst.arena.epicentre.AppExecutors;
import in.ac.siesgst.arena.epicentre.database.dao.AuthDao;
import in.ac.siesgst.arena.epicentre.database.entity.Auth;

/**
 * Created by SIESGSTarena on 02/12/18.
 */
public class AuthRepository {

    private static final String LOG_TAG = AuthRepository.class.getSimpleName();
    private static final Object LOCK = new Object();

    // For Singleton Instantiation
    private static AuthRepository sInstance;
    private final AuthDao mAuthDao;
    private final AppExecutors mExecutors;

    // Constructor
    private AuthRepository(AuthDao authDao, AppExecutors appExecutors) {
        mAuthDao = authDao;
        mExecutors = appExecutors;
    }

    // Create Instance
    public synchronized static AuthRepository getInstance(AuthDao authDao,
                                                          AppExecutors appExecutors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AuthRepository(authDao, appExecutors);
            }
        }
        return sInstance;
    }

    public LiveData<Auth> getAuth() {
        return mAuthDao.get();
    }

    public void insertAuth(String url, String code, String token) {
        // Insert in Remote Firebase Realtime Database
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(
                instanceIdResult -> {
                    // Insert appToken for notifications
                    String appToken = instanceIdResult.getToken();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("tokens");
                    myRef.push().setValue(appToken, (databaseError, databaseReference) -> {
                        // TODO: Handle Database Insert Errors for Remote Database
                        // Insert into Local Room Database
                        String remoteId = databaseReference.getKey();
                        mExecutors.diskIO().execute(() -> {
                            mAuthDao.insert(new Auth(1, url, code, token, remoteId, new Date().toString(), new Date().toString()));
                        });

                    });
                });
    }

    public void updateAuth(String token) {
        mExecutors.diskIO().execute(() -> {
            mAuthDao.update(token, new Date().toString());
        });
    }

    public void updateAuth() {
        mExecutors.diskIO().execute(() -> {
            mAuthDao.update(new Date().toString());
        });
    }

    public void deleteAuth() {
        mExecutors.diskIO().execute(() -> {
            // Remove from Remote Firebase Realtime Database
            String remoteId = mAuthDao.getData().getRemoteId();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("tokens");
            myRef.child(remoteId).setValue(null);
            // Remove from Local Room Database
            mAuthDao.delete();
        });
    }

}
