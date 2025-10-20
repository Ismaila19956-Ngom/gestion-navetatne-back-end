//package sn.webg.suivievaluation.controllers;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//import sn.webg.suivievaluation.models.ChatMessageDTO;
//
//@Controller
//@RequiredArgsConstructor
//public class ChatController {
//
//    private final SimpMessagingTemplate simpMessagingTemplate;
//
//    @MessageMapping("/public-chat")
//    @SendTo("/chatroom")
//    public ChatMessageDTO receivePublicMessage(@Payload ChatMessageDTO chatMessage) {
//        return chatMessage;
//    }
//
//    @MessageMapping("/private-chat")
//    public ChatMessageDTO receivePrivateMessage(@Payload ChatMessageDTO chatMessage) {
//        simpMessagingTemplate.convertAndSendToUser("user_"+chatMessage.getReceiverId(), "/user", chatMessage);
//        return chatMessage;
//    }
//}
