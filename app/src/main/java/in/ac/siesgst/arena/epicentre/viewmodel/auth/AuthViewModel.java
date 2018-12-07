package in.ac.siesgst.arena.epicentre.viewmodel.auth;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import in.ac.siesgst.arena.epicentre.database.entity.Auth;
import in.ac.siesgst.arena.epicentre.repository.AuthRepository;

/**
 * Created by SIESGSTarena on 02/12/18.
 */
public class AuthViewModel extends ViewModel {

    private final LiveData<Auth> mAuth;

    private final AuthRepository mAuthRepository;

    AuthViewModel(AuthRepository authRepository) {
        mAuthRepository = authRepository;
        mAuth = mAuthRepository.getAuth();
    }

    public LiveData<Auth> getAuth() {
        return mAuth;
    }

    public void updateAuth() {
        mAuthRepository.updateAuth();
    }

    public void updateAuth(String token) {
        mAuthRepository.updateAuth(token);
    }

    public void deleteAuth() {
        mAuthRepository.deleteAuth();
    }

    public void insertAuth(String url, String code, String token) {
        mAuthRepository.insertAuth(url, code, token);
    }

}
