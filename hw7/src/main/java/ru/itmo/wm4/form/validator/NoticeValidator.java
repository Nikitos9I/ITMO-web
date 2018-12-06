package ru.itmo.wm4.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wm4.form.NoticeMini;

/**
 * ru.itmo.wm4.form.validator
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

@Component
public class NoticeValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return NoticeMini.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
