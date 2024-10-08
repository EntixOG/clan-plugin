package io.entix.validator;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public interface Validator {

    CharsetEncoder ENCODER_LATIN = StandardCharsets.ISO_8859_1.newEncoder();

    boolean isValid();

}