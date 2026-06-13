package GroupProject.Service;

import GroupProject.Entity.Tenant;
import GroupProject.Repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TenantService {
    @Autowired
    private TenantRepository tenantRepository;

    //READ - get all tenants
    public List<Tenant> getAllTenants(){
        return tenantRepository.findAll();
    }

    //READ - get single tenant by id
    public Tenant getTenantById(Long id){
        return tenantRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Tenant with id: " + id)
        );
    }

    //CREATE & UPDATE - save tenants
    public void saveTenant(Tenant tenant){
        tenantRepository.save(tenant);
    }

    //DELETE - delete tenant by id
    public void deleteTenantById(Long id){
        tenantRepository.deleteById(id);
    }
}
