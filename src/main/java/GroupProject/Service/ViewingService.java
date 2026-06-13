package GroupProject.Service;

import GroupProject.Entity.Viewing;
import GroupProject.Repository.ViewingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewingService {
    @Autowired
    private ViewingRepository viewingRepository;

    //READ - get all viewings
    public List<Viewing> getallViewings(){
        return viewingRepository.findAll();
    }

    //READ - get single by id
    public Viewing getViewingById(Long id){
        return viewingRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Viewing not found with id: " + id)
        );
    }

    //CREATE AND UPDATE - save viewing
    public void saveViewing(Viewing viewing){
        viewingRepository.save(viewing);
    }

    //DELETE - delete viewing by id
    public void deleteViewing(Long id){
        viewingRepository.deleteById(id);
    }
}
