package com.example.websokettest1_1.Controller;

//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//@ServerEndpoint("/websocket") //index.html에서 웹소켓을 연결하기 위한 주소로 @ServerEndpoint()를 지정
//public class GreetingController extends Socket {
//    private static final List<Session> session = new ArrayList<Session>();//필드에는 사용자 정보를 담기 위해 session list를 선언
//
//    @GetMapping("/") // 사용자가 입장 시 index.html을 리턴
//    public String index() {
//        return "index.html";
//    }
//
//    @OnOpen //사용자가 페이지에 접속할 때 실행되는 @OnOpen메서드에서 세션 리스트에 담아준다.
//    public void open(Session newUser) {
//        System.out.println("connected");
//        session.add(newUser);
//        System.out.println(newUser.getId()); // 사용자가 증가할 때마다 세션의 getId()는 1씩 증가하며 문자열 형태로 지정
//    }
//
//    @OnMessage // 사용자로부터 메시지를 받았을 때, 실행된다, 세션을 가져오도록 한다.
//    public void getMsg(Session recieveSession, String msg) {
//        for (int i = 0; i < session.size(); i++) { // 세션에 담겨있는 모든 사용자에게 메세지를 전달하기 위해 session의 size만큼 반복문을 돌림
//            if (!recieveSession.getId().equals(session.get(i).getId())) { // 메세지를 보낸 사람의 SessionId와 SessionList의 Id가 같지 않으면 상대방이 보낸 메시지이고
//                try {
//                    session.get(i).getBasicRemote().sendText("상대 : "+msg);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }else{                                                          // 아이디가 같다면 내가 보낸 메시지다
//                try {
//                    session.get(i).getBasicRemote().sendText("나 : "+msg);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}


import com.example.websokettest1_1.Greeting;
import com.example.websokettest1_1.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings") // .hello대상으로 메세지가 전송될때 greeting 호출
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(500); // simulated delay
        return new Greeting("상대방 : " + HtmlUtils.htmlEscape(message.getName()));
        // HtmlUtils.htmlEscape(message.getName()) : 내가 입력한 text
        // Greeting개체는 상대방에게 전송되는 최종 text
    }

}