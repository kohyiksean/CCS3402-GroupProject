package GroupProject.Service;

import GroupProject.Entity.Room;
import GroupProject.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    //READ - get all rooms
    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    //READ - get single room by id
    public Room getRoomById(Long id){
        return roomRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Room not found with id: " + id)
        );
    }

    //CREATE & UPDATE - save room
    public void saveRoom(Room room){
        roomRepository.save(room);
    }

    //DELETE - delete room by id
    public void deleteRoomById(Long id){
        roomRepository.deleteById(id);
    }
}
