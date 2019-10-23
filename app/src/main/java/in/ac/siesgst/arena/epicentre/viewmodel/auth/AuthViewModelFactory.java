package in.ac.siesgst.arena.epicentre.viewmodel.auth;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import in.ac.siesgst.arena.epicentre.repository.AuthRepository;

/**
 * Created by SIESGSTarena on 02/12/18.
 */
public class AuthViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AuthRepository mAuthRepository;

    public AuthViewModelFactory(AuthRepository authRepository) {
        mAuthRepository = authRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AuthViewModel(mAuthRepository);
    }

}
