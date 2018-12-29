package in.ac.siesgst.arena.epicentre.viewmodel.heroku;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import in.ac.siesgst.arena.epicentre.repository.HerokuRepository;

/**
 * Created by SIESGSTarena on 02/12/18.
 */
public class HerokuViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final HerokuRepository mHerokuRepository;

    public HerokuViewModelFactory(HerokuRepository herokuRepository) {
        mHerokuRepository = herokuRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HerokuViewModel(mHerokuRepository);
    }

}
