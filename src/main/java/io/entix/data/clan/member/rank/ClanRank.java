package io.entix.data.clan.member.rank;

import io.entix.data.clan.member.permission.ClanPermission;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ClanRank {

    MEMBER("Mitglied", Collections.emptyList()),
    MOD("Moderator", Collections.emptyList()),
    ADMIN("Admin", Collections.emptyList()),
    OWNER("Besitzer", List.of(ClanPermission.values()));

    String rankName;
    List<ClanPermission> allowedPermission;

    public boolean hasPermission(@NonNull ClanPermission permission) {
        return allowedPermission.contains(permission);
    }

}
