package com.example.school.security.notification.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class EmailTesterService implements IEmailTester{

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean test(String t) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(t);
        return matcher.matches();
    }
    
}
