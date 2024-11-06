package de.szut.lf8_starter.projekt;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class QualifikationDetail {

    @Column(name = "soll")
    private long soll;

    @Column(name = "ist")
    private long ist;

    public void istDe(){
        this.ist--;
    }

    public void istIn(){
        this.ist++;
    }
}

