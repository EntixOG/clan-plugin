package io.entix.validator.impl;

import io.entix.validator.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClanNameValidator implements Validator {

    String clanName;

    @Override
    public boolean isValid() {
        if (!clanName.matches("[a-zA-Z0-9©$™^]*")) return false;
        if (!ENCODER_LATIN.canEncode(clanName)) return false;
        return clanName.length() <= 8;
    }
}