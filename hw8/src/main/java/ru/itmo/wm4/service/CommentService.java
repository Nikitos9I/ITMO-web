package ru.itmo.wm4.service;

import org.springframework.stereotype.Service;
import ru.itmo.wm4.domain.Comment;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.domain.User;
import ru.itmo.wm4.repository.CommentRepository;

import java.util.List;

/**
 * ru.itmo.wm4.service
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findByNoticeId(Long noticeId) {
        return commentRepository.findByNoticeId(noticeId);
    }

    public void save(Comment comment, Notice notice, User user) {
        user.addComments(comment);
        notice.addComments(comment);
        comment.setAuthor(user);
        comment.setNotice(notice);
        commentRepository.save(comment);
    }
}
