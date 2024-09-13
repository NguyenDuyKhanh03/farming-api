package com.example.Farming_App.dto;

import lombok.*;

import java.awt.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String message;
    private String sender;
    private MessageType type;

}
