package ru.itmo.wm4.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * ru.itmo.wm4.form
 * Short Description: (눈_눈)
 *
 * @author nikitos
 * @version 1.0.0
 */

public class NoticeMini {

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 255)
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
