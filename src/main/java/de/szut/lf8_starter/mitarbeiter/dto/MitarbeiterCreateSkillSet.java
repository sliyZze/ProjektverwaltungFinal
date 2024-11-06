package de.szut.lf8_starter.mitarbeiter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
//Author: Tobias
@Getter
@Setter
public class MitarbeiterCreateSkillSet {
    @NotBlank
    private Set<String> skills = new HashSet<>();
}
