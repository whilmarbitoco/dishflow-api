package org.whilmarbitoco.Core.utils;

import java.time.LocalDateTime;

public class Verification {
    public String code;
    public LocalDateTime expiry;

    public Verification(String code, LocalDateTime expiry) {
        this.code = code;
        this.expiry = expiry;
    }
}
