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
    System.out.println(arenaUpdate);
    String[] commands = new String[]{"F", "R", "L", "T"};
    // 목표는 오른쪽 아래 구석탱이로 가자
    // 왼쪽으로 가서 벽이면 위로 간다
    arenaSize.get(0);// 높이
    arenaSize.get(1);// 넓이
    // 이스트랑 // 사우스로 맞춘다

    System.out.println(myInfo.x + "::::" + myInfo.y);
    System.out.println(arenaSize.get(1) + "::::" + arenaSize.get(0));
    //myInfo.x;
    //myInfo.y;
    if(myInfo.x == arenaSize.get(1) && myInfo.y == arenaSize.get(0)){
      // my direction은 항상 N이나 W를 바라보고 있어야 한다 아니라면 맞춰주자
      // 위아래로 2칸에 위치한 적의 정보를 얻는다
      if(!"N".equals(myInfo.direction) && !"W".equals(myInfo.direction)){
        if("S".equals(myInfo.direction)){
          return "L";
        }else{
          return "R";
        }
      }

      return "T";


      // 여기서 방어 한다 대각선방향은 어디로 바라보고 있는지 확인하고 아래위로 2칸전에 오고 있는지 확인하자
    }else{
      // 계속 움직이자 움직이면서 맞아도 움직이자.
      // 오른쪽부터 그리고 아래쪽으로 순서대로 움직인다.
      if(myInfo.x != arenaSize.get(1)){
        // 여기서 방향이 동쪽이면 움직이고 아니면 방향을 맞춰주자
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

