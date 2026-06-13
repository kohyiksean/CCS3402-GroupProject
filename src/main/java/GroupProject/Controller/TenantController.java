package GroupProject.Controller;

import GroupProject.Entity.Tenant;
import GroupProject.Service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/tenants")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    //READ - show all tenants
    @GetMapping
    public String listTenants(Model model){
        model.addAttribute("tenants", tenantService.getAllTenants());
        return "tenant/list";
    }

    //CREATE - show add tenant form
    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("tenant", new Tenant());
        return "tenant/form";
    }

    //CREATE - save new tenant
    @PostMapping("/save")
    public String saveTenant(@ModelAttribute Tenant tenant){
        tenantService.saveTenant(tenant);
        return "redirect:/tenants";
    }

    //UPDATE - show edit form with existing data
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        model.addAttribute("tenant", tenantService.getTenantById(id));
        return "tenant/form";
    }

    //DELETE - delete tenant
    @GetMapping("/delete/{id}")
    public String deleteTenant(@PathVariable Long id){
        tenantService.deleteTenantById(id);
        return "redirect:/tenants";
    }
}
