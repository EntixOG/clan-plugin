package io.entix.clan.model.member.rank;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ClanRank {

    MEMBER("Mitglied"),
    MOD("Moderator"),
    ADMIN("Admin"),
    OWNER("Besitzer");

    String rankName;

}
