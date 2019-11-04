package in.ac.siesgst.arena.epicentre.viewmodel.travis;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

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
