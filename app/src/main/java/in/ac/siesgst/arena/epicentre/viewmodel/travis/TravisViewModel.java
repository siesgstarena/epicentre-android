package in.ac.siesgst.arena.epicentre.viewmodel.travis;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import in.ac.siesgst.arena.epicentre.database.entity.Travis;
import in.ac.siesgst.arena.epicentre.repository.TravisRepository;

/**
 * Created by SIESGSTarena on 02/12/18.
 */
public class TravisViewModel extends ViewModel {

    private final LiveData<List<Travis>> mTravis;
    private final LiveData<Travis> mTravisSingle;

    private final TravisRepository mTravisRepository;

    TravisViewModel(TravisRepository travisRepository) {
        mTravisRepository = travisRepository;
        mTravis = mTravisRepository.get();
        mTravisSingle = mTravisRepository.getSingle();
    }

    public LiveData<List<Travis>> get() {
        return mTravis;
    }

    public LiveData<Travis> getSingle() {
        return mTravisSingle;
    }

    public void insert(List<Travis> travisList) {
        mTravisRepository.insert(travisList);
    }

    public void insert(Travis travis) {
        mTravisRepository.insert(travis);
    }

    public void delete() {
        mTravisRepository.delete();
    }

}
