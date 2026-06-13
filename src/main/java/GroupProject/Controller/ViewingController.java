package GroupProject.Controller;

import GroupProject.Entity.Viewing;
import GroupProject.Service.ViewingService;
import GroupProject.Service.RoomService;
import GroupProject.Service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/viewings")
public class ViewingController {

    @Autowired
    private ViewingService viewingService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private TenantService tenantService;

    //READ - show all viewings
    @GetMapping
    public String listViewings(Model model){
        model.addAttribute("viewings", viewingService.getallViewings());
        return "viewings/list";
    }

    //CREATE - show add viewing form
    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("viewing", new Viewing());
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("tenants", tenantService.getAllTenants());
        return "viewings/form";
    }

    //CREATE - save new viewing
    @PostMapping("/save")
    public String saveViewing(@ModelAttribute Viewing viewing){
        viewingService.saveViewing(viewing);
        return "redirect:/viewings";
    }

    //UPDATE - show edit form with existing data
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        model.addAttribute("viewing", viewingService.getViewingById(id));
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("tenants", tenantService.getAllTenants());
        return "viewings/form";
    }

    //DELETE - delete viewing
    @GetMapping("/delete/{id}")
    public String deleteViewing(@PathVariable Long id){
        viewingService.deleteViewing(id);
        return "redirect:/viewings";
    }
}
