package GroupProject.Controller;

import GroupProject.Entity.Room;
import GroupProject.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    //READ - show all rooms
    @GetMapping
    public String listRooms(Model model){
        model.addAttribute("rooms", roomService.getAllRooms());
        return "rooms/list";
    }

    //CREATE - show add room form
    @GetMapping("/add")
    public String addRoom(Model model){
        model.addAttribute("room", new Room());
        return "room/form";
    }

    //CREATE - save new room
    @PostMapping("/save")
    public String saveRoom(Room room){
        roomService.saveRoom(room);
        return "redirect:/rooms";
    }

    //UPDATE - show edit form with existing data
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model){
        model.addAttribute("room", roomService.getRoomById(id));
        return "room/form";
    }

    //DELETE - delete room
    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id){
        roomService.deleteRoomById(id);
        return "redirect:/rooms";
    }
}
