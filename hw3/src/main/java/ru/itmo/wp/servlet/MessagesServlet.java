package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class MessagesServlet extends HttpServlet {

    private ArrayList<MessageInformation> usersMessages = new ArrayList<>();

    private class MessageInformation {
        String user;
        String text;

        private MessageInformation(String user, String text) {
            this.user = user;
            this.text = text;
        }

        public String toString() {
            return user + ":" + text;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();

        if (uri.contains("auth")) {
            userAuth(req, resp);
        } else if (uri.contains("findAll")) {
            findAllMessages(resp);
        } else if (uri.contains("add")) {
            addMessage(req, resp);
        }
    }

    private void userAuth(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String optionalParam = req.getParameter("user");
        HttpSession session = req.getSession();

        if (optionalParam != null && !optionalParam.equals("")) {
            session.setAttribute("user", optionalParam);
        }

        String userName = (String) session.getAttribute("user");

        sendJson(resp, userName);
    }

    private void findAllMessages(HttpServletResponse resp) throws IOException {
        sendJson(resp, usersMessages);
    }


    private void addMessage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String message = req.getParameter("text");
        HttpSession session = req.getSession();

        String userName = (String) session.getAttribute("user");

        if (message != null && !message.isEmpty()) {
            usersMessages.add(new MessageInformation(userName, message));
        }

        sendJson(resp, message);
    }

    private void sendJson(HttpServletResponse resp, Object toConvert) throws IOException {
        String json = new Gson().toJson(toConvert);
        resp.setContentType("application/json");
        resp.getWriter().print(json);
        resp.getWriter().flush();
    }
}
