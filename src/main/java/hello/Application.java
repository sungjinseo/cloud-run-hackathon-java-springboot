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
    List<Integer> arenaSize = arenaUpdate.arena.dims;

    System.out.println(arenaSize.get(0)+"::::"+arenaSize.get(1));
    System.out.println(myInfo.y+"::::"+myInfo.x);

    if(myInfo.x == arenaSize.get(1)-1 && myInfo.y == arenaSize.get(0)-1){
      if(!"N".equals(myInfo.direction) && !"W".equals(myInfo.direction)){
        if("S".equals(myInfo.direction)){
          return "L";
        }else{
          return "R";
        }
      }

      return "T";


    }else{
      // 가는길 앞에 적이 있다면 어떻게 할것인가 공격...
      if(myInfo.x != arenaSize.get(1)-1){
        //String[] commands = new String[]{"F", "R", "L", "T"};
        if("E".equals(myInfo.direction)){
          if(myInfo.wasHit){
            return "T";
          }
          return "F";
        }else if("N".equals(myInfo.direction)){
          return "R";
        }else{
          return "L";
        }

      }

      if(myInfo.y != arenaSize.get(0)-1){
        if("S".equals(myInfo.direction)){
          if(myInfo.wasHit){
            return "T";
          }
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

