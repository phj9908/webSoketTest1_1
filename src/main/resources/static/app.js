var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

// 채팅 시작전 세팅. SockJS 및 stomp.js/gs-guide-websocket 를 사용하여 SockJS 서버가 연결됨.
function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket); //
    stompClient.connect({}, function (frame) { //성공적으로 연결되면
    setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) { // '/topic/greetings'를 구독. 서버가 인사말 메시지를 게시할 대상
            showGreeting(JSON.parse(greeting.body).content); // greeting.body받은 걸 json으로 변환하고 content를 보여줌
        });
        // Config에서 config.enableSimpleBroker("/topic");로 지정했기에
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() { // 압력한 content를 보낼때 실행될 실행될 함수, GreetingController.greeting()수신
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
    // Config에서 config.setApplicationDestinationPrefixes("/app");로 지정했기에
}

function showGreeting(message) { // 받은 content 출력하기
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});