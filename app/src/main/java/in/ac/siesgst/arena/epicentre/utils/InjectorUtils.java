package in.ac.siesgst.arena.epicentre.utils;

import android.content.Context;

import in.ac.siesgst.arena.epicentre.AppExecutors;
import in.ac.siesgst.arena.epicentre.database.AppDatabase;
import in.ac.siesgst.arena.epicentre.repository.AuthRepository;
import in.ac.siesgst.arena.epicentre.repository.HerokuRepository;
import in.ac.siesgst.arena.epicentre.repository.TravisRepository;
import in.ac.siesgst.arena.epicentre.viewmodel.auth.AuthViewModelFactory;
import in.ac.siesgst.arena.epicentre.viewmodel.heroku.HerokuViewModelFactory;
import in.ac.siesgst.arena.epicentre.viewmodel.travis.TravisViewModelFactory;

/**
 * Created by SIESGSTarena on 02/12/18.
 */
public class InjectorUtils {

    private static AuthRepository provideAuthRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        AppExecutors appExecutors = AppExecutors.getInstance();
        return AuthRepository.getInstance(appDatabase.authDao(), appExecutors);
    }

    public static AuthViewModelFactory provideAuthViewModelFactory(Context context) {
        AuthRepository authRepository = provideAuthRepository(context.getApplicationContext());
        return new AuthViewModelFactory(authRepository);
    }

    private static HerokuRepository provideHerokuRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        AppExecutors appExecutors = AppExecutors.getInstance();
        return HerokuRepository.getInstance(appDatabase.herokuDao(), appExecutors);
    }

    public static HerokuViewModelFactory provideHerokuViewModelFactory(Context context) {
        HerokuRepository herokuRepository = provideHerokuRepository(context.getApplicationContext());
        return new HerokuViewModelFactory(herokuRepository);
    }

    private static TravisRepository provideTravisRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context.getApplicationContext());
        AppExecutors appExecutors = AppExecutors.getInstance();
        return TravisRepository.getInstance(appDatabase.travisDao(), appExecutors);
    }

    public static TravisViewModelFactory provideTravisViewModelFactory(Context context) {
        TravisRepository travisRepository = provideTravisRepository(context.getApplicationContext());
        return new TravisViewModelFactory(travisRepository);
    }

}
