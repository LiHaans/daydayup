package com.golaxy.controller;


import com.golaxy.ws.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class WebSocketController {

    @Autowired
    private WebSocketServer webSocketServer;

    @GetMapping("/send")
    public void send() throws IOException {
        webSocketServer.batchSendMessage();
    }
}
