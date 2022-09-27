package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

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
    arenaUpdate.arena.state.remove(arenaUpdate._links.self.href);
    List<PlayerState> enermyList = new ArrayList<>(arenaUpdate.arena.state.values());
    List<Integer> arenaSize = arenaUpdate.arena.dims;

    System.out.println(arenaSize.get(0)+"::::"+arenaSize.get(1));
    System.out.println(myInfo.x+"::::"+myInfo.y);
    System.out.println(myInfo.direction);

    if(myInfo.x == arenaSize.get(0)-1 && myInfo.y == arenaSize.get(1)-1){
      //대각선까지는 귀찮아
      //해당적이 오는 방향으로 돌아서 잇다가 공격
      List<PlayerState> x_enermy = enermyList.stream()
                                             .filter(item->item.x >= arenaSize.get(0)-3)
                                             .collect(Collectors.toList());

      List<PlayerState> y_enermy = enermyList.stream()
                                             .filter(item->item.y >= arenaSize.get(1)-3)
                                             .collect(Collectors.toList());

      // 3칸이내로 공격된다고 한다~~
      if(x_enermy.size()>0){
        // 서쪽아니면 북쪽을 본다
        if("W".equals(myInfo.direction)){
          return "T";
        }else{
          return "L";
        }
      }

      if(y_enermy.size()>0){
        if("N".equals(myInfo.direction)){
          return "T";
        }else{
          return "R";
        }
      }

      if(!"N".equals(myInfo.direction) && !"W".equals(myInfo.direction)){
        if("S".equals(myInfo.direction)){
          return "R";
        }else{
          return "L";
        }
      }

      return "T";


    }else{
      // 가는길 앞에 적이 있다면 어떻게 할것인가 공격...
      // 적이나랑 같은 방향으로 움직이고 있다면 공격
      // 없다면 지나가자
      // 경로에서 3칸안에 적이 있다면 공격부터 하자

      if(myInfo.x != arenaSize.get(0)-1){

        //String[] commands = new String[]{"F", "R", "L", "T"};
        if("E".equals(myInfo.direction)){
          List<PlayerState> x_enermy = enermyList.stream()
                  .filter(item->item.x >= arenaSize.get(0)-3)
                  .collect(Collectors.toList());

          if(x_enermy.size()>0){
            return "T";
          }
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

      if(myInfo.y != arenaSize.get(1)-1){
        if("S".equals(myInfo.direction)){

          List<PlayerState> y_enermy = enermyList.stream()
                  .filter(item->item.y >= arenaSize.get(1)-3)
                  .collect(Collectors.toList());
          if(y_enermy.size()>0){
            return "T";
          }

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

