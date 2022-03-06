package com.example.communities_post_website.exception;

import org.springframework.mail.MailException;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String s) {
        super(s);
    }
}
