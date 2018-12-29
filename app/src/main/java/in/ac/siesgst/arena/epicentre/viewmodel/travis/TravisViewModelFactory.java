package in.ac.siesgst.arena.epicentre.viewmodel.travis;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import in.ac.siesgst.arena.epicentre.repository.TravisRepository;

/**
 * Created by SIESGSTarena on 02/12/18.
 */
public class TravisViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final TravisRepository mTravisRepository;

    public TravisViewModelFactory(TravisRepository travisRepository) {
        mTravisRepository = travisRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TravisViewModel(mTravisRepository);
    }

}
