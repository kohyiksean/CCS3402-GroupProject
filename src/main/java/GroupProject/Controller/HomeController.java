package GroupProject.Controller;

import GroupProject.Service.RoomService;
import GroupProject.Service.TenantService;
import GroupProject.Service.ViewingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private ViewingService viewingService;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("totalRooms", roomService.getAllRooms());
        model.addAttribute("totalTenants", tenantService.getAllTenants());
        model.addAttribute("totalViewings", viewingService.getallViewings());
        return "index";
    }
}
