package ru.itmo.wm4.service;

import org.springframework.stereotype.Service;
import ru.itmo.wm4.domain.Notice;
import ru.itmo.wm4.domain.User;
import ru.itmo.wm4.repository.NoticeRepository;

import java.util.List;

/**
 * ru.itmo.wm4.service
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    public void save(Notice notice) {
        this.noticeRepository.save(notice);
    }
}
