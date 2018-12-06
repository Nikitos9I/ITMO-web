package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Message;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ru.itmo.webmail.web.page
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class TalkPage extends Page {
    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

        if (getUser() == null) {
            throw new RedirectException("/index");
        }
    }

    private void sendMessage(HttpServletRequest request, Map<String, Object> view) {
        Message message = new Message();
        String textFromMessage = request.getParameter("text-message");
        if (textFromMessage.equals("")) {
            view.put("error", "No message");
            action(request, view);
            return;
        }

        User targetUser = getUserService().findByLoginOrPassword(request.getParameter("target-user"));
        if (targetUser == null) {
            view.put("error", "No such User");
            view.put("text", textFromMessage);
            action(request, view);
            return;
        }

        message.setSourceUserId((Long) request.getSession().getAttribute(USER_ID_SESSION_KEY));
        message.setTargetUserId(targetUser.getId());
        message.setText(textFromMessage);
        getTalkService().sendMessage(message);

        throw new RedirectException("/talk", "action");
    }

    private String getCurrentTextMessage(String[] literals) {
        StringBuilder sb = new StringBuilder();
        for (String current : literals) {
            sb.append(current).append("<br>");
        }

        return sb.toString();
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        List<TalkWrapper> talkWrapper = new ArrayList<>();
        for (Message current : getTalkService().findAll((Long) request.getSession().getAttribute(USER_ID_SESSION_KEY))) {
            User sourceUser = getUserService().find(current.getSourceUserId());
            User targetUser = getUserService().find(current.getTargetUserId());
            talkWrapper.add(new TalkWrapper(
                    sourceUser.getId(),
                    sourceUser.getLogin(),
                    targetUser.getId(),
                    targetUser.getLogin(),
                    current.getText(),
                    current.getCreationTime())
            );
        }

        view.put("messages", talkWrapper);
    }

    public class TalkWrapper {
        Long sourceUserId;
        String sourceUserName;
        Long targetUserId;
        String targetUserName;
        String text;
        Date creationTime;

        TalkWrapper(Long sourceUserId, String sourceUserName, Long targetUserId, String targetUserName, String text, Date creationTime) {
            this.sourceUserId = sourceUserId;
            this.sourceUserName = sourceUserName;
            this.targetUserId = targetUserId;
            this.targetUserName = targetUserName;
            this.text = text;
            this.creationTime = creationTime;
        }

        public Long getSourceUserId() {
            return sourceUserId;
        }

        public void setSourceUserId(Long sourceUserId) {
            this.sourceUserId = sourceUserId;
        }

        public String getSourceUserName() {
            return sourceUserName;
        }

        public void setSourceUserName(String sourceUserName) {
            this.sourceUserName = sourceUserName;
        }

        public Long getTargetUserId() {
            return targetUserId;
        }

        public void setTargetUserId(Long targetUserId) {
            this.targetUserId = targetUserId;
        }

        public String getTargetUserName() {
            return targetUserName;
        }

        public void setTargetUserName(String targetUserName) {
            this.targetUserName = targetUserName;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Date getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(Date creationTime) {
            this.creationTime = creationTime;
        }
    }
}
