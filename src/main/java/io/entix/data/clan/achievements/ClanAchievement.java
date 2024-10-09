package io.entix.data.clan.achievements;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.Component;

import java.util.List;


@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ClanAchievement {

    FIRST_MOB_KILL("Erster Mob-Kill.", 50, List.of(Component.text("<gray>Eliminiert ein beliebiges Mob.")));

    String translation;
    double experience;
    List<Component> description;
}
