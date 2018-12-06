package ru.itmo.wm4.service;

import org.springframework.stereotype.Service;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.domain.Tag;
import ru.itmo.wm4.domain.User;
import ru.itmo.wm4.repository.NoticeRepository;
import ru.itmo.wm4.repository.TagRepository;

import java.util.List;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final TagRepository tagRepository;

    public NoticeService(NoticeRepository noticeRepository, TagRepository tagRepository) {
        this.noticeRepository = noticeRepository;
        this.tagRepository = tagRepository;
    }

    public List<Notice> findByOrderByCreationTimeDesc() {
        return noticeRepository.findByOrderByCreationTimeDesc();
    }

    public Notice findById(Long noticeId) {
        return noticeId == null ? null : noticeRepository.findById(noticeId).orElse(null);
    }

    public void save(Notice notice, User user) {
        user.addNotice(notice);
        notice.setAuthor(user);
        noticeRepository.save(notice);
    }

    public void addTags(String[] tags, Long noticeId) {
        Notice notice = findById(noticeId);

        for (String tag : tags) {
            System.out.println(tag);
            Tag currentTag = new Tag();
            currentTag.setName(tag);
            notice.addTags(currentTag);
            tagRepository.save(currentTag);
        }

        System.out.println("NOTICE TAGS SIZE " + notice.getTags().size());
    }
}
