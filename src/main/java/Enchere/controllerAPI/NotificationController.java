//package Enchere.controllerAPI;
//
//
//import Enchere.dao.TokenUserDao;
//import Enchere.models.Notification;
//import Enchere.models.TokenUser;
//import Enchere.interfaces.NotificationRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/notification")
//@CrossOrigin
//public class NotificationController {
//    @Autowired
//    private NotificationRepository notificationRepository;
//
//    @PostMapping("/insert")
//    public ResponseEntity<Notification> insert_notification(@RequestBody Notification notification) throws Exception {
//             Notification n = notificationRepository.save(notification);
//            return new ResponseEntity(n,HttpStatus.OK);
//    }
//    @GetMapping("/notifications")
//    public ResponseEntity<List<Notification>> liste() throws Exception {
//            return new ResponseEntity(notificationRepository.findAll(),HttpStatus.OK);
//    }
//
//    @GetMapping("/notification/{idEnchere}")
//    public ResponseEntity<ArrayList<Notification>> getNotificationUser(@PathVariable int idEnchere,@RequestHeader("token") String token) throws Exception {
//        ArrayList<Notification> list = new ArrayList<>();
//        TokenUserDao tud = new TokenUserDao();
//        TokenUser tu = null;
//        try{
//            if(tud.validTokenUser(token)!=0)
//            {
//                tu = tud.getTokenUser(token);
//                list = (ArrayList<Notification>) notificationRepository.findByidUserAndIdEnchere(tu.getIdUtilisateur(),idEnchere);
//                return new ResponseEntity<>(list, HttpStatus.OK);
//            }
//            else
//            {
//               return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
//            }
//        }catch(Exception e){
//            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
//        }
//    }
//
//}
