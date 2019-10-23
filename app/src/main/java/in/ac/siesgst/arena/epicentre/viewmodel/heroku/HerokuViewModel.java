package in.ac.siesgst.arena.epicentre.viewmodel.heroku;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import in.ac.siesgst.arena.epicentre.database.entity.Heroku;
import in.ac.siesgst.arena.epicentre.repository.HerokuRepository;

/**
 * Created by SIESGSTarena on 02/12/18.
 */
public class HerokuViewModel extends ViewModel {

    private final LiveData<Heroku> mHeroku;

    private final HerokuRepository mHerokuRepository;

    HerokuViewModel(HerokuRepository herokuRepository) {
        mHerokuRepository = herokuRepository;
        mHeroku = mHerokuRepository.get();
    }

    public LiveData<Heroku> get() {
        return mHeroku;
    }

    public void insert(Heroku heroku) {
        mHerokuRepository.insert(heroku);
    }

    public void updateMaintenance(Boolean maintenance) {
        mHerokuRepository.updateMaintenance(maintenance);
    }

    public void updateState(String state) {
        mHerokuRepository.updateState(state);
    }

    public void delete() {
        mHerokuRepository.delete();
    }

}
