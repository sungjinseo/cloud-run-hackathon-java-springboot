package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
@RestController
public class Application {

  static class Self {
    public String href;
  }

  static class Links {
    public Self self;
  }

  static class PlayerState {
    public Integer x;
    public Integer y;
    public String direction;
    public Boolean wasHit;
    public Integer score;
  }

  static class Arena {
    public List<Integer> dims;
    public Map<String, PlayerState> state;
  }

  static class ArenaUpdate {
    public Links _links;
    public Arena arena;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.initDirectFieldAccess();
  }

  @GetMapping("/")
  public String index() {
    return "Let the battle begin!";
  }

  @PostMapping("/**")
  public String index(@RequestBody ArenaUpdate arenaUpdate) {
    String[] commands = new String[]{"F", "R", "L", "T"};



    PlayerState myInfo = arenaUpdate.arena.state.get(arenaUpdate._links.self.href);
    List<Integer> arenaUpdate.arena.dims;
    arenaSize.get(0);// 높이
    arenaSize.get(1);// 넓이
    //myInfo.x;
    //myInfo.y;
    if(myInfo.x == arenaSize.get(1) && myInfo.y == arenaSize.get(0)){
      if(!"N".equals(myInfo.direction) && !"W".equals(myInfo.direction)){
        if("S".equals(myInfo.direction)){
          return "L";
        }else{
          return "R";
        }
      }

      return "T";


    }else{
      if(myInfo.x != arenaSize.get(1)){
        //String[] commands = new String[]{"F", "R", "L", "T"};
        if("E".equals(myInfo.direction)){
          return "F";
        }else if("N".equals(myInfo.direction)){
          return "R";
        }else{
          return "L";
        }

      }

      if(myInfo.y != arenaSize.get(0)){
        if("S".equals(myInfo.direction)){
          return "F";
        }else if("E".equals(myInfo.direction)){
          return "R";
        }else{
          return "L";
        }
      }
    }
    return "T";
  }

}

