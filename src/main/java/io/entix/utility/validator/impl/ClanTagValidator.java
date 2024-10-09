package io.entix.utility.validator.impl;

import io.entix.utility.validator.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClanTagValidator implements Validator {

    String clanTag;

    @Override
    public boolean isValid() {
        if (!clanTag.matches("[a-zA-Z0-9©$™^]*")) return false;
        if (!ENCODER_LATIN.canEncode(clanTag)) return false;
        return clanTag.length() <= 6;
    }
}