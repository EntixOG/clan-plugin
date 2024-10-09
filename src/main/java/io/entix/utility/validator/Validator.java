package io.entix.utility.validator;

import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

public interface Validator {

    CharsetEncoder ENCODER_LATIN = StandardCharsets.ISO_8859_1.newEncoder();

    boolean isValid();

}